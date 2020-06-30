package com.zjclugger.finance;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zjclugger.finance.ui.FinanceWorkbenchFragment;
import com.zjclugger.finance.ui.account.AccountManageFragment;
import com.zjclugger.finance.ui.original.OriginalManageFragment;
import com.zjclugger.finance.ui.report.MainReportFragment;
import com.zjclugger.lib.base.BaseFragment;
import com.zjclugger.lib.base.BaseMainActivity;
import com.zjclugger.lib.business.UserPermission;
import com.zjclugger.router.ARouterConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Route(path = ARouterConstants.Path.SUB_SYSTEM_FINANCE)
public class FinanceMainActivity extends BaseMainActivity {

    @Override
    public int getMenu() {
        return R.menu.menu_finance;
    }

    @Override
    public int getMenuCheckedIndex() {
        return 0;
    }

    @Override
    public Map<Integer, BaseFragment> getFragmentList() {
        Map<Integer, BaseFragment> fragmentMap = new HashMap<>();
        fragmentMap.put(R.id.fin_menu_manager, new FinanceWorkbenchFragment());
        fragmentMap.put(R.id.fin_menu_original, new OriginalManageFragment());
        fragmentMap.put(R.id.fin_menu_account, new AccountManageFragment());
        fragmentMap.put(R.id.fin_menu_report, new MainReportFragment());
        return fragmentMap;
    }

    @Override
    public List<UserPermission> getMenuPermissionList() {
        List<UserPermission> permissionList = new ArrayList<>();
        permissionList.add(new UserPermission("finance:", R.id.fin_menu_manager));
        permissionList.add(new UserPermission("finance:increment", R.id.fin_menu_original));
        permissionList.add(new UserPermission("finance:voucher", R.id.fin_menu_account));
        permissionList.add(new UserPermission("finance:voucher", R.id.fin_menu_account));

        return permissionList;
    }
}