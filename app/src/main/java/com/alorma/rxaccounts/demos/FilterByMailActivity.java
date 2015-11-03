package com.alorma.rxaccounts.demos;

import android.support.v7.widget.RecyclerView;
import com.alorma.androidreactiveaccounts.RxAccountsManager;
import com.alorma.rxaccounts.BaseDemoActivity;
import com.alorma.rxaccounts.adapter.StringAdapter;
import java.util.List;
import rx.Observable;

/**
 * Created by bernat.borras on 3/11/15.
 */
public class FilterByMailActivity extends BaseDemoActivity<List<String>> {

  private StringAdapter adapter;

  @Override
  protected RecyclerView.Adapter getAdapter() {
    adapter = new StringAdapter();
    return adapter;
  }

  @Override
  protected Observable<List<String>> getObservable() {
    return RxAccountsManager.emailsText(this);
  }

  @Override
  protected void onResult(List<String> mails) {
    adapter.addAll(mails);
  }
}
