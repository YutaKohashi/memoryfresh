package jushin.net.memoryfresh;

import android.graphics.Color;
import android.util.Log;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

/**
 * Created by freedom18 on 2016/01/09.
 */


public class GraphManager {

    PieChart pieChart;//グラフの設定に使用
    ArrayList<Entry> yVals;//グラフの値
    ArrayList<String> xVals;//値の名前
    ArrayList<Integer> colors;//色
    PieDataSet dataSet;

    boolean flgColor;

    //引数 1:IDと関連化済みのPieChart,2:真ん中に穴を空けるかどうか,3:真ん中の穴の大きさ(%指定)
    public GraphManager(PieChart pieChart, boolean flg, float size){

        this.pieChart = pieChart;
        this.pieChart.setDrawHoleEnabled(flg);
        this.pieChart.setHoleRadius(size);
    }

    public void graphSettings(String str){

        pieChart.setHoleColorTransparent(true);
        pieChart.setTransparentCircleRadius(55f);
        pieChart.setRotationAngle(270);          // 開始位置の調整
        pieChart.setRotationEnabled(false);       // 回転可能かどうか
        pieChart.getLegend().setEnabled(true);   //
        pieChart.setDescription(str);
        pieChart.setData(createPieChartData());

    }

    //グラフの表示
    public void strart(){

        // 更新
        pieChart.invalidate();
        // アニメーション
//        pieChart.animateXY(2000, 1000); // 表示アニメーション
    }

    //グラフの項目の名前と値を設定
    public void graphData(String[] prossesName,float[] prossesSize){

        yVals = new ArrayList<>();//グラフの値
        xVals = new ArrayList<>();//値の名前

        for(String name : prossesName){
            xVals.add(name);//名前の追加
        }

        for(float size : prossesSize){
            yVals.add(new Entry(size,0));//値の追加
        }
    }

    public void graphColors(int maxLength){

        colors = new ArrayList<Integer>();//色


        for (int i = 0;i < maxLength;i++ ){

            colors.add(ColorTemplate.COLORFUL_COLORS[i]);
        }

        dataSet.setColors(colors);

        dataSet.setDrawValues(true);
    }


    //pieChartのデータ設定
    private PieData createPieChartData() {

        dataSet = new PieDataSet(yVals, "Data");
        dataSet.setSliceSpace(5f);
        dataSet.setSelectionShift(1f);

        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());

        // テキストの設定
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.WHITE);
        return data;

    }

    public boolean startGraph(){

        try {




            return true;
        }catch (Exception e){

            Log.e("Erorr",e.toString());
            return false;
        }

    }


}