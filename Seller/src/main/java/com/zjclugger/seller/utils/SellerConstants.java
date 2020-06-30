package com.zjclugger.seller.utils;

/**
 * <br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class SellerConstants {
    public class QueryParameter {
        public static final String COMPANY_ID = "companyId";
        public static final String STATUS = "status";
        public static final String REASON = "reason";
        public static final String BILL_TYPE_ID = "dictId";
        /**
         * 待完善、待校对、待入账
         */
        public static final String PROOF_STATUS = "proofStatus";
        public static final String UPLOAD_DATE = "uploadDate";
    }

    public class Keywords {
        public static final String KEY_ORIGINAL_BILL_ID = "original_bill_id";
        public static final String KEY_ORIGINAL_UPLOADER = "original_bill_uploader";
        public static final String KEY_ORIGINAL_UPLOAD_DATE = "original_bill_upload_date";
        public static final String KEY_SEARCH_CONDITION = "search_condition";
        public static final String KEY_SEARCH_TEXT = "search_text";
        public static final String KEY_SEARCH_START_DATE = "search_start_date";
        public static final String KEY_SEARCH_ENTRY_DATE = "search_entry_date";

        //for seller
        public static final String KEY_GOODS_MANAGE = "goods_manage";
        public static final String KEY_GOODS_MANAGE_READ_ONLY = "goods_manage_read_only";
        public static final String KEY_GOODS_ID = "goods_id";
        public static final String KEY_IS_JOINED = "is_joined";
    }

}