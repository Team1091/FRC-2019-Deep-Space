package vision

import org.junit.Test

class SpeedTest() {

    @Test
    fun testGreen() {


        val greenPoints = hashSetOf<Point>()


        for (y in 100..200) {
            for (x in 100..200) {
                greenPoints.add(Point(x, y))
            }

            for (x in 300..400) {
                greenPoints.add(Point(x, y))
            }
        }

        val start = System.nanoTime()

        val results = findCenter(greenPoints)

        val end = System.nanoTime()

        val timeToCompute = (end - start).toDouble() / 1000000000.0
        println("${timeToCompute} seconds to execute")
        println("${1 / timeToCompute} fps, assuming everything else infinitely fast")

        for (result in results) {
            with(result) {
                println("${topLeft} ${topRight} ${bottomLeft} ${bottomRight}")
            }
        }


    }


}