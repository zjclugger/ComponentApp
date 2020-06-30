package com.zjclugger.buyer.utils;

/**
 * <br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class BuyerConstants {
    public class QueryParameter {
    }

    public class Keywords {
        //for buyer
        public static final String KEY_SHOP_INFO = "shop_info";
        public static final String KEY_SHOP_DETAIL = "shop_detail";
        public static final String KEY_ORDER_SHOP_LIST = "order_shop_list";
        public static final String KEY_ORDER_SHOP_GOODS_LIST = "order_shop_goods_list";
        public static final String KEY_ORDER_TOTAL_PRICE = "order_total_price";
        public static final String KEY_ORDER_STATUS = "order_status";
    }

    public class OrderStatus {
        public static final int WAIT_PAY = 30000;
        public static final int WAIT_DELIVERY = 30001;
        public static final int WAIT_RECEIVE = 30002;
        public static final int COMPLETED = 30003;
    }
}