package jp.jaxa.iss.kibo.rpc.defaultapk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import gov.nasa.arc.astrobee.types.Point;
import gov.nasa.arc.astrobee.types.Quaternion;

public class TrajectoryConstructor {
    FieldPositions astronaut = new FieldPositions(11.143, -6.7607, 4.9654);

    // KOZ 1-1, KOZ 2-1, KOZ 3-1
    public static final List<FieldPositions> KOZSetpoints1 = Arrays.asList(
            new FieldPositions(10.56, -9.475, 4.62),
            new FieldPositions(10.475, -8.475, 5.295),
            new FieldPositions(10.56, -7.375, 4.62)
    );

    // KOZ 1-2, KOZ 2-2, KOZ 3-2
    public static final List<FieldPositions> KOZSetpoints2 = Arrays.asList(
            new FieldPositions(11.235, -9.475, 5.295),
            new FieldPositions(11.235, -8.475, 4.62),
            new FieldPositions(11.235, -7.375, 5.295)
    );

    public static List<Point> construct(Point target) {
        ArrayList<Point> list = new ArrayList<>();

        for (int i = KOZSetpoints2.size() - 1; i >= 0; i--) {
            if (target.getY() > KOZSetpoints2.get(i).get().getY()) { break; }
            list.add(KOZSetpoints2.get(i).shift(0.0, 0.18, 0.0));
            list.add(KOZSetpoints2.get(i).shift(0.0, -0.18, 0.0));
        }

        list.add(target);

        return list;
    }
}
