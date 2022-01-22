package puf.frisbee.frontend.utils;

public class Calculations {
    private Calculations() {
    }

    /**
     * Return true if circles touch or intersect.
     * adapted from https://stackoverflow.com/a/37563695
     *
     * @param x1 x position circle 1
     * @param y1 y position circle 1
     * @param r1 radius circle 1
     * @param x2 x position circle 2
     * @param y2 y position circle 2
     * @param r2 radius circle 2
     * @return true if circles touch or intersect
     */
    public static boolean circlesIntersect(double x1, double y1, double r1,
                                           double x2, double y2, double r2) {
        return Math.hypot(x1 - x2, y1 - y2) <= (r1 + r2);
    }
}
