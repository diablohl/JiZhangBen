package com.example.hele.jizhangben;

import android.app.Activity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * Created by hele on 2017/3/21.
 */

public class ChartsActivity extends Activity {

    private LineChartView mChart;
    private Map<String,Integer>  table=new TreeMap<>();
    private LineChartData mData;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chart_view);
        mChart=(LineChartView) findViewById(R.id.chart);
        List<CostBean> allData= (List<CostBean>) getIntent().getSerializableExtra("cost_list");
        generateValues(allData);
        generateData();
        mChart.setLineChartData(mData);
    }

    private void generateData() {
        List<Line> lines=new ArrayList<>();
        List<PointValue> values=new ArrayList<>();
        int indexX=0;

        for (Integer value : table.values()) {
            values.add(new PointValue(indexX,value));
            indexX++;
        }
        Line line =new Line(values);
        line.setColor(ChartUtils.COLORS[0]);
        line.setShape(ValueShape.CIRCLE);
        line.setPointColor(ChartUtils.COLORS[1]);
        lines.add(line);
        mData=new LineChartData(lines);

    }

    private void generateValues(List<CostBean> allData) {
        if (allData!=null){
            for (int i=0;i<allData.size();i++){
                CostBean costbean=allData.get(i);
                String costData=costbean.costDate;
                int costMoney= Integer.parseInt(costbean.costMoney);
                if (!table.containsKey(costData)){
                    table.put(costData,costMoney);
                }else {
                    int  originMoney=table.get(costMoney);
                    table.put(costData,originMoney+costMoney);
                }

            }
        }

    }
}
