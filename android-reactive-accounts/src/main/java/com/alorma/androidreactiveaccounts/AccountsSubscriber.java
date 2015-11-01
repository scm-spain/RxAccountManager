package com.alorma.androidreactiveaccounts;

import android.accounts.Account;
import java.util.List;
import rx.Subscriber;

/**
 * Created by bernat.borras on 1/11/15.
 */
public abstract class AccountsSubscriber extends Subscriber<List<Account>> {

    @Override
    public void onError(Throwable e) {
        if (e instanceof RequestPermissionException) {
            missingPermission(((RequestPermissionException) e).getPermission());
        } else {
            onAccountsError(e);
        }
    }

    protected abstract void missingPermission(final String permission);

    protected abstract void onAccountsError(Throwable e);
}
