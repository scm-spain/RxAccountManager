package com.alorma.rxaccounts;

import android.accounts.Account;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import com.alorma.androidreactiveaccounts.AccountsSubscriber;
import com.alorma.androidreactiveaccounts.RequestPermissionException;
import com.alorma.androidreactiveaccounts.SimpleAccountsSubscriber;
import java.util.List;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_PERMISSION = 5;
    private AccountAdapter adapter;
    private RecyclerView recyclerView;
    private int selectedTabPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        tabLayout.addTab(tabLayout.newTab().setText("ALL"));
        tabLayout.addTab(tabLayout.newTab().setText("GOOGLE"));
        tabLayout.addTab(tabLayout.newTab().setText("flat(all)"));
        tabLayout.addTab(tabLayout.newTab().setText("flat(google)"));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                                               @Override
                                               public void onTabSelected(TabLayout.Tab tab) {
                                                   adapter.clear();
                                                   selectedTabPosition = tab.getPosition();
                                                   selectTab(selectedTabPosition);
                                               }

                                               @Override
                                               public void onTabUnselected(TabLayout.Tab tab) {

                                               }

                                               @Override
                                               public void onTabReselected(TabLayout.Tab tab) {
                                                   adapter.clear();
                                                   selectedTabPosition = tab.getPosition();
                                                   selectTab(selectedTabPosition);
                                               }
                                           }

        );

        recyclerView = (RecyclerView) findViewById(R.id.recycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new AccountAdapter();

        recyclerView.setAdapter(adapter);

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
        AccountsObservable.createObservable(this).subscribe(new AccountsSubscriber() {
            @Override
            protected void missingPermission(String permission) {
                showPermissionMissing(permission);
            }

            @Override
            protected void onAccountsError(Throwable e) {

            }

            @Override
            public void onCompleted() {
                Snackbar.make(recyclerView, "All accounts loaded in a observable", Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(List<Account> accounts) {
                adapter.addAll(accounts);
            }
        });
    }

    private void getGoogle() {
        AccountsObservable.createObservable(this, "com.google").subscribe(new AccountsSubscriber() {
            @Override
            protected void missingPermission(String permission) {
                showPermissionMissing(permission);
            }

            @Override
            protected void onAccountsError(Throwable e) {

            }

            @Override
            public void onCompleted() {
                Snackbar.make(recyclerView, "Google accounts loaded in a observable", Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(List<Account> accounts) {
                adapter.addAll(accounts);
            }
        });
    }

    private void getAllFlat() {
        AccountsObservable.createFlatMapObservable(this).subscribe(new SimpleAccountsSubscriber() {
            @Override
            protected void missingPermission(String permission) {
                showPermissionMissing(permission);
            }

            @Override
            protected void onAccountsError(Throwable e) {

            }

            @Override
            public void onCompleted() {
                Snackbar.make(recyclerView, "All accounts loaded in flat observable", Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(Account account) {
                adapter.add(account);
            }
        });
    }

    private void getFlatGoogle() {
        AccountsObservable.createFlatMapObservable(this, "com.google").subscribe(new SimpleAccountsSubscriber() {
            @Override
            protected void missingPermission(String permission) {
                showPermissionMissing(permission);
            }

            @Override
            protected void onAccountsError(Throwable e) {

            }

            @Override
            public void onCompleted() {
                Snackbar.make(recyclerView, "Google accounts loaded in flat observable", Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(Account account) {
                adapter.add(account);
            }
        });
    }

    private void showPermissionMissing(final String permission) {
        Snackbar.make(recyclerView, "Missing permission", Snackbar.LENGTH_SHORT).setAction("REQUEST", new View.OnClickListener() {
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
