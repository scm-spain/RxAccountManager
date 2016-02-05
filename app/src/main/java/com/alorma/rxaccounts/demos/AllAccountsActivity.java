package com.alorma.rxaccounts.demos;

import android.support.v7.widget.RecyclerView;

import com.alorma.androidreactiveaccounts.RxAccountsManager;
import com.alorma.androidreactiveaccounts.accountmanager.RxAccount;
import com.alorma.rxaccounts.BaseDemoActivity;
import com.alorma.rxaccounts.adapter.AccountAdapter;

import java.util.List;

import rx.Observable;

public class AllAccountsActivity extends BaseDemoActivity<List<RxAccount>> {

  private AccountAdapter adapter;

  @Override
  protected RecyclerView.Adapter getAdapter() {
    adapter = new AccountAdapter();
    return adapter;
  }

  @Override
  protected Observable<List<RxAccount>> getObservable() {
    return RxAccountsManager.get(getAccountManager());
  }

  @Override
  protected void onResult(List<RxAccount> accounts) {
    adapter.addAll(accounts);
  }
}
