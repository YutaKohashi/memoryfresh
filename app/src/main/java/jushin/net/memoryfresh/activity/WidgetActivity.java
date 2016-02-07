package jushin.net.memoryfresh.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.DialogFragment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import jushin.net.memoryfresh.R;
import jushin.net.memoryfresh.fragment.ProgressDialogFragment;
import jushin.net.memoryfresh.util.MemoryKillerExecuteManager;

public class WidgetActivity extends Activity {

    //背景が透過しているActivity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_widget);

        //このActivityは起動時にメモリ解放を行うのでここに記述
        MemoryKillerExecuteManager exec = new MemoryKillerExecuteManager();
       exec.killExe(WidgetActivity.this);

        final int interval = 9000;
        final ProgressDialogFragment dialogFragment = ProgressDialogFragment
                .newInstance();
        dialogFragment.show(getFragmentManager(), "dialog_fragment");


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dialogFragment.dismiss();
                onDestroy();
            }
        }, interval);

    }


    @Override
    protected void onDestroy(){
        super.onDestroy();
        finish();
    }




}
