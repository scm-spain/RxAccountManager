package com.alorma.rxaccounts.demos;

import android.accounts.Account;
import android.support.v7.widget.RecyclerView;
import com.alorma.rxaccounts.BaseDemoActivity;
import java.util.List;
import rx.Observable;

/**
 * Created by bernat.borras on 3/11/15.
 */
public class FilterByMailActivity extends BaseDemoActivity<List<Account>> {

  @Override
  protected RecyclerView.Adapter getAdapter() {
    return null;
  }

  @Override
  protected Observable<List<Account>> getObservable() {
    return null;
  }

  @Override
  protected void onResult(List<Account> accounts) {

  }
}
