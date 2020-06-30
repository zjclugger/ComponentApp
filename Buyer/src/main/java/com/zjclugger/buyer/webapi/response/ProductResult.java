package com.zjclugger.buyer.webapi.response;

import com.wuhenzhizao.sku.bean.Sku;

import java.util.List;

/**
 * TODO:暂时使用这个对象来实现SKU
 */
public class ProductResult {
    public static String PRODUCT_JSON_STRING = "{\"id\":\"101\"," +
            "\"name\":\"羊毛呢2017秋冬装新款女装韩版棉服宽松棉袄处理短款棉衣外套反季\",\"status\":\"1\"," +
            "\"mainImage\":\"https://img.alicdn" +
            ".com/bao/uploaded/i3/1754317604/TB1H1DViesAV1JjSZFsXXadZXXa_!!0-item_pic.jpg_b" +
            ".jpg\",\"sellingPrice\":26800,\"originPrice\":36800,\"currencyUnit\":\"¥\"," +
            "\"measurementUnit\":\"件\",\"saleQuantity\":1335,\"stockQuantity\":10998," +
            "\"medias\":[\"https://img.alicdn" +
            ".com/imgextra/i3/1754317604/TB2UkuShvBNTKJjy1zdXXaScpXa_!!1754317604.jpg\"," +
            "\"https://img.alicdn.com/imgextra/i4/1754317604/TB2JNyYhvBNTKJjSszeXXcu2VXa_" +
            "!!1754317604.jpg\",\"https://img.alicdn" +
            ".com/imgextra/i4/1754317604/TB2ZFJxiaagSKJjy0FhXXcrbFXa_!!1754317604.jpg\"," +
            "\"https://img.alicdn.com/imgextra/i3/1754317604/TB2" +
            ".ohtijihSKJjy0FfXXbGzFXa_!!1754317604.jpg\"],\"skus\":[{\"id\":\"1\"," +
            "\"itemId\":\"101\",\"mainImage\":\"https://img.alicdn" +
            ".com/imgextra/i3/1754317604/TB2UkuShvBNTKJjy1zdXXaScpXa_!!1754317604.jpg\"," +
            "\"originPrice\":36800,\"sellingPrice\":26800,\"stockQuantity\":21," +
            "\"attributes\":[{\"key\":\"颜色\",\"value\":\"粉色\"},{\"key\":\"尺码\"," +
            "\"value\":\"S\"}]},{\"id\":\"2\",\"itemId\":\"101\",\"mainImage\":\"https://img" +
            ".alicdn.com/imgextra/i3/1754317604/TB2UkuShvBNTKJjy1zdXXaScpXa_!!1754317604.jpg\"," +
            "\"originPrice\":36800,\"sellingPrice\":26800,\"stockQuantity\":7," +
            "\"attributes\":[{\"key\":\"颜色\",\"value\":\"粉色\"},{\"key\":\"尺码\"," +
            "\"value\":\"M\"}]},{\"id\":\"3\",\"itemId\":\"101\",\"mainImage\":\"https://img" +
            ".alicdn.com/imgextra/i3/1754317604/TB2UkuShvBNTKJjy1zdXXaScpXa_!!1754317604.jpg\"," +
            "\"originPrice\":36800,\"sellingPrice\":26800,\"stockQuantity\":74," +
            "\"attributes\":[{\"key\":\"颜色\",\"value\":\"粉色\"},{\"key\":\"尺码\"," +
            "\"value\":\"L\"}]},{\"id\":\"4\",\"itemId\":\"101\",\"mainImage\":\"https://img" +
            ".alicdn.com/imgextra/i3/1754317604/TB2UkuShvBNTKJjy1zdXXaScpXa_!!1754317604.jpg\"," +
            "\"originPrice\":36800,\"sellingPrice\":26800,\"stockQuantity\":0," +
            "\"attributes\":[{\"key\":\"颜色\",\"value\":\"粉色\"},{\"key\":\"尺码\"," +
            "\"value\":\"XL\"}]},{\"id\":\"5\",\"itemId\":\"101\",\"mainImage\":\"https://img" +
            ".alicdn.com/imgextra/i1/1754317604/TB2MlHNmgoQMeJjy0FoXXcShVXa_!!1754317604.jpg\"," +
            "\"originPrice\":36800,\"sellingPrice\":26800,\"stockQuantity\":254," +
            "\"attributes\":[{\"key\":\"颜色\",\"value\":\"灰色\"},{\"key\":\"尺码\"," +
            "\"value\":\"S\"}]},{\"id\":\"6\",\"itemId\":\"101\",\"mainImage\":\"https://img" +
            ".alicdn.com/imgextra/i1/1754317604/TB2MlHNmgoQMeJjy0FoXXcShVXa_!!1754317604.jpg\"," +
            "\"originPrice\":36800,\"sellingPrice\":26800,\"stockQuantity\":93," +
            "\"attributes\":[{\"key\":\"颜色\",\"value\":\"灰色\"},{\"key\":\"尺码\"," +
            "\"value\":\"M\"}]},{\"id\":\"7\",\"itemId\":\"101\",\"mainImage\":\"https://img" +
            ".alicdn.com/imgextra/i1/1754317604/TB2MlHNmgoQMeJjy0FoXXcShVXa_!!1754317604.jpg\"," +
            "\"originPrice\":36800,\"sellingPrice\":26800,\"stockQuantity\":137," +
            "\"attributes\":[{\"key\":\"颜色\",\"value\":\"灰色\"},{\"key\":\"尺码\"," +
            "\"value\":\"L\"}]},{\"id\":\"8\",\"itemId\":\"101\",\"mainImage\":\"https://img" +
            ".alicdn.com/imgextra/i1/1754317604/TB2MlHNmgoQMeJjy0FoXXcShVXa_!!1754317604.jpg\"," +
            "\"originPrice\":36800,\"sellingPrice\":31800,\"stockQuantity\":311," +
            "\"attributes\":[{\"key\":\"颜色\",\"value\":\"灰色\"},{\"key\":\"尺码\"," +
            "\"value\":\"XXL\"}]},{\"id\":\"9\",\"itemId\":\"101\",\"mainImage\":\"https://img" +
            ".alicdn.com/imgextra/i1/1754317604/TB2WDJTiX9gSKJjSspbXXbeNXXa_!!1754317604.jpg\"," +
            "\"originPrice\":36800,\"sellingPrice\":26800,\"stockQuantity\":305," +
            "\"attributes\":[{\"key\":\"颜色\",\"value\":\"黑色\"},{\"key\":\"尺码\"," +
            "\"value\":\"S\"}]},{\"id\":\"10\",\"itemId\":\"101\",\"mainImage\":\"https://img" +
            ".alicdn.com/imgextra/i1/1754317604/TB2WDJTiX9gSKJjSspbXXbeNXXa_!!1754317604.jpg\"," +
            "\"originPrice\":36800,\"sellingPrice\":26800,\"stockQuantity\":0," +
            "\"attributes\":[{\"key\":\"颜色\",\"value\":\"黑色\"},{\"key\":\"尺码\"," +
            "\"value\":\"M\"}]},{\"id\":\"11\",\"itemId\":\"101\",\"mainImage\":\"https://img" +
            ".alicdn.com/imgextra/i1/1754317604/TB2WDJTiX9gSKJjSspbXXbeNXXa_!!1754317604.jpg\"," +
            "\"originPrice\":36800,\"sellingPrice\":26800,\"stockQuantity\":178," +
            "\"attributes\":[{\"key\":\"颜色\",\"value\":\"黑色\"},{\"key\":\"尺码\"," +
            "\"value\":\"L\"}]},{\"id\":\"12\",\"itemId\":\"101\",\"mainImage\":\"https://img" +
            ".alicdn.com/imgextra/i1/1754317604/TB2WDJTiX9gSKJjSspbXXbeNXXa_!!1754317604.jpg\"," +
            "\"originPrice\":36800,\"sellingPrice\":26800,\"stockQuantity\":0," +
            "\"attributes\":[{\"key\":\"颜色\",\"value\":\"黑色\"},{\"key\":\"尺码\"," +
            "\"value\":\"XL\"}]},{\"id\":\"13\",\"itemId\":\"101\",\"mainImage\":\"https://img" +
            ".alicdn.com/imgextra/i3/1754317604/TB2GDbcmgoQMeJjy1XaXXcSsFXa_!!1754317604.jpg\"," +
            "\"originPrice\":36800,\"sellingPrice\":26800,\"stockQuantity\":360," +
            "\"attributes\":[{\"key\":\"颜色\",\"value\":\"绿色\"},{\"key\":\"尺码\"," +
            "\"value\":\"S\"}]},{\"id\":\"14\",\"itemId\":\"101\",\"mainImage\":\"https://img" +
            ".alicdn.com/imgextra/i3/1754317604/TB2GDbcmgoQMeJjy1XaXXcSsFXa_!!1754317604.jpg\"," +
            "\"originPrice\":36800,\"sellingPrice\":26800,\"stockQuantity\":339," +
            "\"attributes\":[{\"key\":\"颜色\",\"value\":\"绿色\"},{\"key\":\"尺码\"," +
            "\"value\":\"M\"}]},{\"id\":\"15\",\"itemId\":\"101\",\"mainImage\":\"https://img" +
            ".alicdn.com/imgextra/i3/1754317604/TB2GDbcmgoQMeJjy1XaXXcSsFXa_!!1754317604.jpg\"," +
            "\"originPrice\":36800,\"sellingPrice\":26800,\"stockQuantity\":313," +
            "\"attributes\":[{\"key\":\"颜色\",\"value\":\"绿色\"},{\"key\":\"尺码\"," +
            "\"value\":\"L\"}]},{\"id\":\"16\",\"itemId\":\"101\",\"mainImage\":\"https://img" +
            ".alicdn.com/imgextra/i3/1754317604/TB2GDbcmgoQMeJjy1XaXXcSsFXa_!!1754317604.jpg\"," +
            "\"originPrice\":36800,\"sellingPrice\":26800,\"stockQuantity\":285," +
            "\"attributes\":[{\"key\":\"颜色\",\"value\":\"绿色\"},{\"key\":\"尺码\"," +
            "\"value\":\"XL\"}]},{\"id\":\"17\",\"itemId\":\"101\",\"mainImage\":\"https://img" +
            ".alicdn.com/imgextra/i3/1754317604/TB2GDbcmgoQMeJjy1XaXXcSsFXa_!!1754317604.jpg\"," +
            "\"originPrice\":36800,\"sellingPrice\":30800,\"stockQuantity\":317," +
            "\"attributes\":[{\"key\":\"颜色\",\"value\":\"绿色\"},{\"key\":\"尺码\"," +
            "\"value\":\"XXL\"}]},{\"id\":\"18\",\"itemId\":\"101\",\"mainImage\":\"https://img" +
            ".alicdn.com/imgextra/i3/1754317604/TB2UkuShvBNTKJjy1zdXXaScpXa_!!1754317604.jpg\"," +
            "\"originPrice\":36800,\"sellingPrice\":28800,\"stockQuantity\":327," +
            "\"attributes\":[{\"key\":\"颜色\",\"value\":\"粉色(中长款)\"},{\"key\":\"尺码\"," +
            "\"value\":\"L\"}]},{\"id\":\"18\",\"itemId\":\"101\",\"mainImage\":\"https://img" +
            ".alicdn.com/imgextra/i3/1754317604/TB2UkuShvBNTKJjy1zdXXaScpXa_!!1754317604.jpg\"," +
            "\"originPrice\":36800,\"sellingPrice\":28800,\"stockQuantity\":301," +
            "\"attributes\":[{\"key\":\"颜色\",\"value\":\"粉色(中长款)\"},{\"key\":\"尺码\"," +
            "\"value\":\"XL\"}]},{\"id\":\"18\",\"itemId\":\"101\",\"mainImage\":\"https://img" +
            ".alicdn.com/imgextra/i3/1754317604/TB2UkuShvBNTKJjy1zdXXaScpXa_!!1754317604.jpg\"," +
            "\"originPrice\":36800,\"sellingPrice\":31800,\"stockQuantity\":215," +
            "\"attributes\":[{\"key\":\"颜色\",\"value\":\"粉色(中长款)\"},{\"key\":\"尺码\"," +
            "\"value\":\"XXL\"}]},{\"id\":\"18\",\"itemId\":\"101\",\"mainImage\":\"https://img" +
            ".alicdn.com/imgextra/i1/1754317604/TB2MlHNmgoQMeJjy0FoXXcShVXa_!!1754317604.jpg\"," +
            "\"originPrice\":36800,\"sellingPrice\":28800,\"stockQuantity\":329," +
            "\"attributes\":[{\"key\":\"颜色\",\"value\":\"灰色(中长款)\"},{\"key\":\"尺码\"," +
            "\"value\":\"L\"}]},{\"id\":\"18\",\"itemId\":\"101\",\"mainImage\":\"https://img" +
            ".alicdn.com/imgextra/i1/1754317604/TB2MlHNmgoQMeJjy0FoXXcShVXa_!!1754317604.jpg\"," +
            "\"originPrice\":36800,\"sellingPrice\":28800,\"stockQuantity\":328," +
            "\"attributes\":[{\"key\":\"颜色\",\"value\":\"灰色(中长款)\"},{\"key\":\"尺码\"," +
            "\"value\":\"XL\"}]},{\"id\":\"18\",\"itemId\":\"101\",\"mainImage\":\"https://img" +
            ".alicdn.com/imgextra/i1/1754317604/TB2MlHNmgoQMeJjy0FoXXcShVXa_!!1754317604.jpg\"," +
            "\"originPrice\":36800,\"sellingPrice\":31800,\"stockQuantity\":0," +
            "\"attributes\":[{\"key\":\"颜色\",\"value\":\"灰色(中长款)\"},{\"key\":\"尺码\"," +
            "\"value\":\"XXL\"}]},{\"id\":\"18\",\"itemId\":\"101\",\"mainImage\":\"https://img" +
            ".alicdn.com/imgextra/i1/1754317604/TB2WDJTiX9gSKJjSspbXXbeNXXa_!!1754317604.jpg\"," +
            "\"originPrice\":36800,\"sellingPrice\":28800,\"stockQuantity\":328," +
            "\"attributes\":[{\"key\":\"颜色\",\"value\":\"黑色(中长款)\"},{\"key\":\"尺码\"," +
            "\"value\":\"L\"}]},{\"id\":\"18\",\"itemId\":\"101\",\"mainImage\":\"https://img" +
            ".alicdn.com/imgextra/i1/1754317604/TB2WDJTiX9gSKJjSspbXXbeNXXa_!!1754317604.jpg\"," +
            "\"originPrice\":36800,\"sellingPrice\":28800,\"stockQuantity\":328," +
            "\"attributes\":[{\"key\":\"颜色\",\"value\":\"黑色(中长款)\"},{\"key\":\"尺码\"," +
            "\"value\":\"XL\"}]},{\"id\":\"18\",\"itemId\":\"101\",\"mainImage\":\"https://img" +
            ".alicdn.com/imgextra/i1/1754317604/TB2WDJTiX9gSKJjSspbXXbeNXXa_!!1754317604.jpg\"," +
            "\"originPrice\":36800,\"sellingPrice\":31800,\"stockQuantity\":327," +
            "\"attributes\":[{\"key\":\"颜色\",\"value\":\"黑色(中长款)\"},{\"key\":\"尺码\"," +
            "\"value\":\"XXL\"}]},{\"id\":\"18\",\"itemId\":\"101\",\"mainImage\":\"https://img" +
            ".alicdn.com/imgextra/i3/1754317604/TB2GDbcmgoQMeJjy1XaXXcSsFXa_!!1754317604.jpg\"," +
            "\"originPrice\":36800,\"sellingPrice\":28800,\"stockQuantity\":325," +
            "\"attributes\":[{\"key\":\"颜色\",\"value\":\"绿色(中长款)\"},{\"key\":\"尺码\"," +
            "\"value\":\"L\"}]},{\"id\":\"18\",\"itemId\":\"101\",\"mainImage\":\"https://img" +
            ".alicdn.com/imgextra/i3/1754317604/TB2GDbcmgoQMeJjy1XaXXcSsFXa_!!1754317604.jpg\"," +
            "\"originPrice\":36800,\"sellingPrice\":28800,\"stockQuantity\":327," +
            "\"attributes\":[{\"key\":\"颜色\",\"value\":\"绿色(中长款)\"},{\"key\":\"尺码\"," +
            "\"value\":\"XL\"}]},{\"id\":\"18\",\"itemId\":\"101\",\"mainImage\":\"https://img" +
            ".alicdn.com/imgextra/i3/1754317604/TB2GDbcmgoQMeJjy1XaXXcSsFXa_!!1754317604.jpg\"," +
            "\"originPrice\":36800,\"sellingPrice\":31800,\"stockQuantity\":328," +
            "\"attributes\":[{\"key\":\"颜色\",\"value\":\"绿色(中长款)\"},{\"key\":\"尺码\"," +
            "\"value\":\"XXL\"}]}]}";
    private String id;
    private String name;
    private String status;
    private String mainImage;
    private long sellingPrice;
    private long originPrice;
    private String currencyUnit;
    private String measurementUnit;
    private int saleQuantity;
    private int stockQuantity;
    private List<String> medias;
    private List<Sku> skus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public long getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(long sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public long getOriginPrice() {
        return originPrice;
    }

    public void setOriginPrice(long originPrice) {
        this.originPrice = originPrice;
    }

    public String getCurrencyUnit() {
        return currencyUnit;
    }

    public void setCurrencyUnit(String currencyUnit) {
        this.currencyUnit = currencyUnit;
    }

    public String getMeasurementUnit() {
        return measurementUnit;
    }

    public void setMeasurementUnit(String measurementUnit) {
        this.measurementUnit = measurementUnit;
    }

    public int getSaleQuantity() {
        return saleQuantity;
    }

    public void setSaleQuantity(int saleQuantity) {
        this.saleQuantity = saleQuantity;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public List<String> getMedias() {
        return medias;
    }

    public void setMedias(List<String> medias) {
        this.medias = medias;
    }

    public List<Sku> getSkus() {
        return skus;
    }

    public void setSkus(List<Sku> skus) {
        this.skus = skus;
    }
}
