package jushin.net.memoryfresh.fragment;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jushin.net.memoryfresh.R;
import jushin.net.memoryfresh.activity.MainActivity;
import jushin.net.memoryfresh.activity.PacageListActivity;
import jushin.net.memoryfresh.activity.SettingsActivity;
import jushin.net.memoryfresh.service.MemoryFreshService;


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

    private SharedPreferences.OnSharedPreferenceChangeListener onPreferenceChangeListenter = new SharedPreferences.OnSharedPreferenceChangeListener() {

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

            //サービスのスイッチが有効になった時
            if (key.equals("service_switch")) {
                if (sharedPreferences.getBoolean("service_switch", true)) {
                    Intent intent = new Intent(getActivity(), MemoryFreshService.class);
                    getActivity().startService(intent);
                    //Toast.makeText(this, "サービスが停止していたため起動しました", Toast.LENGTH_LONG).show();
                    final CoordinatorLayout coordinatorLayout = (CoordinatorLayout) getActivity().findViewById(R.id.coordinator);
                    Snackbar.make(getActivity().findViewById(android.R.id.content), "サービスを開始しました", Snackbar.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(getActivity(), MemoryFreshService.class);

                    getActivity().stopService(intent);
                    //Toast.makeText(this, "サービスが停止していたため起動しました", Toast.LENGTH_LONG).show();
                    final CoordinatorLayout coordinatorLayout = (CoordinatorLayout) getActivity().findViewById(R.id.coordinator);
                    Snackbar.make(getActivity().findViewById(android.R.id.content), "サービスを停止しました", Snackbar.LENGTH_LONG).show();
                }
            }

        }
    };

    @Override
    public void onResume() {
        super.onResume();
        //リスナー登録
        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
        sharedPreferences.registerOnSharedPreferenceChangeListener(onPreferenceChangeListenter);
    }

    @Override
    public void onPause() {
        super.onPause();
        //リスナー解除
        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(onPreferenceChangeListenter);
    }
}
