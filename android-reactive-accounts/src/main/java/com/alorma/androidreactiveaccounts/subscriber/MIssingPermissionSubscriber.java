package com.alorma.androidreactiveaccounts.subscriber;

import com.alorma.androidreactiveaccounts.RequestPermissionException;
import rx.Subscriber;

/**
 * Created by bernat.borras on 2/11/15.
 */
public abstract class MissingPermissionSubscriber<T> extends Subscriber<T> {

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
