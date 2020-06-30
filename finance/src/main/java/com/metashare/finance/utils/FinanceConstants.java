package com.zjclugger.finance.utils;

/**
 * <br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class FinanceConstants {
    public class QueryParameter {
        public static final String PAYROLL_STATUS = "payrollstatus";
        public static final String COMPANY_ID = "companyId";
        public static final String STATUS = "status";
        public static final String REASON = "reason";
        public static final String BILL_TYPE_ID = "dictId";
        public static final String VOUCHER_STATUS = "voucherStatus";  //制单状态 0:待制单,1:待审核,2:待过账
        public static final String PERIOD = "period"; //会计期间（yyyy-MM）选填
        /**
         * 待完善、待校对、待入账
         */
        public static final String PROOF_STATUS = "proofStatus";
        public static final String UPLOAD_DATE = "uploadDate";
        public static final String STATISTICAL_TYPE = "type";
        public static final String STATISTICAL_TIME = "time";
        public static final String STATISTICAL_OBJECT = "object";
    }

    public class Keywords {
        public static final String KEY_BILL_DETAIL = "bill_detail";
        public static final String KEY_ORIGINAL_BILL_ID = "original_bill_id";
        public static final String KEY_ORIGINAL_BILL_DETAIL = "original_bill_detail";
        public static final String KEY_ORIGINAL_UPLOADER = "original_bill_uploader";
        public static final String KEY_ORIGINAL_UPLOAD_DATE = "original_bill_upload_date";
        public static final String KEY_SEARCH_VIEW_TYPE = "search_view_type";
        public static final String KEY_SEARCH_CONDITION = "search_condition";
        public static final String KEY_SEARCH_TEXT = "search_text";
        public static final String KEY_SEARCH_START_DATE = "search_start_date";
        public static final String KEY_SEARCH_ENTRY_DATE = "search_entry_date";
        public static final String KEY_ACCOUNT_BILL_DETAIL = "account_bill_detail";
    }

    public class SearchViewType {
        /**
         * 关键字搜索
         */
        public static final int SEARCH_KEYWORD = 4100;
        /**
         * 过滤器搜索
         */
        public static final int SEARCH_FILTER = 4101;
    }

    public class ProofStatus {
        public static final int TO_PERFECT = 0;
        public static final int TO_CHECK = 1;
        public static final int TO_ACCOUNT = 2;
        public static final int ACCOUNTED = 3;  //已入账
    }

    public class VoucherStatus {
        /**
         * 待制单
         */
        public static final int TO_ORDER = 0;
        /**
         * 待审核
         */
        public static final int TO_APPROVE = 1;
        /**
         * 待过账
         */
        public static final int TO_ACCOUNT = 2;
        /**
         * 待过账
         */
        public static final int ACCOUNTED = 3;
    }
}