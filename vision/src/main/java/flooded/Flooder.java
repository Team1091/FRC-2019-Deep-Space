package flooded;



//import java.util.ArrayDeque;
//
//public class Flooder {
//    int xSize;
//    int ySize;
//
//    private int[][] groups;
//    private boolean[][] green;
//
//    Flooder(int xSize, int ySize) {
//        this.xSize = xSize;
//        this.ySize = ySize;
//        groups = new int[xSize][ySize];
//        green = new boolean[xSize][ySize];
//    }
//
//    public void clear() {
//        for (int x = 0; x < xSize; x++) {
//            for (int y = 0; y < ySize; y++) {
//                groups[x][y] = 0;
//                green[x][y] = false;
//            }
//        }
//    }
//
////
////    public void flood() {
////
////
////        for (int x = 0; x < xSize; x++) {
////            for (int y = 0; y < ySize; y++) {
////
////
////
////                if (overWorld.getElevation(x, y) < 0)
////                    overWorld.setRegion(x, y, blockedRegion);
////                else
////                    overWorld.setRegion(x, y, uncalculatedRegion);
////            }
////        }
////
////        int i = 0;
////        for (int x = 0; x < overWorld.getPreciseXSize(); x++) {
////            for (int y = 0; y < overWorld.getPreciseYSize(); y++) {
////                if (overWorld.getRegionId(x, y) == uncalculatedRegion) {
////                    floodFillBFS(overWorld, x, y, uncalculatedRegion, i++);
////                }
////            }
////        }
////    }
////
////    private void floodFillBFS(OverWorld overWorld, int sx, int sy, int target, int replacement) {
////
////        Point2i q = new Point2i(sx, sy);
////        int xSize = overWorld.getPreciseXSize();
////        int ySize = overWorld.getPreciseYSize();
////
////        if (q.y < 0 || q.y >= ySize || q.x < 0 || q.x >= xSize)
////            return;
////
////        Deque<Point2i> stack = new ArrayDeque<>();
////        stack.push(q);
////        while (stack.size() > 0) {
////            Point2i p = stack.pop();
////            int x = p.x;
////            int y = p.y;
////            if (y < 0 || y >= ySize || x < 0 || x >= xSize)
////                continue;
////            int val = overWorld.getRegionId(x, y);
////            if (val == target) {
////                overWorld.setRegion(x, y, replacement);
////
////                if (x + 1 < xSize && overWorld.getRegionId(x + 1, y) == target)
////                    stack.push(new Point2i(x + 1, y));
////                if (x - 1 > 0 && overWorld.getRegionId(x - 1, y) == target)
////                    stack.push(new Point2i(x - 1, y));
////                if (y + 1 < ySize && overWorld.getRegionId(x, y + 1) == target)
////                    stack.push(new Point2i(x, y + 1));
////                if (y - 1 > 0 && overWorld.getRegionId(x, y - 1) == target)
////                    stack.push(new Point2i(x, y - 1));
////            }
////        }
////
////    }
//}
