package com.alorma.rxaccounts;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;
import com.alorma.androidreactiveaccounts.RequestPermissionException;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by bernat.borras on 2/11/15.
 */
public abstract class BaseDemoActivity<T> extends AppCompatActivity {

  private static final int REQUEST_PERMISSION = 5;

  protected RecyclerView recyclerView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.content_demo);

    recyclerView = (RecyclerView) findViewById(R.id.recycler);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    recyclerView.setAdapter(getAdapter());

    execute();
  }

  protected abstract RecyclerView.Adapter getAdapter();

  protected void execute() {
    Observable<T> observable = getObservable();
    if (observable != null) {
      observable.subscribe(new Subscriber<T>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
          if (e instanceof RequestPermissionException) {
            showPermissionMissing(((RequestPermissionException) e).getPermission());
          } else {
            showError(e);
          }
        }

        @Override
        public void onNext(T t) {
          onResult(t);
        }
      });
    }
  }

  private void showError(Throwable e) {
    Snackbar.make(recyclerView, "Error: " + e.getMessage(), Snackbar.LENGTH_SHORT).show();
  }

  protected abstract Observable<T> getObservable();

  protected abstract void onResult(T t);

  private void showPermissionMissing(final String permission) {
    Snackbar.make(recyclerView, "Missing permission", Snackbar.LENGTH_INDEFINITE)
        .setAction("REQUEST", new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(BaseDemoActivity.this,
                permission)) {
              Toast.makeText(BaseDemoActivity.this, "Permission " + permission + " is needed",
                  Toast.LENGTH_SHORT).show();
            }
            ActivityCompat.requestPermissions(BaseDemoActivity.this, new String[] { permission },
                REQUEST_PERMISSION);
          }
        })
        .show();
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, String[] permissions,
      int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    if (requestCode == REQUEST_PERMISSION) {
      if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        execute();
      }
    }
  }
}
