package in.curium.gtmmytestapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import com.google.android.gms.tagmanager.DataLayer;
import com.google.android.gms.tagmanager.PreviewActivity;
import com.google.android.gms.tagmanager.TagManager;

public class MainActivity extends AppCompatActivity {

  IAnalytics iAnalytics;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    iAnalytics = IAnalytics.IAnalyticsFactory.get(IAnalytics.GET_GA);
    findViewById(R.id.gtm_btn).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(new Intent(MainActivity.this, PreviewActivity.class));
      }
    });
    mHandler = new Handler();
  }
  boolean DISABLE = true;
  Handler mHandler;
  Runnable mRefresh = new Runnable() {
    @Override
    public void run() {
      if (DISABLE) return;
      Log.d("run", "in run");
      if (ContainerHolderSingleton.getContainerHolder() != null) {
        Log.d("run", "singleton not null");
        ContainerHolderSingleton.getContainerHolder().refresh();
      }
    }
  };

  @Override
  protected void onResume() {
    super.onResume();
    //iAnalytics.track("pageview", iAnalytics.mapOf("test1", "test2"));
    DataLayer dataLayer = TagManager.getInstance(this).getDataLayer();
    dataLayer.pushEvent("mainActivityOpen", DataLayer.mapOf("screenName", "MainActivity"));
    mHandler.postDelayed(mRefresh, 5000L);
  }

  @Override
  protected void onPause() {
    mHandler.removeCallbacks(mRefresh);
    super.onPause();
  }
}
