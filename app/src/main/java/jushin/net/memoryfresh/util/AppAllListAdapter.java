package jushin.net.memoryfresh.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import jushin.net.memoryfresh.R;
import jushin.net.memoryfresh.object.ListItem;

/**
 * Created by Yuta on 2015/12/27.
 */

public class AppAllListAdapter extends ArrayAdapter<ListItem> {
    private int resourceId;
    private List<ListItem> items;
    private LayoutInflater inflater;

    public AppAllListAdapter(Context context, int resourceId, List<ListItem> items) {
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
        TextView pacageName = (TextView)view.findViewById(R.id.textName);
        pacageName.setText(item.getTextProcessName());

        //画像をセット
        ImageView appInfoImage = (ImageView)view.findViewById(R.id.item_app_image);
        appInfoImage.setImageDrawable(item.getImageId());


        // CheckBoxをセット
        //CheckBox checkBox = (CheckBox)view.findViewById(R.id.c)


//        // 先にイベントリスナーを設定
//        // ただし、onCheckedChangedの中で行われる操作は冪等性が保たれなければいけない
//        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//                // ここではitemのメンバにboolean値を代入するだけなので冪等性は保たれているとする
//                item.setSelected(isChecked);
//
//                // もしここで冪等性が保たれない操作をしたりするとバグにつながる
//                // 例えば選択された個数をメンバに記録しようとする
//                // selectedCounter += isChecked ? 1 : -1;
//                // とか
//            }
//        });


        return view;

    }




}