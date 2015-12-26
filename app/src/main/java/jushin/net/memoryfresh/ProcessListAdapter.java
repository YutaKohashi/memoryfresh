package jushin.net.memoryfresh;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Yuta on 2015/12/26.
 */

/**
 * アイコンとテキストを表示するためのアダプタ.
 */
public class ProcessListAdapter extends ArrayAdapter<IconTextArrayItem> {

    /**
     * XMLからViewを生成するのに使うヤツ.
     */
    private LayoutInflater inflater;

    /**
     * リストアイテムのレイアウト.
     */
    private int textViewResourceId;

    /**
     * 表示するアイテム.
     */
    private List<IconTextArrayItem> items;

    /**
     * コンストラクタ.
     */
    public ProcessListAdapter(
            Context context,
            int textViewResourceId,
            List<IconTextArrayItem> items) {
        super(context, textViewResourceId, items);

        // リソースIDと表示アイテムを保持っておく
        this.textViewResourceId = textViewResourceId;
        this.items = items;

        // ContextからLayoutInflaterを取得
        inflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE
        );
    }

    /**
     * 1アイテム分のビューを取得.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        // なにか入ってたら、それを使う
        if (convertView != null) {
            view = convertView;
        }
        // nullなら新規作成
        else {
            view = inflater.inflate(textViewResourceId, null);
        }

        // 対象のアイテムを取得
        IconTextArrayItem item = items.get(position);


        // アイコンにアレを設定
       try{
           ImageView imageView = (ImageView) view.findViewWithTag("icon");
           imageView.setImageDrawable(item.getIconResource());
       }catch(Exception e){
           Log.d("test","test");
       }


        return view;
    }
}