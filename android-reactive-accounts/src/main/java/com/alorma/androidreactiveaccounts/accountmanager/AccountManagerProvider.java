package com.alorma.androidreactiveaccounts.accountmanager;

import java.util.List;
import java.util.regex.Pattern;

public interface AccountManagerProvider {

  List<RxAccount> getAccounts();

  Pattern patternMail();
}
