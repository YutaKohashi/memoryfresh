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
    private String textProcessName;
    private CheckBox checkBox;

    public Drawable getImageId() {
        return image;
    }
    public void setImageId(Drawable imageId) {
        this.image = imageId;
    }

    public String getTextProcessName() {
        return textProcessName;
    }

    public boolean getcheckboxstatus(){

        Boolean flag;

        if(checkBox.isEnabled()){
            flag = true;
        }else{
            flag = false;
        }

        return flag;
    }
    public CheckBox getcheckbox(){

        return checkBox;
    }

    public void setText(String textProcessName) {
        this.textProcessName = textProcessName;
    }

    public void saveCheckBoxState(CheckBox checkBox,boolean bln){
        checkBox.setChecked(bln);
    }
}
