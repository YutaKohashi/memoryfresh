package jushin.net.memoryfresh;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jaredrummler.android.processes.ProcessManager;
import com.jaredrummler.android.processes.models.AndroidAppProcess;

import android.content.pm.PackageManager;

import java.util.List;
import android.widget.ListView;
import android.widget.TextView;


public class TeatFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    public TeatFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static TeatFragment newInstance(int position) {
        TeatFragment fragment = new TeatFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_teat, container, false);
//        TextView t1,t2,t3;
//        t1 = (TextView)v.findViewById(R.id.t1);
//        t2 = (TextView)v.findViewById(R.id.t2);
//        t3 = (TextView)v.findViewById(R.id.t3);
//
//        List<AndroidAppProcess> processes = ProcessManager.getRunningAppProcesses();
//
//        long size = 0L,total = 0,free = 0L;
//        for (AndroidAppProcess appProcess:processes){
//            try{
//                size += appProcess.statm().getSize();
//                total = appProcess.statm().getTotalSpace();
//                free = appProcess.statm().getFreeSpace();
//                Log.i("memory",String.valueOf(size));
//            }catch (Exception e){
//                Log.e("Erorr",e.toString());
//            }
//            t1.setText(String.valueOf(((size/8)/1024)/1024));
//
//        }
        MemoryManager memoryManager = new MemoryManager(this.getContext());

        memoryManager.TestSize();

        return v;
    }


}
