package com.alorma.rxaccounts;

import android.accounts.Account;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;
import com.alorma.androidreactiveaccounts.AccountsObservable;
import com.alorma.androidreactiveaccounts.RequestPermissionException;
import java.util.List;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_PERMISSION = 5;
    private Subscriber<Account> subscriber;
    private AccountAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new AccountAdapter();

        recyclerView.setAdapter(adapter);

        createSubscriber();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getAccounts();
            }
        });
    }

    private void getAccounts() {
        AccountsObservable.createObservable(this, "com.google").flatMap(new Func1<List<Account>, Observable<Account>>() {
            @Override
            public Observable<Account> call(List<Account> accounts) {
                return Observable.from(accounts);
            }
        }).subscribe(subscriber);
    }

    private void createSubscriber() {
        subscriber = new Subscriber<Account>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(final Throwable e) {
                if (e instanceof RequestPermissionException) {
                    Snackbar.make(recyclerView, "Missing permission", Snackbar.LENGTH_SHORT)
                        .setAction("REQUEST", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String permission = ((RequestPermissionException) e).getPermission();
                                if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, permission)) {
                                    Toast.makeText(MainActivity.this, "Permission " + permission + " is needed" , Toast.LENGTH_SHORT).show();
                                } else {
                                    ActivityCompat.requestPermissions(MainActivity.this, new String[] { permission },
                                        REQUEST_PERMISSION);
                                }
                            }
                        })
                        .show();
                } else {
                    Snackbar.make(recyclerView, e.getMessage(), Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNext(Account account) {
                adapter.add(account);
            }
        };
    }
}
