package jp.jaxa.iss.kibo.rpc.defaultapk;

import java.util.List;

import gov.nasa.arc.astrobee.types.Point;
import gov.nasa.arc.astrobee.types.Quaternion;
import jp.jaxa.iss.kibo.rpc.api.KiboRpcService;

public class YourService extends KiboRpcService {
    @Override
    protected void runPlan1(){
        api.startMission();

        // idk why artrobee didn't move to astronaut
        Point pos = new Point(11.143, -6.7607, 4.9654);
        Quaternion dir = new Quaternion(0f, 0f, -0.707f, 0.707f);
        api.moveTo(pos, dir, true);

//        Point targetPos = new Point(9.815, -9.806, 4.293);
//        Quaternion targetDir = new Quaternion(1.0f, 0.0f, 0.0f, 0.0f);
//        List<Point> trajectory = TrajectoryConstructor.construct(targetPos);
//
//        for (int i = 0; i < trajectory.size(); i++) {
//            api.moveTo(trajectory.get(i), targetDir, true);
//        }
    }

    @Override
    protected void runPlan2(){
        // write your plan 2 here
    }

    @Override
    protected void runPlan3(){
        // write your plan 3 here
    }
}
