import com.github.sarxos.webcam.Webcam
import com.github.sarxos.webcam.WebcamPanel
import com.github.sarxos.webcam.ds.ipcam.IpCamDeviceRegistry
import com.github.sarxos.webcam.ds.ipcam.IpCamDriver
import com.github.sarxos.webcam.ds.ipcam.IpCamMode
import com.google.gson.Gson
import spark.Spark.exception
import spark.Spark.get
import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics2D
import java.awt.image.BufferedImage
import java.io.File
import java.lang.Double.min
import java.text.DecimalFormat
import javax.imageio.ImageIO
import javax.swing.JFrame
import javax.swing.WindowConstants


private val imageInfo = ImageInfo()

private val gson = Gson()
private val testImage: String? = null // "test.png"

fun main(args: Array<String>) {

    if (args.size != 1) {
        Webcam.setDriver(IpCamDriver())
        IpCamDeviceRegistry.register("RoboRioCam", "http://roborio-1091-frc.local:1181/stream.mjpg", IpCamMode.PUSH)
    }

    val webcams = Webcam.getWebcams()
    webcams
            .map { it.name }
            .forEachIndexed { i, cam -> println("$i: $cam") }

    val webcam = webcams.last()
    webcam.setCustomViewSizes(Dimension(640, 480))
    webcam.viewSize = Dimension(640, 480)
    val panel = WebcamPanel(webcam)

    panel.painter = object : WebcamPanel.Painter {
        override fun paintPanel(panel: WebcamPanel, g2: Graphics2D) {

        }

        override fun paintImage(panel: WebcamPanel, image: BufferedImage, g2: Graphics2D) {
            val imageToUse = if (testImage != null) ImageIO.read(File(testImage)) else image

            val targetingOutput = process(Color.GREEN, imageToUse)

            // pull out results we care about, let web server serve them as quick as possible
            imageInfo.seen = targetingOutput.seen
            imageInfo.center = targetingOutput.targetCenter
            imageInfo.distance = min(1000.0, targetingOutput.targetDistanceInches)

            writeToPanel(panel, g2, targetingOutput)
        }

        /**
         * Writes an image onto the panel, and deals with stretching it while keeping aspect ratio
         * @param panel
         * @param g2
         * @param targetingOutput
         */
        private fun writeToPanel(panel: WebcamPanel, g2: Graphics2D, targetingOutput: TargetingOutput) {

            // Draw our results onto the image, so that the driver can see if the autonomous code is tracking
            val outImage = targetingOutput.drawOntoImage(Color.RED, targetingOutput.processedImage)

            val imageX = outImage.width
            val imageY = outImage.height

            val imageAspectRatio = imageX.toFloat() / imageY.toFloat()

            val panelX = panel.width
            val panelY = panel.height

            val screenAspectRatio = panelX.toFloat() / panelY.toFloat()

            if (imageAspectRatio < screenAspectRatio) {
                // wide screen - y to the max
                val scaledImageX = (panelY * imageAspectRatio).toInt()
                g2.drawImage(outImage, (panelX - scaledImageX) / 2, 0, scaledImageX, panelY, null)

            } else {
                // tall screen - x to the max
                val scaledImageY = (panelX / imageAspectRatio).toInt()
                g2.drawImage(outImage, 0, (panelY - scaledImageY) / 2, panelX, scaledImageY, null)
            }
        }
    }

    val window = JFrame("Test webcam panel")
    window.add(panel)
    window.defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
    window.pack()
    window.isVisible = true

    // handle exceptions by just printing them out
    exception(Exception::class.java) { exception, request, response -> exception.printStackTrace() }

    // a little webserver.  Go to http://localhost:4567/center
    get("/center") { _, _ -> gson.toJson(imageInfo) }

    // This will be accessed from the robot at
    // http://laptop-1091.local:4567/center
}

