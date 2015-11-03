package com.alorma.rxaccounts.demos;

import android.accounts.Account;
import android.support.v7.widget.RecyclerView;
import com.alorma.androidreactiveaccounts.RxAccountsManager;
import com.alorma.rxaccounts.adapter.AccountAdapter;
import com.alorma.rxaccounts.BaseDemoActivity;
import java.util.List;
import rx.Observable;

/**
 * Created by bernat.borras on 3/11/15.
 */
public class GoogleAccountsActivity extends BaseDemoActivity<List<Account>> {
  private AccountAdapter adapter;

  @Override
  protected RecyclerView.Adapter getAdapter() {
    adapter = new AccountAdapter();
    return adapter;
  }

  @Override
  protected Observable<List<Account>> getObservable() {
    return RxAccountsManager.get(this, "com.google");
  }

  @Override
  protected void onResult(List<Account> accounts) {
    adapter.addAll(accounts);
  }
}