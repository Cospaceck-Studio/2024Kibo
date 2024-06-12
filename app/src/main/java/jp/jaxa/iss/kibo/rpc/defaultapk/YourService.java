package jp.jaxa.iss.kibo.rpc.defaultapk;

import jp.jaxa.iss.kibo.rpc.api.KiboRpcService;

public class YourService extends KiboRpcService {
    @Override
    protected void runPlan1(){
        // write your plan 1 here
        api.startMission();
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

