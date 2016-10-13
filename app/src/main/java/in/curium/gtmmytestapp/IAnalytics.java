package in.curium.gtmmytestapp;

import java.util.List;
import java.util.Map;

public interface IAnalytics {

  //int GET_ADOBE = 1;
  int GET_GA = 1;

  void track(String eventName, Map<String, Object> valuesToPush);

  List<Object> listOf(Object... objects);

  Map<String, Object> mapOf(Object... objects);

  class IAnalyticsFactory {
    static public IAnalytics get(int getAnalyticsTypeId) {
      switch (getAnalyticsTypeId) {
        case GET_GA:
          return GoogleAnalytics.get();
        default:
          return null;
      }
    }
  }
}