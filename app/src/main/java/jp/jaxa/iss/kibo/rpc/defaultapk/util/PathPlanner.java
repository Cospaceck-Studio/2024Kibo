package jp.jaxa.iss.kibo.rpc.defaultapk.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;

import gov.nasa.arc.astrobee.types.Point;
import gov.nasa.arc.astrobee.types.Quaternion;
import jp.jaxa.iss.kibo.rpc.defaultapk.constant.Constants;

public class PathPlanner {
    public static class Path {
        private final ArrayList<Point> midPoints = new ArrayList<>();

        void add(Point point) {
            this.midPoints.add(point);
        }

        public void follow(BiConsumer<Point, Quaternion> consumer, Quaternion direction) {
            for (Point point : this.midPoints) {
                consumer.accept(point, direction);
            }
        }
    }

    public static Path constructPath(Point currentPos, Point goal) {
        Path path = new Path();

        double startY = currentPos.getY();
        double endY = goal.getY();
        List<KeepOutZone> zones = getPassedZones(startY, endY);

        for (KeepOutZone zone : zones) {
            double yShift = Constants.Astrobee.SIDE_LENGTH * (startY < endY ? -1.0 : 1.0);
            Point pos1 = MathHelper.shift(zone.safe1, 0.0, yShift, 0.0);
            Point pos2 = MathHelper.shift(zone.safe2, 0.0, yShift, 0.0);
            boolean flag = MathHelper.distanceToSqr(currentPos, pos1) < MathHelper.distanceToSqr(currentPos, pos2);
            Point pos = flag ? pos1 : pos2;
            Point nextPos = MathHelper.shift(pos, 0.0, -2.0 * yShift, 0.0);
            path.add(pos);
            path.add(nextPos);
            currentPos = nextPos;
        }

        path.add(goal);
        return path;
    }

    private static List<KeepOutZone> getPassedZones(double startY, double endY) {
        List<KeepOutZone> list = new ArrayList<>();

        KeepOutZone.KOZs.forEach(zone -> {
            if (MathHelper.isBetween(zone.yPos, startY, endY)) {
                list.add(zone);
            }
        });

        if (startY > endY) Collections.reverse(list);
        return list;
    }
}
