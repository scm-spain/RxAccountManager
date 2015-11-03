package com.alorma.androidreactiveaccounts;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AuthenticatorDescription;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by bernat.borras on 3/11/15.
 */
public class RxAccountsManager {

  public static Observable<List<Account>> get(final Context context) {
    return Observable.create(new Observable.OnSubscribe<List<Account>>() {
      @Override
      public void call(Subscriber<? super List<Account>> subscriber) {
        if (checkPermission(context, Manifest.permission.GET_ACCOUNTS)) {
          AccountManager accountManager = AccountManager.get(context);

          Account[] accounts = accountManager.getAccounts();

          subscriber.onNext(Arrays.asList(accounts));
          subscriber.onCompleted();
        } else {
          subscriber.onError(new RequestPermissionException(Manifest.permission.GET_ACCOUNTS));
        }
      }
    });
  }

  public static Observable<List<Account>> get(final Context context, final String accountType) {
    return Observable.create(new Observable.OnSubscribe<List<Account>>() {
      @Override
      public void call(Subscriber<? super List<Account>> subscriber) {
        if (checkPermission(context, Manifest.permission.GET_ACCOUNTS)) {
          AccountManager accountManager = AccountManager.get(context);

          Account[] accounts = accountManager.getAccountsByType(accountType);

          subscriber.onNext(Arrays.asList(accounts));
          subscriber.onCompleted();
        } else {
          subscriber.onError(new RequestPermissionException(Manifest.permission.GET_ACCOUNTS));
        }
      }
    });
  }

  public static Observable<List<AuthenticatorDescription>> authenticators(final Context context) {
    return Observable.create(new Observable.OnSubscribe<List<AuthenticatorDescription>>() {
      @Override
      public void call(Subscriber<? super List<AuthenticatorDescription>> subscriber) {
          AccountManager accountManager = AccountManager.get(context);

          AuthenticatorDescription[] types = accountManager.getAuthenticatorTypes();

          subscriber.onNext(Arrays.asList(types));
          subscriber.onCompleted();
      }
    });
  }

  public static Observable<List<String>> types(final Context context) {
    return authenticators(context).map(new Func1<List<AuthenticatorDescription>, List<String>>() {
      @Override
      public List<String> call(List<AuthenticatorDescription> authenticatorDescriptions) {
        List<String> typesList = new ArrayList<>();
        for (AuthenticatorDescription type : authenticatorDescriptions) {
          typesList.add(type.type);
        }
        return typesList;
      }
    });
  }

  private static boolean checkPermission(Context context, String permission) {
    return ContextCompat.checkSelfPermission(context, permission)
        == PackageManager.PERMISSION_GRANTED;
  }
}
