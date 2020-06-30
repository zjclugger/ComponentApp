package com.zjclugger.router;

/**
 * 全局路由常量类<br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class ARouterConstants {

    public class Path {

        public static final String MAIN_PAGE = "/erp/main";

        /**
         * 登录页面
         */
        public static final String USER_LOGIN = "/app/login";

        //====业务公共组件==========================================================================
        /**
         * 员工列表
         */
        public static final String COM_STAFF_LIST = "/component/staffList";
        public static final String COM_UPLOAD_BILL = "/component/uploadBill";
        public static final String COM_SELECT_MEDIA = "/component/selectMedia";
        public static final String COM_PREVIEW_MEDIA = "/component/previewMedia";
        public static final String COM_USER_PROFILE = "/component/userProfile";
        public static final String COM_FRAGMENT_ACTIVITY = "/component/fragmentActivity";
        public static final String COM_CONTACTS_LIST = "/component/contactsList";
        public static final String COM_SELECT_DEPART_POST = "/component/selectDepartPost";
        //===业务组件（子系统）=====================================================================
        /**
         * 人事系统
         */
        public static final String SUB_SYSTEM_HR = "/hr/main";
        /**
         * 工资系统
         */
        public static final String SUB_SYSTEM_SALARY = "/salary/main";
        /**
         * 财务系统
         */
        public static final String SUB_SYSTEM_FINANCE = "/finance/main";
        /**
         * OA系统
         */
        public static final String SUB_SYSTEM_OA = "/oa/main";
    }

    /**
     * 服务名
     */
    public class Service {
        public static final String USER_SERVICE = "/user/service";

        /**
         * 员工列表
         */
        public static final String COM_STAFF_LIST = "/component/StaffListService";
        /**
         * 二维码
         */
        public static final String COM_QR_CODE = "/component/QRCodeService";
    }

    public class KeyWord {
        /**
         * 需要登录
         */
        public static final int NEED_LOGIN = 0x01;
        public static final String KEY_LOGIN_MESSAGE = "relogin_message";

        public static final String KEY_DETAIL_VIEW = "detail_view";
        public static final String KEY_SHOW_EMAIL = "show_email";
        public static final String KEY_COMPANY_ID = "company_id";
        //for media(picture selector)
        public static final String KEY_MEDIA_TITLE_TEXT = "media_title_text";
        public static final String KEY_MEDIA_LIST = "media_list";
        public static final String KEY_MEDIA_LIST_POSITION = "media_list_position";
        public static final String KEY_MEDIA_IS_PICTURE_SMALL = "media_is_picture_small";
        public static final String KEY_MEDIA_IS_READ_ONLY = "media_is_read_only";
        public static final String KEY_MEDIA_IS_SINGLE_LAYOUT = "media_is_single_layout";
        public static final String KEY_MEDIA_GRID_SPAN_COUNT = "media_grid_span_count";
        public static final String KEY_MEDIA_MAX_NUM = "media_max_number";
        public static final String KEY_MEDIA_SELECTED_LIST = "media_selected_list";
        public static final String KEY_MEDIA_PARAMETERS = "media_parameters";
        public static final String KEY_MEDIA_BACKGROUND_COLOR = "media_background_color";
        public static final String KEY_MEDIA_SUB_LAYOUT = "media_sub_layout";
        public static final String KEY_MEDIA_IS_HIDE_ADD_VIEW = "media_is_hide_add_view";
        public static final String KEY_MEDIA_CUSTOM_ADD_VIEW_ID = "media_custom_add_view_id";
        public static final String KEY_MEDIA_IS_ONLY_SUB_LAYOUT = "media_is_only_sub_layout";

        //for FragmentSupportActivity params
        public static final String KEY_FSA_CLASS_NAME = "fsa_class_name";
        public static final String KEY_FSA_FRAGMENT_COLOR = "fsa_fragment_color";
        public static final String KEY_FSA_FRAGMENT_LIGHT = "fsa_fragment_is_light";
        public static final String KEY_FSA_FRAGMENT_PARAMS = "fsa_fragment_params";
    }

}
