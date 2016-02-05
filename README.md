# RxAccounts

Small library that wraps Account manager API in RxJava Observables reducing boilerplate to minimum.

## API

Really simple. All you need is to call ```AccountsObservable.createObservable(...);```. All observables are already there.

### OBSERVABLES
```
AccountsObservable.createObservable(this);
```
```
AccountsObservable.createObservable(this, "com.google");
```
```
AccountsObservable.createFlatMapObservable(this);
```
```
AccountsObservable.createFlatMapObservable(this, "com.google");
```

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
compile 'com.github.alorma:android-reactive-rxAccounts:0.0.1'
```

## TODO

* Add methods to create / modify / delete account