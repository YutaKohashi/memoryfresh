package jushin.net.memoryfresh.memory;

import android.app.Activity;
import android.app.ActivityManager;
import android.os.*;
import android.os.Process;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sakamotoyuuta on 16/01/30.
 */
public class MemoryManager {

    private Activity activity;

    public MemoryManager(Activity activity) {
        this.activity = activity;
    }

    private List<ActivityManager.RunningAppProcessInfo> initProcess() {
        ActivityManager manager = (ActivityManager) activity.getSystemService(activity.ACTIVITY_SERVICE);
        return manager.getRunningAppProcesses();
    }

    public int killTargetProcessId(ArrayList<Integer> processList) {
        int count = 0;

        for (ActivityManager.RunningAppProcessInfo process : initProcess()) {
            if (processList.contains(process.pid)) {
                killTargetProcess(process.pid);
                count++;
            }
        }
        return count;
    }

    public int killTargetProcessName(ArrayList<String> processList) {
        int count = 0;

        for (String process : processList) {
            if (killTargetProcess(process)) {
                count++;
            }
        }
        return count;
    }

    public int killTargetProcess(int pid) {
        Process.killProcess(pid);
        return 1;
    }

    public boolean killTargetProcess(String processName) {

        for (ActivityManager.RunningAppProcessInfo process : initProcess()) {
            if (process.processName.equals(processName)) {
                killTargetProcess(process.pid);
                return true;
            }
        }

        return false;
    }

    public int killAllProcess() {
        int count = 0;

        for (ActivityManager.RunningAppProcessInfo process : initProcess()) {
            killTargetProcess(process.pid);
            count++;
        }
        return count;
    }

}
