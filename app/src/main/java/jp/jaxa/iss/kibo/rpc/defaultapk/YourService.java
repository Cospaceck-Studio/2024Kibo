package jp.jaxa.iss.kibo.rpc.defaultapk;

import gov.nasa.arc.astrobee.types.Point;
import gov.nasa.arc.astrobee.types.Quaternion;
import jp.jaxa.iss.kibo.rpc.api.KiboRpcService;
import jp.jaxa.iss.kibo.rpc.defaultapk.util.PathPlanner;

public class YourService extends KiboRpcService {
    @Override
    protected void runPlan1(){
        api.startMission();

        Point pos = new Point(11.143, -6.7607, 4.9654);
        Quaternion dir = new Quaternion(0f, 0f, 0.707f, 0.707f);

        PathPlanner.Path path = PathPlanner.constructPath(api.getRobotKinematics().getPosition(), pos);
        path.follow(this::moveRobotTo, dir);

        api.reportRoundingCompletion();
        api.takeTargetItemSnapshot();
        api.notifyRecognitionItem();
    }

    @Override
    protected void runPlan2(){
        // write your plan 2 here
    }

    @Override
    protected void runPlan3(){
        // write your plan 3 here
    }

    private void moveRobotTo(Point pos, Quaternion dir) {
        this.api.moveTo(pos, dir, true);
    }
}
