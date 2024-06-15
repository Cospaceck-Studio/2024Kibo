package jp.jaxa.iss.kibo.rpc.defaultapk.util;

import gov.nasa.arc.astrobee.types.Point;

public final class MathHelper {
    public static Point getMidPoint(Point p, Point q) {
        double x = (p.getX() + q.getX()) / 2.0;
        double y = (p.getY() + q.getY()) / 2.0;
        double z = (p.getZ() + q.getZ()) / 2.0;
        return new Point(x, y, z);
    }

    public static double distanceToSqr(Point p, Point q) {
        double dx = p.getX() - q.getX();
        double dy = p.getY() - q.getY();
        double dz = p.getZ() - q.getZ();
        return dx * dx + dy * dy + dz * dz;
    }

    public static double distanceTo(Point p, Point q) {
        return Math.sqrt(distanceToSqr(p, q));
    }

    public static boolean isBetween(double d, double a, double b) {
        if (a == b) return a == d;
        if (a < b) {
            return a <= d && d <= b;
        } else {
            return b <= d && d <= a;
        }
    }

    public static Point shift(Point p, double x, double y, double z) {
        return new Point(p.getX() + x, p.getY() + y, p.getZ() + z);
    }
}
