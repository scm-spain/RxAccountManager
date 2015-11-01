# RxAccounts

Small library that wraps Account manager API in RxJava Observables reducing boilerplate to minimum.

## API

Really simple. All you need is to call ```AccountsObservable.createObservable(Context context, String accountType);```. All observables are already there.

```
AccountsObservable.createObservable(this, "com.google").subscribe(new Subscriber<List<Account>>() {
    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onNext(List<Account> accounts) {

    }
});
```