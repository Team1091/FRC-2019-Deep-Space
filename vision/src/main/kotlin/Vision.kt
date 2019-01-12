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
import java.lang.Exception
import java.time.Clock

private val imageInfo = ImageInfo()

private val gson = Gson()

fun main(args: Array<String>) {
    if (args.size != 1) {
        //Webcam.setDriver(IpCamDriver())
        // IpCamDeviceRegistry.register("RoboRioCam", "http://roborio-1091-frc.local:1181/stream.mjpg", IpCamMode.PUSH)
    }

    val webcams = Webcam.getWebcams()
    val webcam = webcams[webcams.size - 1]
    webcam.setCustomViewSizes(Dimension(1440, 900))
    webcam.viewSize = Dimension(1440, 900)
    val panel = WebcamPanel(webcam)
    //var lastSample = Clock.systemUTC().millis();

    panel.painter = object : WebcamPanel.Painter {
        override fun paintPanel(panel: WebcamPanel, g2: Graphics2D) {

        }

        override fun paintImage(panel: WebcamPanel, image: BufferedImage, g2: Graphics2D) {

            //var lastTime = lastSample;
            //var now = Clock.systemUTC().millis()
            //if (now > lastTime + 500) {
                val targetingOutput = process(image)

                // pull out results we care about, let web server serve them as quick as possible
                imageInfo.yellow = targetingOutput.yellowCenter;
                imageInfo.yellowDistance = targetingOutput.yellowDistance;

                imageInfo.red = targetingOutput.redCenter;
                imageInfo.redDistance = targetingOutput.redDistance;

                imageInfo.blue = targetingOutput.blueCenter;
                imageInfo.blueDistance = targetingOutput.blueDistance;
                writeToPanel(panel, g2, targetingOutput)
            //    lastSample = Clock.systemUTC().millis()
            //    return;
            //}

            //writeToPanelFast(panel, g2, image)
        }

        private fun writeToPanelFast(panel: WebcamPanel, g2: Graphics2D, image: BufferedImage?) {

            // Draw our results onto the image, so that the driver can see if the autonomous code is tracking
            val outImage = image

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

        /**
         * Writes an image onto the panel, and deals with stretching it while keeping aspect ratio
         * @param panel
         * @param g2
         * @param targetingOutput
         */
        private fun writeToPanel(panel: WebcamPanel, g2: Graphics2D, targetingOutput: TargetingOutput) {

            // Draw our results onto the image, so that the driver can see if the autonomous code is tracking
            val outImage = targetingOutput.drawOntoImage(targetingOutput.processedImage)

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

fun process(inputImage: BufferedImage): TargetingOutput {

    val outputImage = BufferedImage(
            inputImage.width, inputImage.height,
            BufferedImage.TYPE_INT_RGB
    )

    val radius = 1
    var xSumY: Long = 0
    var ySumY: Long = 0
    var totalCountY = 0

    var xSumR: Long = 0
    var ySumR: Long = 0
    var totalCountR = 0

    var xSumB: Long = 0
    var ySumB: Long = 0
    var totalCountB = 0

    for (x in 0 until inputImage.width) {
        for (y in 0 until inputImage.height) {


//            var pixel = 0
//            var red = 0
//            var green = 0
//            var blue = 0

//            for (ix in x - radius..x + radius) {
//                for (iy in y - radius..y + radius) {
//
//                    if (ix < 0 || iy < 0 || ix >= inputImage.width || iy >= inputImage.height)
//                        continue
//
//                    val rgb = Color(inputImage.getRGB(ix, iy))
//                    red += Math.pow(rgb.red.toDouble(), 2.0).toInt()
//                    green += Math.pow(rgb.green.toDouble(), 2.0).toInt()
//                    blue += Math.pow(rgb.blue.toDouble(), 2.0).toInt()
//                    pixel++
//
//                }
//            }
            val rgb = Color(inputImage.getRGB(x, y))
            val r = rgb.red.toFloat() / 255f //Math.sqrt((red / pixel).toDouble()) / 255.0
            val g = rgb.green.toFloat() / 255f //Math.sqrt((green / pixel).toDouble()) / 255.0
            val b = rgb.blue.toFloat() / 255f //Math.sqrt((blue / pixel).toDouble()) / 255.0

            val yellow = Math.min(r, g) * (1 - b) // TODO: find a function to find yellowness

            if (yellow > .35) { //was 10
                outputImage.setRGB(x, y, Color.YELLOW.rgb)
                xSumY += x.toLong()
                ySumY += y.toLong()
                totalCountY++

            } else if (r > 0.5 && r > b + 0.18 && r > g + 0.18) {
                // its Red
                outputImage.setRGB(x, y, Color.RED.rgb)
                xSumR += x.toLong()
                ySumR += y.toLong()
                totalCountR++

            } else if (b > 0.5 && b > r + 0.18 && b > g + 0.18) {
                outputImage.setRGB(x, y, Color.BLUE.rgb)
                // its blue
                xSumB += x.toLong()
                ySumB += y.toLong()
                totalCountB++

            } else {
                outputImage.setRGB(x, y, inputImage.getRGB(x, y))
            }

        }
    }

    val xCenterY: Int
    val yCenterY: Int
    if (totalCountY == 0) {
        xCenterY = inputImage.width / 2
        yCenterY = inputImage.height / 2
    } else {
        xCenterY = (xSumY / totalCountY).toInt()
        yCenterY = (ySumY / totalCountY).toInt()
    }

    val xCenterR: Int
    val yCenterR: Int
    if (totalCountR == 0) {
        xCenterR = inputImage.width / 2
        yCenterR = inputImage.height / 2
    } else {
        xCenterR = (xSumR / totalCountR).toInt()
        yCenterR = (ySumR / totalCountR).toInt()
    }

    val xCenterB: Int
    val yCenterB: Int
    if (totalCountB == 0) {
        xCenterB = inputImage.width / 2
        yCenterB = inputImage.height / 2
    } else {
        xCenterB = (xSumB / totalCountB).toInt()
        yCenterB = (ySumB / totalCountB).toInt()
    }

    val targetingOutput = TargetingOutput()
    targetingOutput.imageWidth = inputImage.width
    targetingOutput.imageHeight = inputImage.height

    targetingOutput.xCenterYellow = xCenterY
    targetingOutput.yCenterYellow = yCenterY

    targetingOutput.xCenterRed = xCenterR
    targetingOutput.yCenterRed = yCenterR

    targetingOutput.xCenterBlue = xCenterB
    targetingOutput.yCenterBlue = yCenterB

    targetingOutput.processedImage = outputImage
    return targetingOutput
}

class TargetingOutput {

    var imageWidth: Int = 0
    var imageHeight: Int = 0

    var xCenterYellow: Int = 0
    var yCenterYellow: Int = 0

    var xCenterRed: Int = 0
    var yCenterRed: Int = 0

    var xCenterBlue: Int = 0
    var yCenterBlue: Int = 0

    var processedImage: BufferedImage? = null

    /**
     * Get the center as a fraction of total image width
     *
     * @return float from -0.5 to 0.5
     */
    val yellowCenter: Double
        get() = xCenterYellow.toDouble() / imageWidth.toDouble() - 0.5

    val redCenter: Double
        get() = xCenterRed.toDouble() / imageWidth.toDouble() - 0.5

    val blueCenter: Double
        get() = xCenterBlue.toDouble() / imageWidth.toDouble() - 0.5

    val yellowDistance: Double
        get() = 0.0

    val redDistance: Double
        get() = 0.0

    val blueDistance: Double
        get() = 0.0

    /**
     * This draws debug info onto the image before it's displayed.
     *
     * @param outputImage
     * @return
     */
    fun drawOntoImage(outputImage: BufferedImage?): BufferedImage? {

        val g = outputImage?.createGraphics()
        if (g == null) {
            return null
        }

        g.color = Color.YELLOW
        g.drawLine(xCenterYellow, yCenterYellow - 10, xCenterYellow, yCenterYellow + 10)

        g.color = Color.RED
        g.drawLine(xCenterRed, yCenterRed - 10, xCenterRed, yCenterRed + 10)

        g.color = Color.BLUE
        g.drawLine(xCenterBlue, yCenterBlue - 10, xCenterBlue, yCenterBlue + 10)

        // width labels, px and % screen width
        //g.drawString(width + " px", xCenterYellow, yCenterYellow - 25);

        //g.drawLine(outputImage.getWidth() / 2, yCenterYellow + 20, calcXCenter, yCenterYellow + 20);


        return outputImage
    }

    companion object {
        private val df = DecimalFormat("#.0")
    }
}

class ImageInfo {

    var yellow = 0.0
    var red = 0.0
    var blue = 0.0

    var redDistance = java.lang.Double.MAX_VALUE
    var blueDistance = java.lang.Double.MAX_VALUE
    var yellowDistance = java.lang.Double.MAX_VALUE
}