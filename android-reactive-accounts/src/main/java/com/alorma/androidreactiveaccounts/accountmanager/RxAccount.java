package com.alorma.androidreactiveaccounts.accountmanager;

public class RxAccount {
  public final String name;
  public final String type;

  public RxAccount(String name, String type) {
    this.name = name;
    this.type = type;
  }

  public String getName() {
    return name;
  }

  public String getType() {
    return type;
  }

  public boolean equals(Object o) {
    if (o == this) return true;
    if (!(o instanceof RxAccount)) return false;
    final RxAccount other = (RxAccount)o;
    return name.equals(other.name) && type.equals(other.type);
  }

  public int hashCode() {
    int result = 17;
    result = 31 * result + name.hashCode();
    result = 31 * result + type.hashCode();
    return result;
  }
}
