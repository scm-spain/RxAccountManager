!https://travis-ci.org/alorma/RxAccountManager.svg?branch=master!:https://travis-ci.org/alorma/RxAccountManager

# RxAccounts

Small library that wraps Account manager API in RxJava Observables reducing boilerplate to minimum.

### OBSERVABLES
```
public AccountManagerProvider getAccountManager() {
    return new AndroidAccountManagerProvider(AccountManager.get(this));
}
```

```
RxAccountsManager.get(getAccountManager());
```
> List<RxAccount>
```
AccountsObservable.emails(getAccountManager);
```
> List<RxAccount>
```
AccountsObservable.emailsText(getAccountManager);
```
> List<String>


### SUBSCRIBER

```
new MissingPermissionSubscriber<T>() {
    @Override
    protected void missingPermission(String permission) {
        ...
    }

    @Override
    protected void onAccountsError(Throwable e) {
        ...
    }

    @Override
    public void onCompleted() {
        ...
    }

    @Override
    public void onNext(T t) {
        ...
    }
});
```
```

## DOWNLOAD

```
compile 'com.github.alorma:android-reactive-rxAccounts:0.0.2'
```