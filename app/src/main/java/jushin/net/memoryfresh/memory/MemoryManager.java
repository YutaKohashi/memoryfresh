package jushin.net.memoryfresh.memory;

import android.app.Activity;
import android.app.ActivityManager;
import android.os.*;
import android.os.Process;
import android.util.Log;
import android.widget.Toast;

import com.jaredrummler.android.processes.ProcessManager;
import com.jaredrummler.android.processes.models.AndroidAppProcess;

import java.lang.ref.SoftReference;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MemoryManager {

    private List<AndroidAppProcess> initProcess() {
        return ProcessManager.getRunningAppProcesses();
    }

    public List<String> getRunningPackageName() {
        List<String> list = new ArrayList<>();
        for (AndroidAppProcess process : initProcess()) {
            list.add(process.getPackageName());
        }
        return list;
    }

    public int killTargetProcessId(List<Integer> processList) {
        int count = 0;


        for (AndroidAppProcess process : initProcess()) {
            if (processList.contains(process.pid)) {
                killTargetProcess(process.pid);
                count++;
            }
        }
        return count;
    }

    public int killTargetProcessName(List<String> processList) {
        int count = 0;

        for (String process : processList) {
            if (killTargetProcess(process)) {
                count++;
            }
        }
        return count;
    }
    String a;
    public int killProcessWithinList(List<String> processList) {

        int count = 0;


        Integer integer = new Integer(123456);
        SoftReference<Integer> ref = new SoftReference<Integer>(integer);

        Integer i = ref.get();
        integer = null;
        i = null;
        System.gc();

        jushin.net.memoryfresh.util.MemoryManager manager = new jushin.net.memoryfresh.util.MemoryManager();

        int flg = (int) manager.totalMemory();

        if(flg > 3000){
            flg = 15000;
        }else if(flg > 2000){
            flg = 10000;
        }else if(flg > 500){
            flg = 5000;
        }else{
            flg = 3000;
        }


        try {
            HashMap<String, Byte[]> map = new HashMap<>();
            for (int j = 0; j < flg; j++) {  //100000
                Byte[] v = new Byte[10000];
                String k = String.valueOf(System.currentTimeMillis());
                map.put(k, v);
            }
        } catch (OutOfMemoryError e) {

        }
        i = ref.get();

//
//        for (AndroidAppProcess process : initProcess()) {
//            if (!processList.contains(process.getPackageName())) {
//                Process.killProcess(process.pid);
//                count++;
//            }
//        }
        return count;
    }

    public int killTargetProcess(int pid) {
        Process.killProcess(pid);
        return 1;
    }

    public boolean killTargetProcess(String processName) {

        for (AndroidAppProcess process : initProcess()) {
            if (process.getPackageName().equals(processName)) {
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
