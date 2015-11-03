package com.alorma.rxaccounts;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.alorma.rxaccounts.demos.AccountTypesActivity;
import com.alorma.rxaccounts.demos.AllAccountsActivity;
import com.alorma.rxaccounts.demos.FilterByMailActivity;
import com.alorma.rxaccounts.demos.GoogleAccountsActivity;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
  }

  private void openIntent(Class<? extends BaseDemoActivity> activityClass) {
    Intent intent = new Intent(this, activityClass);
    startActivity(intent);
  }

  @OnClick(R.id.all_accounts)
  public void allAccounts() {
    openIntent(AllAccountsActivity.class);
  }

  @OnClick(R.id.google_accounts)
  public void googleAccounts() {
    openIntent(GoogleAccountsActivity.class);
  }

  @OnClick(R.id.account_types)
  public void accountTypes() {
    openIntent(AccountTypesActivity.class);
  }

  @OnClick(R.id.filter_by_mail)
  public void filterAccountsByMail() {
    openIntent(FilterByMailActivity.class);
  }

  @Override
  protected void onDestroy() {
    ButterKnife.unbind(this);
    super.onDestroy();
  }
}
