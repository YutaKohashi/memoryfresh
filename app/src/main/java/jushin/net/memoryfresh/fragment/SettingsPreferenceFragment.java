package jushin.net.memoryfresh.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jushin.net.memoryfresh.R;
import jushin.net.memoryfresh.activity.PacageListActivity;


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

        PreferenceScreen preferenceScreen = (PreferenceScreen)getPreferenceScreen().findPreference("enable_memory_clean");
        preferenceScreen.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {

                Intent intent = new Intent(getActivity(),PacageListActivity.class);
                startActivity(intent);
                return false;
            }
        });

    }
}
