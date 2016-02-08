package jushin.net.memoryfresh.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.Button;
import android.widget.RemoteViews;

import jushin.net.memoryfresh.R;
import jushin.net.memoryfresh.activity.WidgetActivity;

/**
 * onEnabled()
 * AppWidgetが作成される際に呼ばれます。
 * 同じAppWidgetを複数起動した際には、初回のみ呼ばれます。
 *  全体的な初期化処理が必要な場合はここに記述します。
 *
 * onUpdate()
 * AppWidgetが更新される際に呼ばれます。
 * updatePeriodMillis等で更新間隔を設定していれば、そのタイミングで呼ばれます。
 * また、AppWidgetを起動した際にも一度呼ばれます。
 *
 * onDeleted()
 * AppWidgetが削除された際に呼ばれます。
 * 終了処理が必要な場合はここに記述します。
 *
 * onDisabled()
 * AppWidgetが全て削除された際に呼ばれます。
 * 全体的な終了処理が必要な場合はここに記述します。
 *
 * onReceive()
 * アクションを受け取り、AppWidgetProviderの各メソッドの呼び出しを処理します。
 **/
public class MemoryFreshWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                           int[] appWidgetIds) {
        // TODO Auto-generated method stub
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        // アクティビティの指定
        Intent intent = new Intent(context,WidgetActivity.class);
        // PendingIntentの取得
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.memory_fresh_widget_layout);
        // インテントによるアクティビティ起動
        remoteViews.setOnClickPendingIntent(R.id.memory_fresh_button, pendingIntent);
        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
        }
}

