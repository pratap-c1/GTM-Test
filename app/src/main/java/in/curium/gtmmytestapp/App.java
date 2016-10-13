package in.curium.gtmmytestapp;

import android.app.Application;

public class App extends Application {
  @Override
  public void onCreate() {
    super.onCreate();
    GoogleAnalytics ga = GoogleAnalytics.get();
    ga.init(getApplicationContext());
  }
}
