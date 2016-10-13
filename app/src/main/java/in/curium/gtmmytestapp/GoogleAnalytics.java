package in.curium.gtmmytestapp;

import android.content.Context;
import android.util.Log;
import com.google.android.gms.tagmanager.DataLayer;
import com.google.android.gms.tagmanager.TagManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GoogleAnalytics implements IAnalytics {
  private static GoogleAnalytics ga;
  private final String TAG = GoogleAnalytics.class.getCanonicalName();
  private DataLayer dataLayer;

  private GoogleAnalytics() {

  }

  public static GoogleAnalytics get() {
    if (ga != null) {
      return ga;
    }
    synchronized (GoogleAnalytics.class) {
      if (ga == null) {
        ga = new GoogleAnalytics();
      }
    }
    return ga;
  }

  public void init(Context context) {
    dataLayer = TagManager.getInstance(context).getDataLayer();
  }

  @Override
  public void track(String eventName, Map<String, Object> valuesToPush) {

    dataLayer.pushEvent(eventName, valuesToPush);
    Log.d("valuesToPush>>>>>>>", valuesToPush.size() + "valuesToPush:" + valuesToPush);
  }

  @Override
  public List<Object> listOf(Object... objects) {
    return DataLayer.listOf(objects);
  }

  @Override
  public Map<String, Object> mapOf(Object... objects) {
    Map<String, Object> map = new HashMap<>();
    try {
      map = DataLayer.mapOf(objects);
    } catch (IllegalArgumentException e) {
      Log.e(TAG, e.getMessage());
      e.printStackTrace();
    }
    return map;
  }


  /*private Map<String, Object> createMap(Map<String, Object> valuesToCreateFinalMap) {
    Map<String, Object> map = new HashMap<>();
    Iterator<String> iterator = valuesToCreateFinalMap.keySet().iterator();

    while (iterator.hasNext()) {
      String key = iterator.next();
      Object value = valuesToCreateFinalMap.get(key);

      if (value instanceof String) {
        map.put(key, value);
      } else if (value instanceof HashMap) {
        Map<String, Object> map1 = createMap((Map<String, Object>) value);
        Log.d("", map1.size() + "map1:" + map1);

        map.put(key, DataLayer.mapOf(map1));
      } else if (value instanceof ArrayList) {
        map.put(key, DataLayer.listOf(iterateList((ArrayList<HashMap<String, Object>>) value)));
      }
    }
    return map;
  }

  private Map<String, Object> iterateList(ArrayList<HashMap<String, Object>> list) {
    Map<String, Object> map = new HashMap<>();
    Iterator iterator = list.iterator();

    while (iterator.hasNext()) {
      HashMap<String, Object> value = (HashMap<String, Object>) iterator.next();

      map = createMap(value);
    }
    return map;
  }*/
}
