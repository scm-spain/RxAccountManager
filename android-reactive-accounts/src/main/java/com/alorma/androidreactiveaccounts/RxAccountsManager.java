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
    return authenticators(context).flatMap(
        new Func1<List<AuthenticatorDescription>, Observable<List<String>>>() {
          @Override
          public Observable<List<String>> call(
              List<AuthenticatorDescription> authenticatorDescriptions) {

            return Observable.from(authenticatorDescriptions)
                .map(new Func1<AuthenticatorDescription, String>() {
                  @Override
                  public String call(AuthenticatorDescription authenticatorDescription) {
                    return authenticatorDescription.type;
                  }
                })
                .toList();
          }
        });
  }

  public static Observable<List<Account>> emails(Context context) {
    return get(context).flatMap(new Func1<List<Account>, Observable<Account>>() {
      @Override
      public Observable<Account> call(List<Account> accounts) {
        return Observable.from(accounts).filter(new Func1<Account, Boolean>() {
          @Override
          public Boolean call(Account account) {
            return Patterns.EMAIL_ADDRESS.matcher(account.name).matches();
          }
        });
      }
    }).toList();
  }

  public static Observable<List<String>> emailsText(Context context) {
    return emails(context).flatMap(new Func1<List<Account>, Observable<List<String>>>() {
      @Override
      public Observable<List<String>> call(List<Account> accounts) {
        return Observable.from(accounts).map(new Func1<Account, String>() {
          @Override
          public String call(Account account) {
            return account.name;
          }
        }).distinct().toList();
      }
    });
  }

  private static boolean checkPermission(Context context, String permission) {
    return ContextCompat.checkSelfPermission(context, permission)
        == PackageManager.PERMISSION_GRANTED;
  }
}
