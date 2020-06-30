package com.zjclugger.basiclib.permission;

import android.Manifest;

/**
 * 常用权限组合类
 */
public interface PermissionConstants {
    /**
     * 联系人
     */
    String[] CONTACTS = {
            Manifest.permission.WRITE_CONTACTS,
            Manifest.permission.GET_ACCOUNTS,
            Manifest.permission.READ_CONTACTS
    };

    /**
     * 电话
     */
    String[] PHONE = {
            Manifest.permission.READ_CALL_LOG,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.WRITE_CALL_LOG,
            Manifest.permission.USE_SIP,
            Manifest.permission.PROCESS_OUTGOING_CALLS,
            Manifest.permission.ADD_VOICEMAIL
    };

    /**
     * 日历
     */
    String[] CALENDAR = {
            Manifest.permission.READ_CALENDAR,
            Manifest.permission.WRITE_CALENDAR
    };

    /**
     * 摄像头
     */
    String[] CAMERA = {
            Manifest.permission.CAMERA
    };

    /**
     * 传感器
     */
    String[] SENSORS = {
            Manifest.permission.BODY_SENSORS
    };

    /**
     * 定位
     */
    String[] LOCATION = new String[] {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    /**
     * 存储
     */
    String[] STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    /**
     * 录音(麦克风)
     */
    String[] MICROPHONE = {
            Manifest.permission.RECORD_AUDIO
    };

    /**
     * SMS
     */
    String[] SMS = {
            Manifest.permission.READ_SMS,
            Manifest.permission.RECEIVE_WAP_PUSH,
            Manifest.permission.RECEIVE_MMS,
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.SEND_SMS
    };
}
