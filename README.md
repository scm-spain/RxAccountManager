[![Build Status](https://travis-ci.org/alorma/RxAccountManager.svg?branch=master)](https://travis-ci.org/alorma/RxAccountManager)
[ ![Download](https://api.bintray.com/packages/alorma/maven/rxaccounts/images/download.svg) ](https://bintray.com/alorma/maven/rxaccounts/_latestVersion)

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

## DOWNLOAD

```
compile 'com.github.alorma:rxaccounts:0.0.3'
```
