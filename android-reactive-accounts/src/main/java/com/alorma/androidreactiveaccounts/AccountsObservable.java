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

/**
 * Created by bernat.borras on 1/11/15.
 */
public class AccountsObservable implements Observable.OnSubscribe<List<Account>> {

    private WeakReference<Context> contextWeakReference;
    private String accountType;

    private AccountsObservable(Context ctx, String accountType) {
        this.accountType = accountType;

        this.contextWeakReference = new WeakReference<>(ctx);
    }

    public static Observable<List<Account>> createObservable(Context ctx, @NonNull String accountType) {
        return Observable.create(new AccountsObservable(ctx, accountType));
    }


    @Override
    public void call(Subscriber<? super List<Account>> subscriber) {
        if (contextWeakReference != null && contextWeakReference.get() != null) {
            if (ContextCompat.checkSelfPermission(contextWeakReference.get(), Manifest.permission.GET_ACCOUNTS) == PackageManager.PERMISSION_GRANTED) {
                AccountManager accountManager = AccountManager.get(contextWeakReference.get());
                Account[] accountsByType = accountManager.getAccountsByType(accountType);

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
