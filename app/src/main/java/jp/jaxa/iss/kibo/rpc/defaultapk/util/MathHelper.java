package jp.jaxa.iss.kibo.rpc.defaultapk.util;

import gov.nasa.arc.astrobee.types.Point;

public final class MathHelper {
    public static Point getMidPoint(Point p, Point q) {
        double x = (p.getX() + q.getX()) / 2.0;
        double y = (p.getY() + q.getY()) / 2.0;
        double z = (p.getZ() + q.getZ()) / 2.0;
        return new Point(x, y, z);
    }

    public static Point shift(Point p, double x, double y, double z) {
        return new Point(p.getX() + x, p.getY() + y, p.getZ() + z);
    }
}
