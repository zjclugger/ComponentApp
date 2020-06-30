package com.zjclugger.oa.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zjclugger.lib.utils.Constants;
import com.zjclugger.lib.utils.FragmentUtils;
import com.zjclugger.lib.utils.ViewUtils;
import com.zjclugger.lib.utils.WidgetUtils;
import com.zjclugger.oa.R;
import com.zjclugger.oa.R2;
import com.zjclugger.oa.ui.adapter.Company;
import com.zjclugger.oa.ui.adapter.Department;
import com.zjclugger.oa.ui.adapter.DepartmentAdapter;
import com.zjclugger.oa.ui.adapter.DepartmentContactsAdapter;
import com.zjclugger.oa.ui.adapter.Staff;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 通讯录<br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class ContactsListFragment extends OABaseFragment {
    private final static String TAG = "Contacts";
    private boolean mIsChooseMode;
    private LinearLayout mDepartLayout;
    private HorizontalScrollView mDepartScrollView;
    private RelativeLayout mFooterLayout;
    private String mPageTitle;

    //view
    View mView;
    @BindView(R2.id.contacts_layout)
    NestedScrollView mContactsLayout;
    @BindView(R2.id.depart_list_view)
    RecyclerView mDepartListView;
    @BindView(R2.id.staff_list_view)
    RecyclerView mStaffListView;
    @BindView(R2.id.select_all_view)
    CheckBox mSelectAllView;
    @BindView(R2.id.select_all_layout)
    LinearLayout mSelectAllLayout;

    private Company mCurrentCompany;
    private List<Department> mDepartmentList = new ArrayList<>();
    private List<Staff> mStaffList = new ArrayList<>();
    private DepartmentAdapter mDepartmentAdapter;
    private DepartmentContactsAdapter mContactsAdapter;
    private List<String> mNavNameList = new ArrayList<>();

    private ContactsDetailFragment mContactsDetailFragment;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
    }

    @Override
    public void initViews() {
        mIsChooseMode = getArguments().getBoolean(Constants.Keywords.KEY_CONTACTS_MODE, false);
        //activity view
        mDepartLayout = getActivity().findViewById(R.id.depart_layout);
        mDepartScrollView = getActivity().findViewById(R.id.depart_scroll_view);
        mFooterLayout = getActivity().findViewById(R.id.footer_layout);
        if (!mIsChooseMode) {
            ViewUtils.setVisibility(false, mFooterLayout, mSelectAllLayout);
        }

        mSelectAllView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                WidgetUtils.toastMessage(getActivity(), "选中状态=" + isChecked);
                mDepartmentAdapter.setSelected(isChecked);
                mContactsAdapter.setSelected(isChecked);
            }
        });

        if (getArguments().containsKey(Constants.Keywords.KEY_CONTACTS_LIST)) {
            mCurrentCompany = getArguments().getParcelable(Constants.Keywords.KEY_CONTACTS_LIST);
            if (mCurrentCompany != null) {
                mDepartmentList = mCurrentCompany.getDepartments();
                //TODO: mStaffList = mCurrentCompany.get???
            }
        } else {
            if (getArguments().containsKey(Constants.Keywords.KEY_DEPARTMENT_LIST)) {
                mDepartmentList =
                        getArguments().getParcelableArrayList(Constants.Keywords.KEY_DEPARTMENT_LIST);
                mStaffList =
                        getArguments().getParcelableArrayList(Constants.Keywords.KEY_DEPARTMENT_CONTACTS_LIST);
            }
        }

        if (mCurrentCompany == null && mDepartmentList == null && mStaffList == null) {
            mDepartScrollView.setVisibility(View.GONE);
            mContactsLayout.setVisibility(View.GONE);
        } else {
            bindDepartNameNavLayout();
            bindDepartmentListView();
            bindStaffListView();
        }

        //TODO:获取数据
       /* Bundle params = getArguments();
        int recordId = params.getInt(Constants.Keywords.KEY_PERSONAL_PAYROLL_ID, 0);
        if (recordId > 0) {
            showWaiting();
            mViewModel.getPersonalPayrollRecordDetail(String.valueOf(recordId)).observe(this,
                    new Observer<ApiResponse<BaseWrapperEntity<PayrollDetailResult>>>() {
                        @Override
                        public void onChanged(ApiResponse<BaseWrapperEntity<PayrollDetailResult>>
                         baseWrapperEntityApiResponse) {
                            closeProgressDialog();
                            if (baseWrapperEntityApiResponse != null &&
                            baseWrapperEntityApiResponse.isSuccess()) {
                                BaseWrapperEntity<PayrollDetailResult> wrapperEntity =
                                        baseWrapperEntityApiResponse.body;
                                if (wrapperEntity != null) {
                                    bindData(wrapperEntity.getResult());
                                }
                            }
                        }
                    });
        } else {
            closeProgressDialog();
            Log.e(TAG, "payroll id is " + recordId);
            WidgetUtils.toastErrorMessage(getActivity(), "未查询到结果");
        }*/
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_contacts;
    }

    private void bindDepartmentListView() {
        mDepartListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mDepartmentAdapter = new DepartmentAdapter(R.layout.item_contacts_department,
                mDepartmentList, mIsChooseMode);
        mDepartmentAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.iv_contacts_more || !mIsChooseMode) {
                    //WidgetUtils.toastMessage(getActivity(), "更多");
                    ContactsListFragment subFragment = new ContactsListFragment();
                    //set data
                    Bundle data = new Bundle();
                    ArrayList<Department> subDepartment =
                            (ArrayList<Department>) mDepartmentList.get(position)
                                    .getSubDepartmentList();

                    data.putParcelableArrayList(Constants.Keywords.KEY_DEPARTMENT_LIST,
                            subDepartment);
                    data.putParcelableArrayList(Constants.Keywords.KEY_DEPARTMENT_CONTACTS_LIST,
                            (ArrayList<Staff>) mDepartmentList.get(position).getStaffList());
                    data.putBoolean(Constants.Keywords.KEY_CONTACTS_MODE, mIsChooseMode);
                    subFragment.setArguments(data);
                    FragmentUtils.replaceFragment(getActivity(), subFragment, R.id.contacts_content,
                            mDepartmentList.get(position).getName());
                }
            }
        });
        mDepartListView.setAdapter(mDepartmentAdapter);
     /*
        TODO:添加分隔线
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(),
        DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.bg_list_devider));
        mDepartListView.addItemDecoration(dividerItemDecoration);
        */

        ViewUtils.setVisibility(mDepartmentList != null && mDepartmentList.size() > 0,
                mDepartListView);
    }

    private void bindStaffListView() {
        mStaffListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mContactsAdapter = new DepartmentContactsAdapter(R.layout.item_contacts_list,
                mStaffList, mIsChooseMode);
        mContactsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mIsChooseMode) {
                    WidgetUtils.toastMessage(getActivity(),
                            "您选择了" + mStaffList.get(position).getName());
                } else {
                    //staff detail
                    if (mContactsDetailFragment == null) {
                        mContactsDetailFragment = new ContactsDetailFragment();
                    }
                    FragmentUtils.addFragment(getActivity(), mContactsDetailFragment,
                            R.id.content_fragment);
                }
            }
        });
        mStaffListView.setAdapter(mContactsAdapter);
        ViewUtils.setVisibility(mStaffList != null && mStaffList.size() > 0, mStaffListView);
    }

    private void initDeptNameNavList() {
        if (mNavNameList == null) {
            mNavNameList = new ArrayList<>();
        }
        mNavNameList.clear();
        mNavNameList.add(getString(R.string.const_contacts));
        for (int i = 0; i < getActivity().getSupportFragmentManager().getBackStackEntryCount(); i++) {
            mNavNameList.add(getActivity().getSupportFragmentManager().getBackStackEntryAt(i).getName());
        }
    }

    private void bindDepartNameNavLayout() {
        initDeptNameNavList();
        mDepartLayout.removeAllViews();
        for (int i = 0; i < mNavNameList.size(); i++) {
            final LinearLayout navLayout =
                    (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.item_contacts_navigation, null);
            ImageView navArrowView = navLayout.findViewById(R.id.iv_nav_arrow);
            TextView navNameView = navLayout.findViewById(R.id.tv_nav_name);

            navLayout.setTag(i);
            navArrowView.setVisibility(i == 0 ? View.GONE : View.VISIBLE);
            navNameView.setText(mNavNameList.get(i));

            if (mNavNameList.size() > 1) {
                if (i == mNavNameList.size() - 1) {
                    navNameView.setTextColor(getResources().getColor(R.color.primary_gray));
                } else {
                    navNameView.setTextColor(getResources().getColor(R.color.primary_orange));
                }
            } else {
                navNameView.setTextColor(getResources().getColor(R.color.primary_gray));
            }

            navLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int currentIndex = 0;

                    if (navLayout.getTag() != null) {
                        currentIndex = (int) navLayout.getTag();
                    }

                    if (currentIndex == 0) {
                        //点击回到一级部门,清除所有回退栈
                        if (mNavNameList.size() > 1) {
                            getActivity().getSupportFragmentManager().popBackStackImmediate(mNavNameList.get(1),
                                    FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        }
                    } else {
                        getActivity().getSupportFragmentManager().popBackStackImmediate(mNavNameList.get(currentIndex), 0);
                    }
                }
            });

            mDepartLayout.addView(navLayout);
            mDepartScrollView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //此方法不能直接被调用
                    mDepartScrollView.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
                }
            }, 100L);
        }
    }
}