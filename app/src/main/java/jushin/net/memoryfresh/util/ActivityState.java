package jushin.net.memoryfresh.util;

/**
 * Created by Yuta on 2016/02/08.
 */
public class ActivityState {
    //画面遷移時にactivityを破棄するか
    static private boolean flag;

    public void activityKeep(){
        //フラグがtrueの時は破棄しない
        flag = true;
    }

    public void activityDestruction(){
        flag = false;
    }

    public boolean getActivityState(){
        return flag;
    }
}
