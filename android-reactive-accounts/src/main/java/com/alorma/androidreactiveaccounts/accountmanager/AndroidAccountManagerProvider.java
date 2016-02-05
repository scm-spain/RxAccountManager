package com.alorma.androidreactiveaccounts.accountmanager;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.util.Patterns;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class AndroidAccountManagerProvider implements AccountManagerProvider{

  private AccountManager accountManager;

  public AndroidAccountManagerProvider(AccountManager accountManager) {
    this.accountManager = accountManager;
  }

  @SuppressLint("MissingPermission")
  @Override
  public List<RxAccount> getAccounts() {
    Account[]results = accountManager.getAccounts();
    List<RxAccount> rxAccounts = new ArrayList<>();
    for (Account result : results) {
      rxAccounts.add(new RxAccount(result.name, result.type));
    }
    return rxAccounts;
  }

  @Override
  public Pattern patternMail() {
    return Patterns.EMAIL_ADDRESS;
  }
}
