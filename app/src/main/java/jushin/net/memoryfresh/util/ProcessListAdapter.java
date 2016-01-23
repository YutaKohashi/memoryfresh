package jushin.net.memoryfresh.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import jushin.net.memoryfresh.R;
import jushin.net.memoryfresh.object.ListItem;

/**
 * Created by Yuta on 2015/12/27.
 */

public class ProcessListAdapter extends ArrayAdapter<ListItem> {
    private int resourceId;
    private List<ListItem> items;
    private LayoutInflater inflater;

    public ProcessListAdapter(Context context, int resourceId, List<ListItem> items) {
        super(context, resourceId, items);

        this.resourceId = resourceId;
        this.items = items;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView != null) {
            view = convertView;
        } else {
            view = this.inflater.inflate(this.resourceId, null);
        }

        ListItem item = this.items.get(position);

        // テキストをセット
        TextView appInfoText = (TextView)view.findViewById(R.id.textName);
        appInfoText.setText(item.getTextProcessName());

        TextView appInfo3Text = (TextView)view.findViewById(R.id.textProcessUses);
        appInfo3Text.setText(item.getTextProcessUses());

        // アイコンをセット
        ImageView appInfoImage = (ImageView)view.findViewById(R.id.item_image);
        appInfoImage.setImageDrawable(item.getImageId());

        return view;

    }



}