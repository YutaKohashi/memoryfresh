package jushin.net.memoryfresh.object;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.widget.CheckBox;

import jushin.net.memoryfresh.activity.MainActivity;

/**
 * Created by Yuta on 2016/01/27.
 */
public class AllAppsListItem {
    private Drawable image;
    private String appName;
    //private CheckBox checkBox;
    private String pacageName;

    public Drawable getImage() {
        return image;
    }

    public String getappName() {
        return appName;
    }

    public String getProcessName(){
        return pacageName;
    }




    //アイコンをセット
    public void setImage(Drawable image) {
        this.image = image;
    }
    //アプリ名をセット
    public void setAppName(String appName) {
        this.appName = appName;
    }
    //パッケージ名をセット
    public void setPackaeName(String text) {
        this.pacageName = text;
    }



//
//    public void saveCheckBoxState(CheckBox checkBox,boolean bln){
//        checkBox.setChecked(bln);
//    }
}
