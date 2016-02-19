package jushin.net.memoryfresh.fragment;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.widget.ListAdapter;
import com.vanniktech.vntnumberpickerpreference.VNTNumberPickerPreference;

import java.util.List;

import jushin.net.memoryfresh.R;
import jushin.net.memoryfresh.service.MemoryFreshService;


/**
 * このクラスに設定画面のアクションを記述
 */
public class SettingsPreferenceFragment extends PreferenceFragment {

    PreferenceScreen preferenceScreen;
    SwitchPreference switchPreference;
    SharedPreferences pref;
    ListPreference listPref;
    VNTNumberPickerPreference pickerPreference;
    PackageManager pm;
    PreferenceScreen preference;

    //twitterで投稿する文面
    final String message =
            "Android6.0対応、RowMemoryKillerを採用した" +
                    "スマホを軽くアプリ　memoryfresh " +
                    "https://play.google.com/store/apps/details?id=jushin.net.memoryfresh";
    //共有するアプリケーション名
    final String appName = "twitter";

    public SettingsPreferenceFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setteings_activity);

        pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        preference = (PreferenceScreen) findPreference("share_button");

        //二度目の起動でpreferenceのservice_switchにtrueが格納されている場合
        if(pref.getBoolean("service_switch", false)) {
            preferenceScreen = getPreferenceScreen();
            switchPreference = (SwitchPreference)preferenceScreen.findPreference("service_boot_switch");
            pickerPreference = (VNTNumberPickerPreference)preferenceScreen.findPreference("freeRegularlytime");
            listPref = (ListPreference)preferenceScreen.findPreference("list_pref");

            switchPreference.setEnabled(true);
            pickerPreference.setEnabled(true);
            listPref.setEnabled(true);
        }

//        //ListFragmentのサマリーに現在の設定を表示
//        ListPreference list_preference = (ListPreference)getPreferenceScreen().findPreference();
//        list_preference.setSummary(list_preference.getValue());
//
//        //共有ボタンの処理
//
//        preference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
//            @Override
//            public boolean onPreferenceChange(Preference preference, Object newValue) {
//        // Twitter連動機能
//
//
//                return false;
//            }
//        });

       PreferenceScreen preferenceScreen = (PreferenceScreen)getPreferenceScreen().findPreference("share_button");
       pref = PreferenceManager.getDefaultSharedPreferences(getActivity());

       preferenceScreen.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
        @Override
        public boolean onPreferenceClick(Preference preference) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, message);

            pm = getActivity().getPackageManager();
            List<?> activityList = pm.queryIntentActivities(shareIntent, 0);
            int len = activityList.size();
            for (int i = 0; i < len; i++) {
                ResolveInfo app = (ResolveInfo) activityList.get(i);
                if ((app.activityInfo.name.contains(appName))) {
                    ActivityInfo activity = app.activityInfo;
                    ComponentName name = new ComponentName(activity.applicationInfo.packageName, activity.name);
                    shareIntent.setComponent(name);
                    startActivity(shareIntent);
                    break;
                }
            }
            return false;
            }
       });

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
                    pickerPreference = (VNTNumberPickerPreference)preferenceScreen.findPreference("freeRegularlytime");
                    listPref = (ListPreference)preferenceScreen.findPreference("list_pref");

                    switchPreference.setEnabled(true);
                    pickerPreference.setEnabled(true);
                    listPref.setEnabled(true);

                    //Toast.makeText(this, "サービスが停止していたため起動しました", Toast.LENGTH_LONG).show();
                    final CoordinatorLayout coordinatorLayout = (CoordinatorLayout) getActivity().findViewById(R.id.coordinator);
                    Snackbar.make(getActivity().findViewById(android.R.id.content), "サービスを開始しました", Snackbar.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(getActivity(), MemoryFreshService.class);

                    getActivity().stopService(intent);

                    preferenceScreen = getPreferenceScreen();
                    switchPreference = (SwitchPreference)preferenceScreen.findPreference("service_boot_switch");
                    pickerPreference = (VNTNumberPickerPreference)preferenceScreen.findPreference("freeRegularlytime");
                    listPref = (ListPreference)preferenceScreen.findPreference("list_pref");

                    switchPreference.setEnabled(false);
                    pickerPreference.setEnabled(false);
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
