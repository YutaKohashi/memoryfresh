package jushin.net.memoryfresh.object;

import android.graphics.drawable.Drawable;

/**
 * Created by Yuta on 2015/12/26.
 */
public class ListItem {
    private Drawable image;
    private String textProcessName;
    private String textProcessUses;

    public Drawable getImageId() {
        return image;
    }
    public void setImageId(Drawable imageId) {
        this.image = imageId;
    }

    public String getTextProcessName() {
        return textProcessName;
    }

    public String getTextProcessUses(){
        return textProcessUses;
    }

    public void setText(String textProcessName, String textProcessUses) {
        this.textProcessName = textProcessName;
        this.textProcessUses = textProcessUses;
    }
}
