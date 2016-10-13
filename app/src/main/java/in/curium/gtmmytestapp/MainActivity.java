package in.curium.gtmmytestapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.google.android.gms.tagmanager.PreviewActivity;

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
  }

  @Override
  protected void onResume() {
    super.onResume();
    iAnalytics.track("pageview", iAnalytics.mapOf("test1", "test2"));
    ContainerHolderSingleton.getContainerHolder().refresh();
  }
}
