package jushin.net.memoryfresh.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import jushin.net.memoryfresh.R;
import jushin.net.memoryfresh.object.AllAppsListItem;

/**
 * Created by Yuta on 2015/01/27.
 */

public class AppAllListAdapter extends ArrayAdapter<AllAppsListItem> {
    private int resourceId;
    private List<AllAppsListItem> items;
    private LayoutInflater inflater;

    public AppAllListAdapter(Context context, int resourceId, List<AllAppsListItem> items) {
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

        AllAppsListItem item = this.items.get(position);

        // アプリアイコン
        ImageView appInfoImage = (ImageView)view.findViewById(R.id.item_app_image);
        appInfoImage.setImageDrawable(item.getImage());

        // アプリ名
        TextView appInfoText = (TextView)view.findViewById(R.id.appname_text);
        appInfoText.setText(item.getappName());

        //パッケージ名
        TextView appInfo3Text = (TextView)view.findViewById(R.id.packagename_text);
        appInfo3Text.setText(item.getProcessName());

        Boolean checkBox;


        //チェックボックス
        ListView listView = (ListView) parent;
        CheckBox check = (CheckBox) view.findViewById(R.id.checkbox);
        if (listView.getChoiceMode() == ListView.CHOICE_MODE_NONE) {
            check.setVisibility(View.GONE);
        } else {
            // チェックボックスを自前で設定すると、どうしてもおかしくなる。
            // ここはListViewで管理している mCheckStates からもらうのが得策。
            check.setChecked(listView.isItemChecked(position));
            check.setVisibility(View.VISIBLE);
        }
        return view;

    }




}