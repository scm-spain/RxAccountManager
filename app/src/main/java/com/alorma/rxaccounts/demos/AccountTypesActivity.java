package com.alorma.rxaccounts.demos;

import android.accounts.Account;
import android.support.v7.widget.RecyclerView;
import com.alorma.androidreactiveaccounts.RxAccountsManager;
import com.alorma.rxaccounts.BaseDemoActivity;
import com.alorma.rxaccounts.adapter.TypesAdapter;
import java.util.List;
import rx.Observable;

/**
 * Created by bernat.borras on 3/11/15.
 */
public class AccountTypesActivity extends BaseDemoActivity<List<String>> {

  private TypesAdapter adapter;

  @Override
  protected RecyclerView.Adapter getAdapter() {
    adapter = new TypesAdapter();
    return adapter;
  }

  @Override
  protected Observable<List<String>> getObservable() {
    return RxAccountsManager.types(this);
  }

  @Override
  protected void onResult(List<String> strings) {
    adapter.addAll(strings);
  }
}
