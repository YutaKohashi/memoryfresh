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

    static final String btnFilter = "jushin.net.memoryfresh.widget.action.ACTION_WIDGET_UPDATE";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {


        RemoteViews views = new RemoteViews(context.getPackageName(),R.layout.memory_fresh_widget_layout);

        // クリック時に発行するIntentを設定する
        Intent btnIntent = new Intent(context,MemoryFreshWidgetProvider.class);
        btnIntent.setAction(btnFilter);
        btnIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

        PendingIntent btnPending = PendingIntent.getBroadcast(context,0,btnIntent,0);
        views.setOnClickPendingIntent(R.id.memory_fresh_button,btnPending);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }

    }

    @Override
    public void onReceive(Context context,Intent intent){
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

        if(btnFilter.equals(intent.getAction())){
            RemoteViews views = new RemoteViews(context.getPackageName(),R.layout.memory_fresh_widget_layout);
            int appWidgetId = intent.getIntExtra(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, -1);

            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.ic_launcher);
            views.setImageViewBitmap(R.id.memory_fresh_button,bitmap);
            appWidgetManager.updateAppWidget(appWidgetId, views);
            appWidgetManager.updateAppWidget(appWidgetId,views);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

