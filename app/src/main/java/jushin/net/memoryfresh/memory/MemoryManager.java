package jushin.net.memoryfresh.memory;

import android.app.Activity;
import android.app.ActivityManager;
import android.os.*;
import android.os.Process;

import com.jaredrummler.android.processes.ProcessManager;
import com.jaredrummler.android.processes.models.AndroidAppProcess;

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

    private List<AndroidAppProcess> initProcess() {
        return ProcessManager.getRunningAppProcesses();
    }

    public int killTargetProcessId(ArrayList<Integer> processList) {
        int count = 0;

        for (AndroidAppProcess process : initProcess()) {
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

        for (AndroidAppProcess process : initProcess()) {
            if (process.name.equals(processName)) {
                killTargetProcess(process.pid);
                return true;
            }
        }

        return false;
    }

    public int killAllProcess() {
        int count = 0;

        for (AndroidAppProcess process : initProcess()) {
            killTargetProcess(process.pid);
            count++;
        }
        return count;
    }

}