fun process(targetColor: Color, inputImage: BufferedImage): TargetingOutput {

    val outputImage = BufferedImage(
            inputImage.width, inputImage.height,
            BufferedImage.TYPE_INT_RGB
    )

    var xSum: Long = 0
    var ySum: Long = 0
    var totalCount = 0

    // critical code, run on every pixel, every frame
    for (x in 0 until inputImage.width) {
        for (y in 0 until inputImage.height) {

            val rgb = inputImage.getRGB(x, y)

            // Extract color channels 0-255.
            val r = rgb shr 16 and 0x000000FF
            val g = rgb shr 8 and 0x000000FF
            val b = rgb and 0x000000FF

            if (g > 128 && g > b + 20 && g > r + 20) {
                outputImage.setRGB(x, y, targetColor.rgb)
                xSum += x.toLong()
                ySum += y.toLong()
                totalCount++

            } else {
                outputImage.setRGB(x, y, rgb)
            }
        }
    }


    val xCenter: Int
    val yCenter: Int

    var seen = false
    var pixelSize = 0
    var rightExtension = inputImage.width
    var leftExtension = inputImage.width

    if (totalCount <= 3) {
        xCenter = inputImage.width / 2
        yCenter = inputImage.height / 2
    } else {
        xCenter = (xSum / totalCount).toInt()
        yCenter = (ySum / totalCount).toInt()

        // left
        for (x in (xCenter downTo 0)) {
            if (outputImage.getRGB(x, yCenter) == Color.green.rgb) {
                leftExtension = xCenter - x
                break
            }
        }

        // right
        for (x in xCenter until inputImage.width) {
            if (outputImage.getRGB(x, yCenter) == Color.green.rgb) {
                rightExtension = x - xCenter
                break
            }
        }

        val total = rightExtension + leftExtension
        if (total < inputImage.width) {
            seen = true
            pixelSize = total
        }
    }

    /*
    * distance(mm) = (focal length (mm) * real height of the object (mm) * camera frame height in device (pixels) ) / ( image height (pixels) * sensor height (mm))
    * */
    //In Millimeters - Vaues for C270 web cam
    val focalLength = 4.2
    val targetPhysicalHeight = 279.4 //11in
    val cameraFrameHeight = inputImage.height
    val cameraSensorHeight = 2.2
    val targetPixelHeight = pixelSize //How to get?

    return TargetingOutput(
            imageWidth = inputImage.width,
            imageHeight = inputImage.height,
            xCenterColor = xCenter,
            yCenterColor = yCenter,
            targetDistance = (focalLength * targetPhysicalHeight * cameraFrameHeight) / (targetPixelHeight * cameraSensorHeight),
            processedImage = outputImage,
            seen = seen,
            rightExtension = rightExtension,
            leftExtension = leftExtension
    )
}

class TargetingOutput(
        val imageWidth: Int,
        val imageHeight: Int,
        val xCenterColor: Int,
        val yCenterColor: Int,
        var targetDistance: Double,
        val processedImage: BufferedImage,
        val seen: Boolean,
        val rightExtension: Int,
        val leftExtension: Int
) {


    /**
     * Get the center as a fraction of total image width
     *
     * @return float from -0.5 to 0.5
     */
    val targetCenter: Double
        get() = xCenterColor.toDouble() / imageWidth.toDouble() - 0.5


    val targetDistanceInches: Double
        get() = targetDistance / 25.4

    /**
     * This draws debug info onto the image before it's displayed.
     *
     * @param outputImage
     * @return
     */
    fun drawOntoImage(drawColor: Color, outputImage: BufferedImage): BufferedImage {

        val g = outputImage.createGraphics()

        g.color = drawColor
        g.drawLine(xCenterColor, yCenterColor - 10, xCenterColor, yCenterColor + 10)

        if (seen) {
            g.color = Color.YELLOW
            g.drawLine(xCenterColor - leftExtension, yCenterColor, xCenterColor + rightExtension, yCenterColor)

            // width labels, px and % screen width
            g.color = Color.BLUE
            g.drawString(df.format(targetDistanceInches) + " Inches", 10, 10)
        }

        return outputImage
    }

    companion object {
        private val df = DecimalFormat("#.0")
    }
}

class ImageInfo {
    var seen = false
    var center = 0.0
    var distance = 1000.0
}
