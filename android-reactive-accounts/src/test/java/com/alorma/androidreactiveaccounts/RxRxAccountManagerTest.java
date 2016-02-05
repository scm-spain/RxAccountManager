package com.alorma.androidreactiveaccounts;

import android.support.annotation.NonNull;

import com.alorma.androidreactiveaccounts.accountmanager.AccountManagerProvider;
import com.alorma.androidreactiveaccounts.accountmanager.RxAccount;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static junit.framework.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

public class RxRxAccountManagerTest {

  @Mock
  AccountManagerProvider accountManagerProvider;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testGetAccounts() {
    List<RxAccount> expected = getFakeAccounts();

    given(accountManagerProvider.getAccounts()).willReturn(expected);

    List<RxAccount> result = RxAccountsManager.get(accountManagerProvider).toBlocking().first();

    assertEquals(expected, result);
  }

  @Test
  public void testGetAccountsFilterMail() {
    List<RxAccount> givenData = getFakeAccounts();
    List<RxAccount> emailsMockData = getMailAccounts();

    given(accountManagerProvider.getAccounts()).willReturn(givenData);
    given(accountManagerProvider.patternMail()).willReturn(mockPatternMail());

    List<RxAccount> result = RxAccountsManager.emails(accountManagerProvider).toBlocking().first();

    assertEquals(emailsMockData, result);
  }

  @Test
  public void testGetAccountsFilterMailText() {
    List<RxAccount> givenData = getFakeAccounts();
    List<String> emailsMockData = new ArrayList<>();

    for (RxAccount rxAccount : getMailAccounts()) {
      emailsMockData.add(rxAccount.getName());
    }

    given(accountManagerProvider.getAccounts()).willReturn(givenData);
    given(accountManagerProvider.patternMail()).willReturn(mockPatternMail());

    List<String> result = RxAccountsManager.emailsText(accountManagerProvider).toBlocking().first();

    assertEquals(emailsMockData, result);
  }

  @NonNull
  private List<RxAccount> getFakeAccounts() {
    List<RxAccount> expected = new ArrayList<>();
    expected.add(new RxAccount("aaa", "typea"));
    expected.add(new RxAccount("bbb", "typeb"));
    expected.add(new RxAccount("ccc", "typea"));
    expected.add(new RxAccount("ddd", "typeb"));
    expected.addAll(getMailAccounts());
    return expected;
  }

  private List<RxAccount> getMailAccounts() {
    List<RxAccount> accounts = new ArrayList<>();
    accounts.add(new RxAccount("aaa@aaa.aaa", "typex"));
    accounts.add(new RxAccount("bbb@bbb.bb", "typey"));
    return accounts;
  }

  private Pattern mockPatternMail() {
    return Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
            "\\@" +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
            "(" +
            "\\." +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
            ")+"
    );
  }
}
