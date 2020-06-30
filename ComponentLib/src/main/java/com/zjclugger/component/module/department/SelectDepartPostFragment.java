package com.zjclugger.component.module.department;

import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProviders;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.zjclugger.component.R;
import com.zjclugger.component.R2;
import com.zjclugger.lib.base.BaseFragment;
import com.zjclugger.lib.entity.common.JListItem;
import com.zjclugger.lib.utils.Constants;
import com.zjclugger.lib.view.JSearchView;
import com.zjclugger.router.ARouterConstants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 部门岗位选择<br>
 * Created by King.Zi on 2020/03/10.<br>
 * Copyright (c) 2020 zjclugger.com
 */
@Route(path = ARouterConstants.Path.COM_SELECT_DEPART_POST)
public class SelectDepartPostFragment extends BaseFragment {
    private static final String TAG = "SelectDepartPost";
    DepartViewModel mViewModel;
    List<JListItem<String>> mDepartPostList = new ArrayList<>();
    ArrayList<DepartPostEntity> mDepartmentList = new ArrayList<>();
    private DepartmentPostResult mDepartmentPostResult;
    DepartPostAdapter mAdapter;
    @BindView(R2.id.depart_post_list_view)
    ListView mListView;
    @BindView(R2.id.depart_search_view)
    JSearchView mSearchView;
    @BindView(R2.id.depart_all_layout)
    RelativeLayout mAllLayout;

    @Override
    public int getLayout() {
        return R.layout.fragment_select_depart_post;
    }

    @Override
    public void initViews() {
        mViewModel = ViewModelProviders.of(getActivity()).get(DepartViewModel.class);
        mAllLayout.setOnClickListener(v -> postData(new DepartPostEntity("", "不限", "", false,
                false)));
        mAdapter = new DepartPostAdapter(mContext, mDepartmentList);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener((parent, view, position, id) -> postData(mAdapter.getItem(position)));

        mSearchView.init(getActivity());
        mSearchView.getRightView().setVisibility(View.GONE);
        mSearchView.setQueryHint("请输入部门或岗位");
        mSearchView.getSearchView().onActionViewExpanded();
        mSearchView.clearFocus();
        mSearchView.getBackView().setOnClickListener(v -> close());
        mSearchView.getSearchView().setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.setKeyword(newText);
                return false;
            }
        });

        getDepartmentPostList();
    }

    private void getDepartmentPostList() {
        //TODO:get data from server
        mDepartmentPostResult=new DepartmentPostResult();
        mDepartmentPostResult.setId("J001");
        mDepartmentPostResult.setOrgName("XX公司");
        DepartmentPostResult childDepartPost;
        for (int i = 0; i < 5; i++) {
            childDepartPost = new DepartmentPostResult();
            childDepartPost.setId("C00" + i);
            childDepartPost.setParentId(mDepartmentPostResult.getId());
            childDepartPost.setOrgName("部门" + i);
            mDepartmentPostResult.getChildrenList().add(childDepartPost);
        }

        if (null != mDepartmentPostResult.getChildrenList() && mDepartmentPostResult.getChildrenList().size() > 0) {
            mAdapter.setTopId(mDepartmentPostResult.getId());
            for (DepartmentPostResult children :
                    mDepartmentPostResult.getChildrenList()) {
                if (children.getOrgName().equalsIgnoreCase("部门2")) {
                    DepartmentPostResult temp = new DepartmentPostResult();
                    temp.setId("10000");
                    temp.setOrgName("Android开发组");
                    temp.setParentId(children.getId());

                    Post post = new Post();
                    post.setOrgId("500");
                    post.setPostName("安卓开发工程师");
                    temp.getPostList().add(post);

                    post = new Post();
                    post.setOrgId("501");
                    post.setPostName("安卓开发总监");
                    temp.getPostList().add(post);
                    children.getChildrenList().add(temp);

                    //2
                    temp = new DepartmentPostResult();
                    temp.setId("10001");
                    temp.setOrgName("Web开发组");
                    temp.setParentId(children.getId());

                    post = new Post();
                    post.setOrgId("600");
                    post.setPostName("WEB开发工程师");
                    temp.getPostList().add(post);

                    post = new Post();
                    post.setOrgId("601");
                    post.setPostName("WEB开发总监");
                    temp.getPostList().add(post);
                    children.getChildrenList().add(temp);
                }
                getDepartPostEntity(children);
            }
        }
       /* mViewModel.getDepartmentPost(UserManager.getInstance().getCurrentCompanyId()).observe
       (this,
                response -> {
                    if (null != mDepartmentList) {
                        mDepartmentList.clear();
                    }
                    if (ApiResponseUtils.isSuccess(response, "获取部门和岗位列表")) {
                        mDepartmentPostResult = response.body.getResult();

                    }

                    mAdapter.notifyDataSetChanged();
                    Log.d(TAG, "get department size is " + (null == mDepartmentList ? 0 :
                            mDepartmentList.size()));

                });*/
    }

    private void getDepartPostEntity(DepartmentPostResult result) {
        if (null != result) {
            //TODO：先加子部门，再加岗位
            mDepartmentList.add(new DepartPostEntity(result.getId(), result.getOrgName(),
                    result.getParentId(), true,
                    (null != result.getChildrenList() && result.getChildrenList().size() > 0)));
            if (null != result.getChildrenList() && result.getChildrenList().size() > 0) {
                for (DepartmentPostResult children : result.getChildrenList()) {
                    getDepartPostEntity(children);
                }
            }

            //TODO:缓存部门与岗位的关系
            JListItem<String> listItem = new JListItem(result.getOrgName(), result.getId());
            listItem.getChildItemList().add(new JListItem(-1, "不限", ""));
            if (null != result.getPostList() && result.getPostList().size() > 0) {
                for (Post post : result.getPostList()) {
                    listItem.getChildItemList().add(new JListItem(post.getPostName(),
                            post.getOrgId()));
                }
            }
            mDepartPostList.add(listItem);

            //TODO:如果不需要，再不添加【加岗位】缓存部门与岗位的关系
           /* if (null != result.getPostList() && result.getPostList().size() > 0) {
                for (Post post : result.getPostList()) {
                    mDepartmentList.add(new DepartPostEntity(post.getOrgId(), post.getPostName(),
                            result.getId(), false, false));
                }
            }*/
        }
    }

    private void postData(DepartPostEntity entity) {
        JListItem<String> erpListItem = new JListItem<>();
        erpListItem.setText(entity.getName());
        erpListItem.setValue(entity.getId());
        if (!TextUtils.isEmpty(entity.getId()) && null != mDepartPostList) {
            for (JListItem<String> depart : mDepartPostList) {
                if (entity.getId().equalsIgnoreCase(depart.getValue())) {
                    erpListItem = depart;
                    break;
                }
            }
        } else {
            erpListItem.getChildItemList().add(new JListItem("不限", ""));
        }
        LiveEventBus.get(Constants.Keywords.KEY_DEPART_SELECTED).post(erpListItem);
        close();
    }

    @Override
    public void closeFloatView() {
    }

    @Override
    public boolean doBackPress() {
        return false;
    }
}