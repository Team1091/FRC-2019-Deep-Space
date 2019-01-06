import com.team1091.shared.math.squareACircle
import org.junit.Assert.assertEquals
import org.junit.Ignore
import org.junit.Test
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import java.lang.Math.toRadians
import javax.imageio.ImageIO
import kotlin.math.cos
import kotlin.math.sin

class InputMappingTest {

    @Test
    fun testInputSquaringAtZero() {
        val (x, y) = squareACircle(0.0, 0.0)
        assertEquals(0.0, x, 0.001)
        assertEquals(0.0, y, 0.001)
    }

    @Test
    fun testInputSquaringForward() {
        val (x, y) = squareACircle(0.0, 1.0)
        assertEquals(0.0, x, 0.001)
        assertEquals(1.0, y, 0.001)
    }

    @Test
    fun testInputSquaringBackwards() {
        val (x, y) = squareACircle(0.0, -1.0)
        assertEquals(0.0, x, 0.001)
        assertEquals(-1.0, y, 0.001)
    }

    @Test
    fun testInputSquaringRight() {
        val (x, y) = squareACircle(1.0, 0.0)
        assertEquals(1.0, x, 0.001)
        assertEquals(0.0, y, 0.001)
    }

    @Test
    fun testInputSquaringLeft() {
        val (x, y) = squareACircle(-1.0, 0.0)
        assertEquals(-1.0, x, 0.001)
        assertEquals(0.0, y, 0.001)
    }


    @Test
    fun testFortyFive() {
        // up and to the right
        val (x, y) = squareACircle(cos(toRadians(45.0)), sin(toRadians(45.0)))
        assertEquals(1.0, x, 0.001)
        assertEquals(1.0, y, 0.001)
    }


    @Test
    fun testOneThirtyFive() {
        // up and to the left
        val (x, y) = squareACircle(
                cos(toRadians(135.0)),
                sin(toRadians(45.0))
        )
        assertEquals(-1.0, x, 0.001)
        assertEquals(1.0, y, 0.001)
    }


    @Test
    fun testTwoTwoFive() {
        val (x, y) = squareACircle(
                cos(toRadians(225.0)),
                sin(toRadians(225.0))
        )
        assertEquals(-1.0, x, 0.001)
        assertEquals(-1.0, y, 0.001)
    }


    @Test
    fun testThreeThreeFive() {
        val (x, y) = squareACircle(
                cos(toRadians(315.0)),
                sin(toRadians(315.0))
        )
        assertEquals(1.0, x, 0.001)
        assertEquals(-1.0, y, 0.001)
    }


    @Ignore("comment this line out if you want to generate the images")
    @Test
    fun genImage() {

        val bi = BufferedImage(1000, 1000, BufferedImage.TYPE_INT_RGB)

        // Make a checkerbox
        for (x in 0 until bi.width) {
            for (y in 0 until bi.height) {
                val color = if (((x / 100) % 2 == 0) xor ((y / 100) % 2 == 0)) Color.BLACK.rgb else Color(x / 1000f, y / 1000f, 0f).rgb
                bi.setRGB(x, y, color)
            }
        }

        ImageIO.write(bi, "png", File("checker.png"))

        val out = BufferedImage(1000, 1000, BufferedImage.TYPE_INT_RGB)
        for (u in 0 until bi.width) {
            for (v in 0 until bi.height) {
                val (x, y) = squareACircle((u - 500) / 1000.0, (v - 500) / 1000.0)

                val xScaled = (x * 1000.0).toInt() + 500
                val yScaled = (y * 1000.0).toInt() + 500
                // println("$xScaled $yScaled")

                if (xScaled >= 0 && xScaled < bi.width && yScaled >= 0 && yScaled < bi.height) {
                    val color = bi.getRGB(xScaled, yScaled)
                    out.setRGB(u, v, color)
                } else out.setRGB(u, v, Color.GRAY.rgb)
            }
        }

        ImageIO.write(out, "png", File("circle.png"))

    }
}