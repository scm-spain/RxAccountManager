package com.alorma.rxaccounts;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.alorma.rxaccounts.demos.AllAccountsActivity;
import com.alorma.rxaccounts.demos.FilterByMailActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
  }

  private void openIntent(Class<? extends BaseDemoActivity> activityClass) {
    Intent intent = new Intent(this, activityClass);
    startActivity(intent);
  }

  @OnClick(R.id.all_accounts)
  public void allAccounts() {
    openIntent(AllAccountsActivity.class);
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
