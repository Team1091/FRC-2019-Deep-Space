package vision

import java.lang.Math.abs

data class Point(val x: Int, val y: Int) {
    fun adjacentTo(point: Point): Boolean {
        return abs(point.x - x) <= 1 && abs(point.y - y) <= 1
    }

    fun getAjacentPoints(): List<Point> {
        return listOf(
                Point(x + 1, y + 1),
                Point(x + 1, y),
                Point(x - 1, y),
                Point(x - 1, y - 1),
                Point(x, y + 1),
                Point(x, y - 1),
                Point(x + 1, y - 1),
                Point(x - 1, y + 1)
        )
    }
}

class Blob(val points: HashSet<Point>) {
    // fun getLeftPt(): vision.Point = points.maxBy { it.x }

    var topLeft: Point? = null
    var topRight: Point? = null
    var bottomLeft: Point? = null
    var bottomRight: Point? = null

    //Function to Calculate Shape
    //Function to find closest blob
    //Function to find sibling blob if exists
    var uncheckedPoints = hashSetOf<Point>()


    fun addPoints(availablePoints: HashSet<Point>, point: Point) {
        //Add ajacent keys if they exist
        for (ajacentPoint in point.getAjacentPoints()) {
            if (availablePoints.contains(ajacentPoint))
                uncheckedPoints.add(ajacentPoint)
            points.add(ajacentPoint)
        }
    }

    fun getNextUncheckedPoint(): Point? {
        var point = uncheckedPoints.lastOrNull()
        if (point != null)
            uncheckedPoints.remove(point)
        return point;
    }

    fun finalize() {
        topLeft = points.minBy { it.x + it.y }
        topRight = points.maxBy { it.x - it.y }

        bottomLeft = points.minBy { it.x - it.y }
        bottomRight = points.maxBy { it.x + it.y }
    }
}


fun findCenter(points: HashSet<Point>): List<Blob> {

    val results = mutableListOf<Blob>()

    while (points.isNotEmpty()) {
        var point = points.lastOrNull()
        if (point == null) break

        points.remove(point);

        val blob = Blob(hashSetOf(point))

        while (point != null) {
            //Add points that touch the current point
            blob.addPoints(points, point)
            //Remove points that we added to the blob

            points.removeAll(blob.points)
            //Select the next point in the blob that hasn't been checked for siblings yet
            point = blob.getNextUncheckedPoint()
        }

        blob.finalize()
        results.add(blob)
    }

    println(results.size)
    return results
}
