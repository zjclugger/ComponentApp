package com.zjclugger.oa.ui;

import com.zjclugger.lib.base.BaseFragment;
import com.zjclugger.lib.base.BaseMainActivity;
import com.zjclugger.oa.R;

import java.util.HashMap;
import java.util.Map;

/**
 * 考勤打卡<br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class AttendanceActivity extends BaseMainActivity {
    private final static String TAG = "Attendance";

    @Override
    public int getMenu() {
        return R.menu.menu_navigation_oa;
    }

    @Override
    public int getMenuCheckedIndex() {
        return 0;
    }

    @Override
    public Map<Integer, BaseFragment> getFragmentList() {
        Map<Integer, BaseFragment> fragmentMap = new HashMap<>();
        fragmentMap.put(R.id.oa_attendance_sign, new AttendanceFragment());
        fragmentMap.put(R.id.oa_attendance_statistics, new AttendanceStatisticsFragment());
        fragmentMap.put(R.id.oa_attendance_setting, new AttendanceSettingsFragment());
        return fragmentMap;
    }
}