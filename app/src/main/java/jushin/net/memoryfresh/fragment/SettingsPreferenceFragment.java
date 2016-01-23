package jushin.net.memoryfresh.fragment;


import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jushin.net.memoryfresh.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsPreferenceFragment extends PreferenceFragment {


    public SettingsPreferenceFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setteings_activity);

    }
}
