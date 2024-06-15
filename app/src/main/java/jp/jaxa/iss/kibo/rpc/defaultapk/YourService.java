package jp.jaxa.iss.kibo.rpc.defaultapk;

import org.opencv.core.CvType;
import org.opencv.core.Mat;

import java.io.IOException;

import gov.nasa.arc.astrobee.types.Point;
import gov.nasa.arc.astrobee.types.Quaternion;
import jp.jaxa.iss.kibo.rpc.api.KiboRpcService;
import jp.jaxa.iss.kibo.rpc.defaultapk.constant.Constants;
import jp.jaxa.iss.kibo.rpc.defaultapk.util.PathPlanner;

public class YourService extends KiboRpcService {
    @Override
    protected void runPlan1(){
        api.startMission();

        Point pos = new Point(11.143, -6.7607, 4.9654);
        Quaternion dir = new Quaternion(0f, 0f, 0.707f, 0.707f);

        PathPlanner.Path path = PathPlanner.constructPath(api.getRobotKinematics().getPosition(), pos);
        path.follow(this::moveRobotTo, dir);

        // Pattern Matcher
        Mat cameraMatrix = new Mat( 3,  3, CvType.CV_64F);
        cameraMatrix.put( 0,  0, api.getNavCamIntrinsics()[0]);

        Mat cameraCoefficients = new Mat(1, 5, CvType.CV_64F);
        cameraCoefficients.put(0,  0, api.getNavCamIntrinsics()[1]);
        cameraCoefficients.convertTo(cameraCoefficients, CvType.CV_64F);

        PatternMatcher patternMatcher = new PatternMatcher(cameraMatrix, cameraCoefficients);
        matchPattern(patternMatcher,1);

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

    private void matchPattern(PatternMatcher patternMatcher, int areaID) {
        Mat image = api.getMatNavCam();
        int[] matchCounts = patternMatcher.matchTemplates(image);
        int mostMatchedIndex = patternMatcher.getMostMatchedTemplateIndex();
        api.setAreaInfo(areaID, Constants.TEMPLATE_NAMES[mostMatchedIndex], matchCounts[mostMatchedIndex]);
    }
}
