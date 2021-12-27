package puf.frisbee.frontend.utils;

public class Calculations {
    /**
     * return true if circles touch or intersect
     * adapted from https://www.geeksforgeeks.org/check-two-given-circles-touch-intersect/
     * @param x1 x position circle 1
     * @param y1 y position circle 1
     * @param r1 radius circle 1
     * @param x2 x position circle 2
     * @param y2 y position circle 2
     * @param r2 radius circle 2
     * @return true if circles touch or intersect
     */
    public static boolean circlesIntersect(double x1, double y1, double r1, double x2, double y2, double r2) {
        double distSq = (x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2);
        double radSumSq = (r1 + r2) * (r1 + r2);

        if (distSq == radSumSq) {
            return true;

        }
        else if (distSq > radSumSq) {
            return false;
        }
        else {
            return true;
        }
    }
}
