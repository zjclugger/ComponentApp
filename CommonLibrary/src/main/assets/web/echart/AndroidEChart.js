var androidChart;

$(function() {
    init();
});

function printDebugMessage(msg){
    //print log
     Android.printDebugMessage(msg);
}

function showMessage(msg){
    //toast message
     Android.showMessage(msg);
}

function init() {
    getAndroidChart();
    var chartJson = initChartOptions();
    loadECharts(chartJson);
}

function getAndroidChart() {
    if(androidChart != undefined) {
        return androidChart;
    }
    printDebugMessage("init chart view");
    var androidChartDoc = document.getElementById("androidChart");
    /*
     *设置lineChart的高度为Android中控件WebView的高度（达到不能滑动且显示完全的效果）
     *var height = document.documentElement.clientHeight;
     *var height = window.innerHeight
     *这2个获取高度建议选择第二个
     */
    var height = window.innerHeight;
    printDebugMessage("height=" + height.toString());
    $(androidChartDoc).css('height', height);

printDebugMessage("===========start============="+androidChartDoc);
    androidChart = echarts.init(androidChartDoc);
    printDebugMessage("===========end============="+androidChartDoc);
    return androidChart;
}

function initChartOptions() {
    /*必须加JOSN.parse 转换数据类型
    *Android表示JAVA类TEChartWebView中的addJavascriptInterface(new TEChartWebView.WebAppEChartInterface(getContext()), "Android");中的Android
    *getChartOptions为WebAppEChartInterface接口中的方法
    */
    var chartJson = Android.getChartOptions();
    printDebugMessage("initChartOptions="+chartJson);
    return chartJson;
}

/**
 * 构建动态图表
 */
function loadECharts(chartJson) {
    printDebugMessage("load echarts json string")+chartJson;

    var option = JSON.parse(chartJson);
    option = preTask(option);
    getAndroidChart().setOption(option);
   // hideLoading();
    androidChartHideLoading();
     //printDebugMessage("load--hide loading");
    //getandroidChart().setOption(makePieStaticOptions());//直接在js中获取静态数据
}

/*
 *刷新图表
 */
function refreshEChartsWithOption(chartJson) {
    printDebugMessage("refreshEChartsWithOption"+chartJson);
    var option = JSON.parse(chartJson);
    option = preTask(option);
    getAndroidChart().setOption(option, true);//刷新，带上第二个参数true
}

/*
 *添加图表事件响应监听
 */
function addEChartActionHandler(eventName) {
    printDebugMessage("addEchartActionHandler:" + eventName);
    var ecConfig = echarts.config;
    getAndroidChart().on(eventName, addEchartViewAction);
}

function addEChartViewAction(param) {
    printDebugMessage("add echart view action:" + param);
    alert(JSON.stringify(param));
    Android.addEChartActionHandlerResponseResult(JSON.stringify(param));
}

/*
 *移除图表事件响应监听
 */
function removeEChartActionHandler(eventName) {
    printDebugMessage("remove echart action handler:" + eventName);
    getAndroidChart().un(name, removeEChartViewAction);
}

function removeEChartViewAction(param) {
    printDebugMessage("remove echart view action:" + param);
    Android.removeEChartActionHandlerResponseResult(JSON.stringify(param));
}

function androidChartShowLoading() {
    getAndroidChart().showLoading();
}

function androidChartHideLoading() {
    getAndroidChart().hideLoading();
}

function preTask(obj) {
    var result;
    if(typeof(obj) == 'object') {
        if(obj instanceof Array) {
            result = new Array();
            for (var i = 0, len = obj.length; i < len ; i++) {
                 result.push(preTask(obj[i]));
            }
            return result;
        } else if(obj instanceof RegExp){
            return obj;
        } else {
            result = new Object();
            for (var prop in obj) {
                result[prop] = preTask(obj[prop]);
            }
            return result;
        }
    } else if(typeof(obj) == 'string'){
        try {
            if(typeof(eval(obj)) == 'function'){
                return eval(obj);
            } else if (typeof(eval(obj) == 'object') && (eval(obj) instanceof Array || eval(obj) instanceof CanvasGradient)) {
                return eval(obj);
            }
        }catch(e) {
            return obj;
        }
        return obj;
    } else {
        return obj;
    }
}

/*示例
 *静态JSON配置信息
 */
function makeStaticOptions(){
    var option = {
        title : {
            text : '时间坐标折线图',
            subtext : 'dataZoom支持'
        },
        tooltip : {
            trigger: 'item',
            formatter : function (params) {
                var date = new Date(params.value[0]);
                data = date.getFullYear() + '-'
                       + (date.getMonth() + 1) + '-'
                       + date.getDate() + ' '
                       + date.getHours() + ':'
                       + date.getMinutes();
                return data + '<br/>'
                       + params.value[1] + ', '
                       + params.value[2];
            }
        },
        toolbox: {
            show : true,
            feature : {
                mark : {show: true},
                dataView : {show: true, readOnly: false},
                restore : {show: true},
                saveAsImage : {show: true}
            }
        },
        dataZoom: {
            show: true,
            start : 70
        },
        legend : {
            data : ['series1']
        },
        grid: {
            y2: 80
        },
        xAxis : [
            {
                type : 'time',
                splitNumber:10
            }
        ],
        yAxis : [
            {
                type : 'value'
            }
        ],
        series : [
            {
                name: 'series1',
                type: 'line',
                showAllSymbol: true,
                symbolSize: function (value){
                    return Math.round(value[2]/10) + 2;
                },

                data: (function () {
                    var d = [];
                    var len = 0;
                    var now = new Date();
                    var value;
                    while (len++ < 200) {
                        d.push([
                            new Date(2014, 9, 1, 0, len * 10000),
                            (Math.random()*30).toFixed(2) - 0,
                            (Math.random()*100).toFixed(2) - 0
                        ]);
                    }
                    return d;
                })()
            }
        ]
    };
    return option;
}

 function makePieStaticOptions(){
        var option = {
            title : {
                text : '时间坐标折线图',
                subtext : 'dataZoom支持'
            },
            tooltip : {
                trigger: 'item',
                formatter:'{a} <br/>{b}: {c} ({d}%)'
            },
            series : [
                {
                    name: '来源',
                    type: 'pie',
                    radius: ['30%', '50%'],
                    label: {

                         normal: {
                         backgroundColor: '#eee000',
                            formatter: '{b}\n{c}({d}%)',
                            show: true,
                            lineHeight:10,
                            position: 'left',
                            textStyle: {
                                fontSize: '12',
                                lineHeight:10,
                                fontWeight: 'bold'
                            }
                        },
                    },
                    data: (function () {
                        var d = [
                            {value: 335, name: '直达'},
                            {value: 310, name: '邮件营销'},
                            {value: 234, name: '联盟广告'},
                            {value: 135, name: '视频广告'},
                            {value: 1048, name: '百度'},
                            {value: 251, name: '谷歌'},
                            {value: 147, name: '必应'},
                            {value: 102, name: '其他'}
                        ];
                        return d;
                    })()
                }
            ]
        };
        return option;
 }