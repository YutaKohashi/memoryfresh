package jushin.net.memoryfresh.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;

import dmax.dialog.SpotsDialog;
import jushin.net.memoryfresh.R;

/**
 * Created by Yuta on 2016/02/08.
 */
public class ProgressDialogFragment extends DialogFragment{
    private AlertDialog progressDialog;

    public static ProgressDialogFragment newInstance() {
        ProgressDialogFragment fragment = new ProgressDialogFragment();
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle safedInstanceState) {
        //第二引数でスタイルを適用
        progressDialog = new SpotsDialog(getActivity(), R.style.Custom);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));


        return progressDialog;
    }
}
