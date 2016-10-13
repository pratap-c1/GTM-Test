package in.curium.gtmmytestapp;

import android.app.Application;
import android.util.Log;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.tagmanager.Container;
import com.google.android.gms.tagmanager.ContainerHolder;
import com.google.android.gms.tagmanager.TagManager;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class App extends Application {
  private final long TIMEOUT_FOR_CONTAINER_OPEN_MILLISECONDS = 2000L;
  private final String GA_ID = "GTM-5L457M"; // "GTM-M2XVS9";

  @Override
  public void onCreate() {
    super.onCreate();
    GoogleAnalytics ga = GoogleAnalytics.get();
    ga.init(getApplicationContext());
    TagManager tagManager = TagManager.getInstance(getApplicationContext());
    PendingResult<ContainerHolder> pending =
        tagManager.loadContainerPreferNonDefault(GA_ID, R.raw.gtm_5l457m);
    tagManager.setVerboseLoggingEnabled(true);
    pending.setResultCallback(new ResultCallback<ContainerHolder>() {
      @Override
      public void onResult(ContainerHolder containerHolder) {
        Log.d("app", "entering onResult");
        ContainerHolderSingleton.setContainerHolder(containerHolder);
        Container container = containerHolder.getContainer();
        if (!containerHolder.getStatus().isSuccess()) {
          return;
        }
        ContainerLoadedCallback.registerCallbacksForContainer(container);
        containerHolder.setContainerAvailableListener(new ContainerLoadedCallback());
        //startMainActivity();
      }
    }, TIMEOUT_FOR_CONTAINER_OPEN_MILLISECONDS, TimeUnit.MILLISECONDS);
  }

  private static class ContainerLoadedCallback
      implements ContainerHolder.ContainerAvailableListener {
    public static void registerCallbacksForContainer(Container container) {
      // Register two custom function call macros to the container.
      container.registerFunctionCallMacroCallback("increment", new CustomMacroCallback());
      container.registerFunctionCallMacroCallback("mod", new CustomMacroCallback());
      // Register a custom function call tag to the container.
      container.registerFunctionCallTagCallback("custom_tag", new CustomTagCallback());
    }

    @Override
    public void onContainerAvailable(ContainerHolder containerHolder, String containerVersion) {
      // We load each container when it becomes available.
      Container container = containerHolder.getContainer();
      registerCallbacksForContainer(container);
    }
  }

  private static class CustomMacroCallback implements Container.FunctionCallMacroCallback {
    private int numCalls;

    @Override
    public Object getValue(String name, Map<String, Object> parameters) {
      if ("increment".equals(name)) {
        return ++numCalls;
      } else if ("mod".equals(name)) {
        return (Long) parameters.get("key1") % Integer.valueOf((String) parameters.get("key2"));
      } else {
        throw new IllegalArgumentException("Custom macro name: " + name + " is not supported.");
      }
    }
  }

  private static class CustomTagCallback implements Container.FunctionCallTagCallback {
    @Override
    public void execute(String tagName, Map<String, Object> parameters) {
      // The code for firing this custom tag.
      Log.i("CuteAnimals", "Custom function call tag :" + tagName + " is fired.");
    }
  }
}
