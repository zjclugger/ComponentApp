package com.zjclugger.lib.utils;

/**
 * <br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class Constants {
    public class Result {
        public static final int CODE_SUCCESS = 0;
        public static final String MSG_SUCCESS = "成功";
    }

    /**
     * 传递数据时的KEY值
     */
    public class Keywords {
        public static final String KEY_HR_STATUS_LIST = "hr_status_list";
        public static final String KEY_STAFF_DETAIL = "staff_detail";
        public static final String KEY_PERSONAL_PAYROLL_ID = "personal_payroll_id";
        public static final String KEY_COMPANY_PAYROLL_ID = "company_payroll_id";
        public static final String KEY_SELECTED_PICTURE = "selected_picture_list";
        /**
         * 通讯录显示模式(选择模式/查看模式)
         */
        public static final String KEY_CONTACTS_MODE = "contacts_mode";
        public static final String KEY_CONTACTS_LIST = "contacts_list";
        public static final String KEY_DEPARTMENT_LIST = "department_list";
        public static final String KEY_DEPARTMENT_CONTACTS_LIST = "depar_contacts_list";
        /**
         * 上传下载
         */
        public static final String KEY_UPLOAD_FILE_PATH_LIST = "file_path_list";
        public static final String KEY_UPLOAD_FILE_LIST = "file_list";
        public static final String KEY_UPLOAD_FILE_OTHERS = "file_upload_others";
        public static final String KEY_UPLOAD_SUBSCRIBE = "upload_subscribe";
        public static final String KEY_UPLOAD_MEDIA_TYPE = "upload_media_type";

        //票据报销原由
        public static final String KEY_BILL_REASON = "key_bill_reason";

        public static final String KEY_DEPART_SELECTED = "depart_selected";
    }

    /**
     * 自定义的广播
     */
    public class Action {
        private static final String PREFIX = "com.zjclugger";
    }

    public class Preferences {
        public static final String KEY_LOGIN_COMPANY = "login_company";
        public static final String KEY_LOGIN_USER = "login_user";
        public static final String KEY_LOGIN_PASSWORD = "login_password";
        public static final String KEY_LOGIN_TOKEN = "login_token";
    }

    public class KeyCode {
        /**
         * 上传文件
         */
        public static final int REQUEST_CODE_UPLOAD = 8000;
        /**
         * 刷新
         */
        public static final int RESULT_CODE_REFRESH = 8001;
        /**
         * 添加新项的标记
         */
        public static final int FLAG_ADD_NEW = -1000;
    }

    /**
     * 查询条件
     */
    public class QueryParameter {
        public static final String PAGE_INDEX = "pageNumber";
        public static final String PAGE_SIZE = "pageSize";
        public static final String COMPANY_ID = "orgId";
        public static final String USER_ID = "userId";
    }

    public class EventBus {
        public static final String KEY_RE_LOGIN = "key_relogin";
    }
}
