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
> `List<RxAccount>`

```
AccountsObservable.emails(getAccountManager);
```
> `List<RxAccount>`

```
AccountsObservable.emailsText(getAccountManager);
```

> `List<String>`

## DOWNLOAD

```
compile 'com.github.alorma:rxaccounts:0.0.3'
```
## LICENSE

Copyright 2016 Bernat Borrás

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
