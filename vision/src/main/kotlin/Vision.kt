import com.github.sarxos.webcam.Webcam
import com.github.sarxos.webcam.WebcamPanel
import com.github.sarxos.webcam.ds.ipcam.IpCamDeviceRegistry
import com.github.sarxos.webcam.ds.ipcam.IpCamDriver
import com.github.sarxos.webcam.ds.ipcam.IpCamMode

import java.awt.image.BufferedImage
import java.awt.Graphics2D
import java.awt.Color
import javax.swing.WindowConstants
import javax.swing.JFrame
import java.text.DecimalFormat
import com.google.gson.Gson
import java.awt.Dimension
import java.io.File
import java.lang.Exception
import java.time.Clock
import javax.imageio.ImageIO

private val imageInfo = ImageInfo()

private val gson = Gson()
private val testImage:String? = null//"C:\\WIP\\Assets\\ReflectorDistance1.jpg"

fun main(args: Array<String>) {
//    if (args.size != 1) {
//    Webcam.setDriver(IpCamDriver())
//    IpCamDeviceRegistry.register("RoboRioCam", "http://roborio-1091-frc.local:1181/stream.mjpg", IpCamMode.PUSH)
//    }

    val webcams = Webcam.getWebcams()
    val webcam = webcams[webcams.size - 1]
    webcam.setCustomViewSizes(Dimension(640, 480))
    webcam.viewSize = Dimension(640, 480)
    val panel = WebcamPanel(webcam)

    panel.painter = object : WebcamPanel.Painter {
        override fun paintPanel(panel: WebcamPanel, g2: Graphics2D) {

        }

        override fun paintImage(panel: WebcamPanel, image: BufferedImage, g2: Graphics2D) {
            var imageToUse = if (testImage != null) ImageIO.read(File(testImage)) else image

            val targetingOutput = process(Color.GREEN, imageToUse)

            // pull out results we care about, let web server serve them as quick as possible
            imageInfo.color = targetingOutput.targetCenter;
            imageInfo.distance = targetingOutput.targetDistance;

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

            if (outImage == null) {
                throw Exception("Failed!");
            }

            val imageX = outImage.getWidth()
            val imageY = outImage.getHeight()

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
}

fun process(targetColor: Color, inputImage: BufferedImage): TargetingOutput {

    val outputImage = BufferedImage(
            inputImage.width, inputImage.height,
            BufferedImage.TYPE_INT_RGB
    )

    var xSum: Long = 0
    var ySum: Long = 0
    var totalCount = 0

    for (x in 0 until inputImage.width) {
        for (y in 0 until inputImage.height) {

            val rgb = Color(inputImage.getRGB(x, y))
            val r = rgb.red.toFloat() / 255f //Math.sqrt((red / pixel).toDouble()) / 255.0
            val g = rgb.green.toFloat() / 255f //Math.sqrt((green / pixel).toDouble()) / 255.0
            val b = rgb.blue.toFloat() / 255f //Math.sqrt((blue / pixel).toDouble()) / 255.0

            if (g > .50 && g > b + 0.1 && g > r + 0.1) { //was 10
                outputImage.setRGB(x, y, targetColor.rgb)
                xSum += x.toLong()
                ySum += y.toLong()
                totalCount++
            } else {
                outputImage.setRGB(x, y, inputImage.getRGB(x, y))
            }
        }
    }

    val xCenter: Int
    val yCenter: Int
    if (totalCount == 0) {
        xCenter = inputImage.width / 2
        yCenter = inputImage.height / 2
    } else {
        xCenter = (xSum / totalCount).toInt()
        yCenter = (ySum / totalCount).toInt()
    }

    val targetingOutput = TargetingOutput()
    targetingOutput.imageWidth = inputImage.width
    targetingOutput.imageHeight = inputImage.height

    targetingOutput.xCenterColor = xCenter
    targetingOutput.yCenterColor = yCenter

    /*
    * distance(mm) = (focal length (mm) * real height of the object (mm) * camera frame height in device (pixels) ) / ( image height (pixels) * sensor height (mm))
    * */
    //In Millimeters - Vaues for C270 web cam
    val focalLength = 4.2;
    val targetPhysicalHeight = 133.35; //5.25in
    val cameraFrameHeight = inputImage.height;
    val cameraSensorHeight = 2.2;
    val targetPixelHeight = 45; //How to get?

    targetingOutput.targetDistance = (focalLength * targetPhysicalHeight * cameraFrameHeight ) / ( targetPixelHeight * cameraSensorHeight)

    targetingOutput.processedImage = outputImage
    return targetingOutput
}

class TargetingOutput {

    var imageWidth: Int = 0
    var imageHeight: Int = 0

    var xCenterColor: Int = 0
    var yCenterColor: Int = 0

    var processedImage: BufferedImage? = null

    /**
     * Get the center as a fraction of total image width
     *
     * @return float from -0.5 to 0.5
     */
    val targetCenter: Double
        get() = xCenterColor.toDouble() / imageWidth.toDouble() - 0.5

    var targetDistance: Double = 0.0

    val targetDistanceInches: Double
            get() = targetDistance / 25.4;
    /**
     * This draws debug info onto the image before it's displayed.
     *
     * @param outputImage
     * @return
     */
    fun drawOntoImage(drawColor: Color, outputImage: BufferedImage?): BufferedImage? {

        val g = outputImage?.createGraphics()
        if (g == null) {
            return null
        }

        g.color = drawColor
        g.drawLine(xCenterColor, yCenterColor - 10, xCenterColor, yCenterColor + 10)

        // width labels, px and % screen width
        g.drawString(targetDistanceInches.toString() + " Inches", xCenterColor, yCenterColor - 25);

        //g.drawLine(outputImage.getWidth() / 2, yCenterYellow + 20, calcXCenter, yCenterYellow + 20);
        return outputImage
    }

    companion object {
        private val df = DecimalFormat("#.0")
    }
}

class ImageInfo {
    var color = 0.0
    var distance = java.lang.Double.MAX_VALUE
}