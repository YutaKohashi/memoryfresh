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

    private PieChart pieChart;//グラフの設定に使用
    private ArrayList<Entry> yVals;//グラフの値
    private ArrayList<String> xVals;//値の名前
    private ArrayList<Integer> colors;//色
    private PieDataSet dataSet;

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
        pieChart.getLegend().setEnabled(false);   //
        pieChart.setDescription(str);
        pieChart.setData(createPieChartData());

    }

    //グラフの表示
    public void strart(String[] name, float[] data, String info,boolean flg){


        graphData(name, data);
        graphSettings(info);
        graphColors(name.length);

        // 更新
        pieChart.invalidate();

        // アニメーション
        if(flg){
            pieChart.animateXY(2000, 1000); // 表示アニメーション
        }

    }

    //グラフの項目の名前と値を設定
    private void graphData(String[] prossesName,float[] prossesSize){

        yVals = new ArrayList<>();//グラフの値
        xVals = new ArrayList<>();//値の名前

        for(String name : prossesName){
            xVals.add(name);//名前の追加
        }

        for(float size : prossesSize){
            yVals.add(new Entry(size,0));//値の追加
        }
    }

    private void graphColors(int maxLength){

        colors = new ArrayList<Integer>();//色

        for (int i = 0;i < maxLength;i++ ){
            colors.add(ColorTemplate.JOYFUL_COLORS[i]);

        }

        dataSet.setColors(colors);

        dataSet.setDrawValues(true);//グラフに値を表示

    }


    //pieChartのデータ設定
    private PieData createPieChartData() {

        dataSet = new PieDataSet(yVals, "");
        dataSet.setSliceSpace(4f);//グラフの値の表示の間のスペース
        dataSet.setSelectionShift(1f);//円グラフの大きさ(画面に対する)：値が小さいほど大きい

        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());

        // テキストの設定
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.WHITE);
        return data;

    }

}
