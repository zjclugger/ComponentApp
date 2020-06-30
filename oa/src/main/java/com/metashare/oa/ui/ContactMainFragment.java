package com.zjclugger.oa.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.zjclugger.lib.business.User;
import com.zjclugger.lib.business.UserManager;
import com.zjclugger.lib.utils.Constants;
import com.zjclugger.lib.utils.FragmentUtils;
import com.zjclugger.oa.R;
import com.zjclugger.oa.R2;
import com.zjclugger.oa.ui.adapter.Company;
import com.zjclugger.oa.ui.adapter.Department;
import com.zjclugger.oa.ui.adapter.Staff;

import butterknife.BindView;

/**
 * 工作台<br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class ContactMainFragment extends OABaseFragment {
    private final static String TAG = "Workbench";
    protected OAViewModel mViewModel;
    private View mView;
    private User mCurrentUser;
    private Company mCurrentCompany;    //当前公司，根据companyId获得
    @BindView(R2.id.iv_title_back)
    ImageView mBackImageView;

    @Override
    public void initViews() {
        initDetailTitleViews("通讯录");
        mBackImageView.setVisibility(View.GONE);
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_contacts_main;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCurrentUser = UserManager.getInstance().getCurrentUser();
        getData();
        initDeptFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    /**
     * get data()
     */
    private void getData() {
        //TODO:读取接口，获取数据
        mCurrentCompany = new Company();
        mCurrentCompany.setName("宇宙垃圾清理公司");
        Department department = new Department();
        department.setName("技术部");
        department.setStaffTotal(15);
        Staff staff;
        for (int i = 0; i < 3; i++) {
            staff = new Staff();
            staff.setId(100 + i);
            staff.setName("张" + i + "丰");
            department.getStaffList().add(staff);
        }

        Department subDepartment = new Department();
        subDepartment.setName("软件组");
        subDepartment.setStaffTotal(7);
        for (int i = 0; i < 7; i++) {
            staff = new Staff();
            staff.setId(1000 + i);
            staff.setName("周" + i + "员");
            subDepartment.getStaffList().add(staff);
        }
        department.getSubDepartmentList().add(subDepartment);

        subDepartment = new Department();
        subDepartment.setStaffTotal(5);
        subDepartment.setName("硬件组");
        for (int i = 0; i < 5; i++) {
            staff = new Staff();
            staff.setId(2000 + i);
            staff.setName("马" + i + "饼");
            subDepartment.getStaffList().add(staff);
        }
        department.getSubDepartmentList().add(subDepartment);
        mCurrentCompany.getDepartments().add(department);

        //
        department = new Department();
        department.setName("财务部");
        department.setStaffTotal(19);
        for (int i = 0; i < 19; i++) {
            staff = new Staff();
            staff.setId(200 + i);
            staff.setName("李" + i + "财");
            department.getStaffList().add(staff);
        }
        mCurrentCompany.getDepartments().add(department);

        department = new Department();
        department.setName("生产部");
        department.setStaffTotal(27);
        for (int i = 0; i < 27; i++) {
            staff = new Staff();
            staff.setId(300 + i);
            staff.setName("王" + i + "六");
            department.getStaffList().add(staff);
        }
        mCurrentCompany.getDepartments().add(department);

        department = new Department();
        department.setName("总经办");
        department.setStaffTotal(4);
        for (int i = 0; i < 4; i++) {
            staff = new Staff();
            staff.setId(400 + i);
            staff.setName("陈" + i + "板");
            department.getStaffList().add(staff);
        }
        mCurrentCompany.getDepartments().add(department);
    }

    private void initDeptFragment() {
        ContactsListFragment contactsListFragment = new ContactsListFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.Keywords.KEY_CONTACTS_LIST, mCurrentCompany);
        bundle.putBoolean(Constants.Keywords.KEY_CONTACTS_MODE, false);
        contactsListFragment.setArguments(bundle);
        FragmentUtils.addFirstFragment(getActivity(), contactsListFragment, R.id.contacts_content);
    }
}