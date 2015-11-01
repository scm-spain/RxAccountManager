package com.alorma.androidreactiveaccounts;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.List;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by bernat.borras on 1/11/15.
 */
public class AccountsObservable implements Observable.OnSubscribe<List<Account>> {

    private WeakReference<Context> contextWeakReference;
    private String accountType;

    private AccountsObservable(Context ctx) {
        this.contextWeakReference = new WeakReference<>(ctx);

    }

    private AccountsObservable(Context ctx, String accountType) {
        this(ctx);
        this.accountType = accountType;
    }

    public static Observable<List<Account>> createObservable(Context ctx) {
        return Observable.create(new AccountsObservable(ctx));
    }

    public static Observable<List<Account>> createObservable(Context ctx, @NonNull String accountType) {
        return Observable.create(new AccountsObservable(ctx, accountType));
    }

    public static Observable<Account> createFlatMapObservable(Context ctx) {
        Func1<? super List<Account>, Observable<Account>> flatMap = new Func1<List<Account>, Observable<Account>>() {
            @Override
            public Observable<Account> call(List<Account> accounts) {
                return Observable.from(accounts);
            }
        };
        return Observable.create(new AccountsObservable(ctx)).flatMap(flatMap);
    }

    public static Observable<Account> createFlatMapObservable(Context ctx, @NonNull String accountType) {
        Func1<? super List<Account>, Observable<Account>> flatMap = new Func1<List<Account>, Observable<Account>>() {
            @Override
            public Observable<Account> call(List<Account> accounts) {
                return Observable.from(accounts);
            }
        };
        return Observable.create(new AccountsObservable(ctx, accountType)).flatMap(flatMap);
    }

    @Override
    public void call(Subscriber<? super List<Account>> subscriber) {
        if (contextWeakReference != null && contextWeakReference.get() != null) {
            if (ContextCompat.checkSelfPermission(contextWeakReference.get(), Manifest.permission.GET_ACCOUNTS) == PackageManager.PERMISSION_GRANTED) {
                AccountManager accountManager = AccountManager.get(contextWeakReference.get());

                Account[] accountsByType;
                if (accountType == null) {
                    accountsByType = accountManager.getAccounts();
                } else {
                    accountsByType = accountManager.getAccountsByType(accountType);
                }

                subscriber.onNext(Arrays.asList(accountsByType));
                subscriber.onCompleted();

            } else {
                subscriber.onError(new RequestPermissionException(Manifest.permission.GET_ACCOUNTS));
            }
        } else {
            subscriber.onError(new NullPointerException("Context is dead"));
        }
    }
}
