package com.alorma.rxaccounts;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

/**
 * Created by bernat.borras on 2/11/15.
 */
public abstract class BaseDemoActivity<T> extends AppCompatActivity {

  private static final int REQUEST_PERMISSION = 5;

  private RecyclerView recyclerView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.content_demo);

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    if (getSupportActionBar() != null) {
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    recyclerView = (RecyclerView) findViewById(R.id.recycler);
  }

  protected void execute() {

  }

  private void showPermissionMissing(final String permission) {
    Snackbar.make(recyclerView, "Missing permission", Snackbar.LENGTH_SHORT)
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
