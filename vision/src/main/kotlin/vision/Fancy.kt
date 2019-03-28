package vision

import kaiju.math.Matrix2d


fun findCenter2(greens: Matrix2d<Boolean>) {

    val groups = Matrix2d(greens.getSize(), { x, y -> 0 })
    var i = 1;

    for (y in 0 until greens.ySize) {
        for (x in 0 until greens.xSize) {

            if (greens[x, y]) {
                val left = groups[x - 1, y]
                val above = groups[x, y - 1]

                val newVal = if (left == 0 && above == 0) {
                    i++

                } else if (left != 0 && above != 0 && above != left) {
                    // mark for merge and just use one of them
                    left
                } else if (left != 0) {
                    left
                } else {
                    above
                }

            }else{

            }

        }

    }

    // merge touching numbers

}
