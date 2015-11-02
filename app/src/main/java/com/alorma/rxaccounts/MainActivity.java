package com.alorma.rxaccounts;

import android.accounts.Account;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;
import com.alorma.androidreactiveaccounts.AccountsObservable;
import com.alorma.androidreactiveaccounts.subscriber.MissingPermissionSubscriber;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_PERMISSION = 5;
    private int selectedTabPosition;
    private View contentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        contentView = findViewById(R.id.content);

        selectedTabPosition = 0;
        selectTab(selectedTabPosition);
    }

    private void selectTab(int selectedTabPosition) {
        switch (selectedTabPosition) {
            case 0:
                getAllAccounts();
                break;
            case 1:
                getGoogle();
                break;
            case 2:
                getAllFlat();
                break;
            case 3:
                getFlatGoogle();
                break;
        }
    }

    private void getAllAccounts() {
        AccountsObservable.createObservable(this).subscribe(new MissingPermissionSubscriber<List<Account>>() {
            @Override
            protected void missingPermission(String permission) {
                showPermissionMissing(permission);
            }

            @Override
            protected void onAccountsError(Throwable e) {

            }

            @Override
            public void onCompleted() {
                Snackbar.make(contentView, "All accounts loaded in a observable", Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(List<Account> accounts) {
                adapter.addAll(accounts);
            }
        });
    }

    private void getGoogle() {
        AccountsObservable.createObservable(this, "com.google").subscribe(new MissingPermissionSubscriber<List<Account>>() {
            @Override
            protected void missingPermission(String permission) {
                showPermissionMissing(permission);
            }

            @Override
            protected void onAccountsError(Throwable e) {

            }

            @Override
            public void onCompleted() {
                Snackbar.make(contentView, "Google accounts loaded in a observable", Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(List<Account> accounts) {
                adapter.addAll(accounts);
            }
        });
    }

    private void getAllFlat() {
        AccountsObservable.createFlatMapObservable(this).subscribe(new MissingPermissionSubscriber<Account>() {
            @Override
            protected void missingPermission(String permission) {
                showPermissionMissing(permission);
            }

            @Override
            protected void onAccountsError(Throwable e) {

            }

            @Override
            public void onCompleted() {
                Snackbar.make(contentView, "All accounts loaded in flat observable", Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(Account account) {
                adapter.add(account);
            }
        });
    }

    private void getFlatGoogle() {
        AccountsObservable.createFlatMapObservable(this, "com.google").subscribe(new MissingPermissionSubscriber<Account>() {
            @Override
            protected void missingPermission(String permission) {
                showPermissionMissing(permission);
            }

            @Override
            protected void onAccountsError(Throwable e) {

            }

            @Override
            public void onCompleted() {
                Snackbar.make(contentView, "Google accounts loaded in flat observable", Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(Account account) {
                adapter.add(account);
            }
        });
    }

    private void showPermissionMissing(final String permission) {
        Snackbar.make(contentView, "Missing permission", Snackbar.LENGTH_SHORT).setAction("REQUEST", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, permission)) {
                    Toast.makeText(MainActivity.this, "Permission " + permission + " is needed", Toast.LENGTH_SHORT).show();
                }
                ActivityCompat.requestPermissions(MainActivity.this, new String[] { permission }, REQUEST_PERMISSION);
            }
        }).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                selectTab(selectedTabPosition);
            }
        }
    }
}
