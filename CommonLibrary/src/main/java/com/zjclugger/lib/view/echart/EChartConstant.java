package com.zjclugger.lib.view.echart;

public class EChartConstant {
    /**
     * echart点击事件名称
     * http://echarts.baidu.com/api.html#events
     * 可以根据具体需求进入上面官方API说明增加事件名称
     */
    public enum EChartAction {
        Resize("resize"),//调整
        Click("click"),
        DBClick("dbclick"),
        DataChanged("dataChanged"),
        DataZoom("dataZoom"),
        DataRange("dataRange"),
        LegendSelected("legendSelected"),
        MapSelected("mapSelected"),
        PieSelected("pieSelected"),
        MagicTypeChange("magicTypeChanged"),
        DataViewChanged("dataViewChanged"),
        TimelineChanged("timelineChanged");

        public String actionValue;

        EChartAction(String actionValue) {
            this.actionValue = actionValue;
        }
    }
}
