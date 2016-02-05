package com.alorma.androidreactiveaccounts;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AuthenticatorDescription;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.util.Patterns;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * Created by bernat.borras on 3/11/15.
 */
public class RxAccountsManager {

  public static Observable<List<Account>> get(final AccountManager accountManager) {
    return Observable.create(subscriber ->  {
          if (!subscriber.isUnsubscribed()) {
            try {
              Account[] accounts = accountManager.getAccounts();
              subscriber.onNext(Arrays.asList(accounts));
              subscriber.onCompleted();
            } catch (Exception e) {
              subscriber.onError(new RequestPermissionException(Manifest
                  .permission.GET_ACCOUNTS));
            }
          }
        }
    );
  }

  public static Observable<List<Account>> emails(AccountManager accountManager) {
    return get(accountManager).flatMap(accounts -> Observable.from(accounts)
        .filter((Func1<Account, Boolean>) account -> Patterns.EMAIL_ADDRESS
            .matcher(account.name).matches())).toList();
  }

  public static Observable<List<String>> emailsText(AccountManager accountManager) {
    return emails(accountManager).flatMap(accounts -> Observable.from(accounts)
        .map(account -> account.name).distinct().toList());
  }
}
