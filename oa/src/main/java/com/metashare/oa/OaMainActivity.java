package com.zjclugger.oa;

import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.zjclugger.lib.base.BaseActivity;
import com.zjclugger.lib.base.BaseFragment;
import com.zjclugger.oa.ui.ContactMainFragment;
import com.zjclugger.oa.ui.WorkbenchFragment;
import com.zjclugger.router.ARouterConstants;

import butterknife.BindView;
import butterknife.ButterKnife;

@Route(path = ARouterConstants.Path.SUB_SYSTEM_OA)
public class OaMainActivity extends BaseActivity {

    private static final String TAG = "SalaryMain";
    private final static int NAVIGATION_WORK = 2000;
    private final static int NAVIGATION_CONTACTS = 2001;
    private final static int NAVIGATION_USER_INFO = 2002;

    @BindView(R2.id.oa_navigation)
    BottomNavigationView mNavigationView;
    private SparseArray<BaseFragment> mFragments = new SparseArray<>();
    private BaseFragment mLastFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oa_main);
        ButterKnife.bind(this);
        setFragmentIndicator(NAVIGATION_WORK);
        mNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if (R.id.oa_work == menuItem.getItemId()) {
                    setFragmentIndicator(NAVIGATION_WORK);
                    return true;
                } else if (R.id.oa_contacts == menuItem.getItemId()) {
                    setFragmentIndicator(NAVIGATION_CONTACTS);
                    return true;
                } else if (R.id.oa_user_center == menuItem.getItemId()) {
                    setFragmentIndicator(NAVIGATION_USER_INFO);
                    return true;
                } else {
                    Log.e(TAG, "you click item " + menuItem.getTitle());
                }

                return false;
            }
        });
        // ERPUtils.toastMessage(this,"app="+ ERPApplication.getAppContext());
    }

    private void setFragmentIndicator(int tag) {
        Log.d("KING", "tag is " + tag);
        closeDetailFragment();
        BaseFragment newFragment = mFragments.get(tag);
        FragmentManager fmManager = getSupportFragmentManager();
        //开启一个fragment事务
        FragmentTransaction transaction = fmManager.beginTransaction();
        if (mLastFragment != null) {
            //隐藏正在显示的fragment ，这里也可以用 transaction.detach(mLastFragment)
            // 会将view从UI中移除，执行onDestroyView()
            mLastFragment.doBackPress();
            transaction.hide(mLastFragment);
//          transaction.detach(mLastFragment);
        }
        if (newFragment == null) {
            // 如果newInfo为空，则创建一个并添加到界面上
            switch (tag) {
                case NAVIGATION_WORK:
                    newFragment = new WorkbenchFragment();
                    break;
                case NAVIGATION_CONTACTS:
                    newFragment = new ContactMainFragment();
                    break;
                case NAVIGATION_USER_INFO:
                    // newFragment = new UserProfileFragment();
                    break;
            }
            mFragments.put(tag, newFragment);
            transaction.add(R.id.content_fragment, newFragment, String.valueOf(tag));
        } else {
            // 如果newInfo不为空，则直接将它显示出来 ，对应的这里可以用 transaction.attach(newInfo)
            // 重建view视图，附加到UI上并显示，会重新执行onActivityView()
            transaction.show(newFragment);
//          transaction.attach(newInfo);
        }
        mLastFragment = newFragment;
        transaction.commit();
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        Log.d("KINGS", "dispatchKeyEvent=,event=" + event);
        return super.dispatchKeyEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.d("KINGS", "keycode=" + keyCode + ",event=" + event);
        if (mLastFragment != null) {
            mLastFragment.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        if (mLastFragment != null) {
            if (mLastFragment.doBackPress()) {
                super.onBackPressed();
            }
        } else {
            super.onBackPressed();
        }
    }
}
