package jushin.net.memoryfresh;

import android.graphics.drawable.Drawable;

/**
 * Created by Yuta on 2015/12/26.
 */
public class IconTextArrayItem {

    /** 表示するアイコンの */
    private Drawable iconResource;

    /** 表示するテキスト. */
    private String text;

    public IconTextArrayItem(Drawable iconResource, String text){
        this.iconResource = iconResource;
        this.text = text;
    }

    public Drawable getIconResource() {
        return iconResource;
    }
    public void setIconResource(Drawable iconResource) {
        this.iconResource = iconResource;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
}