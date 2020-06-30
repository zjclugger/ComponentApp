package com.zjclugger.oa.utils;

/**
 * <br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class OaConstants {
    public class QueryParameter {
        public static final String PAYROLL_STATUS = "payrollstatus";
        public static final String COMPANY_ID = "companyId";
    }

    public class Keywords {
        /**
         * 请假类型
         */
        public static final String KEY_ATTENDANCE_TYPE="attendance_type";
        public static final String KEY_LEAVE_TYPE = "leave_type";
        public static final String KEY_LEAVE_HISTORY = "leave_history";
        public static final String KEY_LEAVE_TYPE_LIST = "leave_type_list";
        public static final String KEY_APPROVE_LIST = "approve_list";
        public static final String KEY_LIST_MAX_SIZE = "list_max_size";
        public static final String KEY_READ_ONLY = "read_only";
    }

    public class AttendanceType {
        /**
         * 考勤打卡
         */
        public static final String SIGN = "sign_in";
        /**
         * 请假
         */
        public static final String LEAVE = "leave";
        /**
         * 考勤统计
         */
        public static final String STATISTICS = "statistics";
    }

    /**
     * 发放类型(-1：无，0：自动,1：手动)
     */
    public class LeaveGrantType {
        public static final int MANUAL = 1;
        public static final int HOUR = 2;
        public static final int DAY = 3;
    }

    /**
     * 请假数的单位（0:小时，1：天）
     */
    public class LeaveUnit {
        public static final int HOUR = 0;
        public static final int DAY = 1;
    }
}
