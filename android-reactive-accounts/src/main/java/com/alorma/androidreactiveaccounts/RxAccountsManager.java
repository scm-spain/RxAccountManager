package com.alorma.androidreactiveaccounts;

import com.alorma.androidreactiveaccounts.accountmanager.AccountManagerProvider;
import com.alorma.androidreactiveaccounts.accountmanager.RxAccount;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

public class RxAccountsManager {

  public static Observable<List<RxAccount>> get(final AccountManagerProvider accountManager) {
    return Observable.create(subscriber ->  {
          if (!subscriber.isUnsubscribed()) {
            try {
              List<RxAccount> accounts = accountManager.getAccounts();
              subscriber.onNext(accounts);
              subscriber.onCompleted();
            } catch (Exception e) {
              subscriber.onError(e);
            }
          }
        }
    );
  }

  public static Observable<List<RxAccount>> emails(AccountManagerProvider accountManager) {
    return get(accountManager).flatMap(accounts -> Observable.from(accounts)
        .filter((Func1<RxAccount, Boolean>) account ->
            accountManager.patternMail().matcher(account.name).matches()))
        .toList();
  }

  public static Observable<List<String>> emailsText(AccountManagerProvider accountManager) {
    return emails(accountManager).flatMap(accounts -> Observable.from(accounts)
        .map(account -> account.name).distinct().toList());
  }
}
