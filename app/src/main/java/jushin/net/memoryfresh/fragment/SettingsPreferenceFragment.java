package jushin.net.memoryfresh.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.SeekBar;

import com.pavelsikun.seekbarpreference.SeekBarPreference;

import jushin.net.memoryfresh.R;
import jushin.net.memoryfresh.activity.MainActivity;
import jushin.net.memoryfresh.activity.PacageListActivity;
import jushin.net.memoryfresh.activity.SettingsActivity;
import jushin.net.memoryfresh.service.MemoryFreshService;


/**
 * このクラスに設定画面のアクションを記述
 */
public class SettingsPreferenceFragment extends PreferenceFragment {

    PreferenceScreen preferenceScreen;
    SwitchPreference switchPreference;
    SeekBarPreference seekBarPreference;
    SharedPreferences pref;
    ListPreference listPref;



    public SettingsPreferenceFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setteings_activity);

        pref = PreferenceManager.getDefaultSharedPreferences(getActivity());



        //二度目の起動でpreferenceのservice_switchにtrueが格納されている場合
        if(pref.getBoolean("service_switch", false)) {
            preferenceScreen = getPreferenceScreen();
            switchPreference = (SwitchPreference)preferenceScreen.findPreference("service_boot_switch");
            seekBarPreference = (SeekBarPreference)preferenceScreen.findPreference("timer_kill");
            listPref = (ListPreference)preferenceScreen.findPreference("list_pref");


            switchPreference.setEnabled(true);
            seekBarPreference.setEnabled(true);
            listPref.setEnabled(true);
        }

        //ListFragmentのサマリーに現在の設定を表示
        ListPreference list_preference = (ListPreference)getPreferenceScreen().findPreference("list_pref");
        list_preference.setSummary(list_preference.getValue());



//        PreferenceScreen preferenceScreen = (PreferenceScreen)getPreferenceScreen().findPreference("enable_memory_clean");
//
//        preferenceScreen.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
//            @Override
//            public boolean onPreferenceClick(Preference preference) {
//
//                Intent intent = new Intent(getActivity(),PacageListActivity.class);
//                startActivity(intent);
//                return false;
//            }
//        });

    }



    private SharedPreferences.OnSharedPreferenceChangeListener onPreferenceChangeListenter = new SharedPreferences.OnSharedPreferenceChangeListener() {

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {



            //サービスのスイッチが有効になった時
            if (key.equals("service_switch")) {
                if (sharedPreferences.getBoolean("service_switch", false)) {
                    Intent intent = new Intent(getActivity(), MemoryFreshService.class);
                    getActivity().startService(intent);


                    preferenceScreen = getPreferenceScreen();
                    switchPreference = (SwitchPreference)preferenceScreen.findPreference("service_boot_switch");
                    seekBarPreference = (SeekBarPreference)preferenceScreen.findPreference("timer_kill");
                    listPref = (ListPreference)preferenceScreen.findPreference("list_pref");

                    switchPreference.setEnabled(true);
                    seekBarPreference.setEnabled(true);
                    listPref.setEnabled(true);

                    //Toast.makeText(this, "サービスが停止していたため起動しました", Toast.LENGTH_LONG).show();
                    final CoordinatorLayout coordinatorLayout = (CoordinatorLayout) getActivity().findViewById(R.id.coordinator);
                    Snackbar.make(getActivity().findViewById(android.R.id.content), "サービスを開始しました", Snackbar.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(getActivity(), MemoryFreshService.class);

                    getActivity().stopService(intent);

                    preferenceScreen = getPreferenceScreen();
                    switchPreference = (SwitchPreference)preferenceScreen.findPreference("service_boot_switch");
                    seekBarPreference = (SeekBarPreference)preferenceScreen.findPreference("timer_kill");
                    listPref = (ListPreference)preferenceScreen.findPreference("list_pref");

                    switchPreference.setEnabled(false);
                    seekBarPreference.setEnabled(false);
                    listPref.setEnabled(false);

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
        //reloadSummary(getActivity());
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(listener);
        //リスナー登録
        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
        sharedPreferences.registerOnSharedPreferenceChangeListener(onPreferenceChangeListenter);


    }

    @Override
    public void onPause() {
        super.onPause();
        //reloadSummary(getActivity());
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(listener);
        //リスナー解除
        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(onPreferenceChangeListenter);
    }

    private void reloadSummary(Context context){
        ListAdapter adapter = getPreferenceScreen().getRootAdapter();
        pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        for (int i=0;i<adapter.getCount();i++){
            Object item = adapter.getItem(i);
            if (item instanceof ListPreference){
                ListPreference preference = (ListPreference) item;
                preference.setSummary(preference.getEntry() == null ? "" : preference.getEntry());

                //二度目の起動でpreferenceのservice_switchにtrueが格納されている場合
                if(pref.getBoolean("service_switch", false)) {
                    Intent intent = new Intent(context, MemoryFreshService.class);
                    context.stopService(intent);
                    context.startService(intent);
                }
            }
        }




    }

    private SharedPreferences.OnSharedPreferenceChangeListener listener =
            new SharedPreferences.OnSharedPreferenceChangeListener() {
                public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                    reloadSummary(getActivity());

                }
            };
}
