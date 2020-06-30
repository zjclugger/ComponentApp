package com.zjclugger.oa.ui;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProviders;

import com.zjclugger.lib.base.BaseActivity;
import com.zjclugger.lib.utils.Constants;
import com.zjclugger.lib.utils.FragmentUtils;
import com.zjclugger.oa.R;
import com.zjclugger.oa.ui.adapter.Company;
import com.zjclugger.oa.ui.adapter.Department;
import com.zjclugger.oa.ui.adapter.Staff;

/**
 * 公司通讯录<br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class CompanyContactsActivity extends BaseActivity {
    private final static String TAG = "Contacts";
    private OAViewModel mViewModel;
    private boolean mIsChooseMode;   //是否为选择模式
    private Company mCurrentCompany;    //当前公司，根据companyId获得

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_contacts);
        mViewModel = ViewModelProviders.of(this).get(OAViewModel.class);

        //TODO:先默认为true
        mIsChooseMode = getIntent().getBooleanExtra(Constants.Keywords.KEY_CONTACTS_MODE, true);

        initViews();
        initDeptFragment();
    }

    @Override
    protected boolean isDetailActivity() {
        return true;
    }

    @Override
    protected String getDetailActivityTitle() {
        return "通讯录";
    }

    public void initViews() {
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
        bundle.putBoolean(Constants.Keywords.KEY_CONTACTS_MODE, mIsChooseMode);
        contactsListFragment.setArguments(bundle);
        FragmentUtils.addFirstFragment(this, contactsListFragment, R.id.contacts_content);
    }

    /*private void initDeptFragment(ArrayList<DeptListBean.DataBean.DepartmentListBean> mDeptList,
                                  ArrayList<String> mDeptUserList, String tag) {
        Fragment mDeptFragment = new DeptFragment();
        FragmentManager manager = getActivity().getSupportFragmentManager();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("deptList", mDeptList);
        bundle.putStringArrayList("deptUserList", mDeptUserList);
        mDeptFragment.setArguments(bundle);
        manager.beginTransaction()
                .replace(R.id.dept_content, mDeptFragment)
                .addToBackStack(tag)
                .commit();
    }*/
}