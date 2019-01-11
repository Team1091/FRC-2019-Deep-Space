import com.team1091.shared.math.centimeters
import com.team1091.shared.math.feet
import com.team1091.shared.math.inches
import org.junit.Assert.assertEquals
import org.junit.Test

class LengthTest {

    @Test
    fun testLengths() {

        assertEquals(12.inches.toFeet(), 1.0, 0.001)

        assertEquals(3.feet.toYards(), 1.0, 0.001)

        assertEquals(100.centimeters.toMeters(), 1.0, 0.001)

    }

}