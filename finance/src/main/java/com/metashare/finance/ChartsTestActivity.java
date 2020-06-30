package com.zjclugger.finance;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.github.abel533.echarts.DataZoom;
import com.github.abel533.echarts.Legend;
import com.github.abel533.echarts.Toolbox;
import com.github.abel533.echarts.Tooltip;
import com.github.abel533.echarts.axis.Axis;
import com.github.abel533.echarts.axis.AxisLabel;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.DataZoomType;
import com.github.abel533.echarts.code.Magic;
import com.github.abel533.echarts.code.SeriesType;
import com.github.abel533.echarts.code.Tool;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.data.PieData;
import com.github.abel533.echarts.feature.DataView;
import com.github.abel533.echarts.feature.Feature;
import com.github.abel533.echarts.feature.MagicType;
import com.github.abel533.echarts.feature.Restore;
import com.github.abel533.echarts.feature.SaveAsImage;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Bar;
import com.github.abel533.echarts.series.Line;
import com.github.abel533.echarts.series.Pie;
import com.github.abel533.echarts.series.Series;
import com.zjclugger.lib.base.BaseActivity;
import com.zjclugger.lib.view.echart.EChartWebView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @title <br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class ChartsTestActivity extends BaseActivity {

    @BindView(R2.id.label_chart_pie)
    TextView mPieButton;
    @BindView(R2.id.label_chart_line)
    TextView mLineButton;
    @BindView(R2.id.label_chart_bar)
    TextView mBarButton;
    @BindView(R2.id.chart_web_view)
    EChartWebView mChartView;

    GsonOption mPieChartOption;
    GsonOption mLineChartOption;
    GsonOption mBarChartOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charts_test);
        ButterKnife.bind(this);

        initViews();
        initPieChartData();
        mChartView.setDataSource(new EChartWebView.DataSource() {
            @Override
            public GsonOption markChartOptions() {
                return mPieChartOption;
            }
        });
    }

    @Override
    protected boolean isDetailActivity() {
        return true;
    }

    @Override
    protected String getDetailActivityTitle() {
        return "ECharts图表学习";
    }

    private void initViews() {
        mPieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initPieChartData();
                mChartView.refreshEChartsWithOption(mPieChartOption);
            }
        });

        mBarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initBarChartData();
                mChartView.refreshEChartsWithOption(mBarChartOption);
            }
        });

        mLineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initLineChartOptions();
                mChartView.refreshEChartsWithOption(mLineChartOption);
            }
        });
    }

    private void initPieChartData() {
        /*
        折线（区域）图、柱状（条形）图: a（系列名称），b（类目值），c（数值）, d（无）
        散点图（气泡）图 : a（系列名称），b（数据名称），c（数值数组）, d（无）
        饼图、雷达图 : a（系列名称），b（数据项名称），c（数值）, d（百分比）
        弦图 : a（系列名称），b（项1名称），c（项1-项2值），d（项2名称)， e(项2-项1值)

        力导向图 :
        节点 : a（类目名称），b（节点名称），c（节点值）
        边 : a（系列名称），b（源名称-目标名称），c（边权重）， d（如果为true的话则数据来源是边）
        {Function}，传递参数列表如下：
        <Array> params : 数组内容同模板变量，[[a, b, c, d], [a1, b1, c1, d1], ...]
        <String> ticket : 异步回调标识
        <Function> callback : 异步回调，回调时需要两个参数，第一个为前面提到的ticket，第二个为填充内容html
        * */
        if (null == mPieChartOption) {
            mPieChartOption = new GsonOption();
            mPieChartOption.calculable(true);
            mPieChartOption.tooltip().trigger(Trigger.item).formatter("{a}<br/>{b}: {c} ({d}%)");

            Pie pie = new Pie();
            pie.name("本月各部门报销");
            pie.data(new PieData("市场部", 1400));
            pie.data(new PieData("售后部", 2300));
            pie.data(new PieData("项目部", 335));
            pie.data(new PieData("研发部", 270));
            pie.data(new PieData("总经办", 9200));
            pie.label().normal().formatter("{b}\\n{c}");
            pie.top(20);
            pie.bottom(20);
            pie.radius(30, 50).avoidLabelOverlap(true);
            mPieChartOption.series(pie);
        }
    }

    public void initBarChartData() {
        if (null == mBarChartOption) {
            mBarChartOption = new GsonOption();
            //title
            String text = "text";
            String subText = "subText";
            mBarChartOption.title(text, subText);
            //tooltip
            Tooltip tooltip = new Tooltip();
            tooltip.trigger(Trigger.axis);
            mBarChartOption.tooltip(tooltip);
            //toolbox
            Toolbox toolbox = new Toolbox();
            toolbox.show(true);
            Map<String, Feature> feature = new HashMap<>();
            feature.put("mark", new Feature().show(true));
            feature.put("dataView", new DataView().show(true).readOnly(false));
            feature.put("magicType", new MagicType(Magic.line, Magic.bar).show(true));
            feature.put("restore", new Restore().show(true));
            feature.put("saveAsImage", new SaveAsImage().show(false));
            toolbox.setFeature(feature);
            mBarChartOption.toolbox(toolbox);
            //calculable
            mBarChartOption.setCalculable(true);
            //legend
            String legend1 = "蒸发量";
            String legend2 = "降水量";
            String legend3 = "平均温度";
            Legend legend = new Legend();
            legend.data(legend1, legend2, legend3);
            mBarChartOption.legend(legend);
            //grid
       /*    Grid grid = new Grid();
           grid.y2(80);
            option.grid(grid);*/
            //dataZoom
            DataZoom dataZoom = new DataZoom();
            dataZoom.show(true);
            dataZoom.type(DataZoomType.slider);
            dataZoom.start(10);
            dataZoom.end(70);
            List<DataZoom> dataZooms = new ArrayList<>();
            dataZooms.add(dataZoom);
            mBarChartOption.dataZoom(dataZooms);
            //xAxis
            List<Axis> xAxis = new ArrayList<Axis>();
            CategoryAxis categoryAxis = new CategoryAxis();
            List xAxisValues = new ArrayList();
            for (int i = 1; i <= 12; i++) {
                xAxisValues.add(i + "月");
            }
            categoryAxis.setData(xAxisValues);
            xAxis.add(categoryAxis);
            mBarChartOption.xAxis(xAxis);
            //yAxis
            List<Axis> yAxis = new ArrayList<Axis>();
            {
                ValueAxis valueAxis = new ValueAxis();
                valueAxis.name("水量");
                valueAxis.axisLabel(new AxisLabel().formatter("{value} ml"));
                yAxis.add(valueAxis);
            }
            {
                ValueAxis valueAxis = new ValueAxis();
                valueAxis.name("温度");
                valueAxis.axisLabel(new AxisLabel().formatter("{value} °C"));
                yAxis.add(valueAxis);
            }
            mBarChartOption.yAxis(yAxis);

            //series
            List<Series> series = new ArrayList<>();
            //蒸发量
            Bar bar1 = new Bar();
            bar1.name(legend1).type(SeriesType.bar).yAxisIndex(0);
            List data1 = new ArrayList();
            double arrays1[] = {2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4,
                    3.3};
            for (double value : arrays1) {
                data1.add(value);
            }
            bar1.setData(data1);
            series.add(bar1);

            //降水量
            Bar bar2 = new Bar();
            bar2.name(legend2).type(SeriesType.bar).yAxisIndex(0);
            List data2 = new ArrayList();
            double arrays2[] = {2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0,
                    2.3};
            for (double value : arrays2) {
                data2.add(value);
            }
            bar2.setData(data2);
            series.add(bar2);

            //平均温度
            Line line = new Line();
            line.name(legend3).type(SeriesType.line).yAxisIndex(1);
            List data3 = new ArrayList();
            double arrays3[] = {2.0, 2.2, 3.3, 4.5, 6.3, 10.2, 20.3, 23.4, 23.0, 16.5, 12.0, 6.2};
            for (double value : arrays3) {
                data3.add(value);
            }
            line.setData(data3);
            series.add(line);
            mBarChartOption.series(series);
        }
    }

    public void initLineChartOptions() {
        if (null == mLineChartOption) {
            mLineChartOption = new GsonOption();
            mLineChartOption.legend("高度(km)与气温(°C)变化关系");

            mLineChartOption.toolbox().show(true).feature(Tool.mark, Tool.dataView,
                    new MagicType(Magic.line, Magic.bar), Tool.restore, Tool.saveAsImage);

            mLineChartOption.calculable(true);
            mLineChartOption.tooltip()
                    .trigger(Trigger.axis)
                    .formatter("Temperature : <br/>{b}km : {c}°C");

            ValueAxis valueAxis = new ValueAxis();
            valueAxis.axisLabel().formatter("{value} °C");
            mLineChartOption.xAxis(valueAxis);

            CategoryAxis categoryAxis = new CategoryAxis();
            categoryAxis.axisLine().onZero(false);
            categoryAxis.axisLabel().formatter("{value} km");
            categoryAxis.boundaryGap(false);
            categoryAxis.data(0, 10, 20, 30, 40, 50, 60, 70, 80);
            mLineChartOption.yAxis(categoryAxis);

            Line line = new Line();
            line.smooth(true).name("高度(km)与气温(°C)变化关系").data(15, -50, -56.5, -46.5, -22.1, -2.5,
                    -27.7, -55.7, -76.5).itemStyle().normal().lineStyle().shadowColor("rgba(0,0," +
                    "0,0.4)");
            mLineChartOption.series(line);
        }
    }
}