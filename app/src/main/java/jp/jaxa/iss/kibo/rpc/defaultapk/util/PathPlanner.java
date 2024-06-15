package jp.jaxa.iss.kibo.rpc.defaultapk.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import gov.nasa.arc.astrobee.types.Point;
import jp.jaxa.iss.kibo.rpc.defaultapk.constant.FieldPositions;

public class PathPlanner {
    public static class Path {
        private final List<Point> midPoints;

        public Path(Point... points) {
            this.midPoints = Arrays.asList(points);
        }

        public void add(Point point) {
            this.midPoints.add(point);
        }
    }

    public static Path constructPath(Point currentPos, Point goal) {
        Path path = new Path();

        double startY = currentPos.getY();
        double endY = goal.getY();
        List<KeepOutZone> zones = getPassedZones(startY, endY);

        zones.forEach(zone -> {
            
        });

        return path;
    }

    private static List<KeepOutZone> getPassedZones(double startY, double endY) {

    }
}
