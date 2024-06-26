package com.reactnativeknotapi;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.knotapi.cardonfileswitcher.CardOnFileSwitcher;
import com.knotapi.cardonfileswitcher.models.Configuration;
import com.knotapi.cardonfileswitcher.models.Environment;
import com.knotapi.cardonfileswitcher.interfaces.OnSessionEventListener;
import com.knotapi.cardonfileswitcher.models.Options;

@ReactModule(name = KnotapiModule.NAME)
public class KnotapiModule extends ReactContextBaseJavaModule {
  public static final String NAME = "Knotapi";
  CardOnFileSwitcher cardOnFileSwitcher;
  Context context;

  public KnotapiModule(ReactApplicationContext reactContext) {
    super(reactContext);
    context = reactContext;
  }

  @Override
  @NonNull
  public String getName() {
    return NAME;
  }

  private void sendEvent(ReactContext reactContext,
                         String eventName,
                         @Nullable WritableMap params) {
    reactContext
      .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
      .emit(eventName, params);
  }

  OnSessionEventListener getOnSessionEventListener(String prefix) {
    return new OnSessionEventListener() {
      @Override
      public void onSuccess(String merchant) {
        WritableMap params = Arguments.createMap();
        params.putString("merchant", merchant);
        sendEvent(getReactApplicationContext(), prefix + "onSuccess", params);
      }

      @Override
      public void onError(String errorCode, String errorMessage) {
        WritableMap params = Arguments.createMap();
        params.putString("message", errorMessage);
        params.putString("error", errorCode);
        sendEvent(getReactApplicationContext(), prefix + "onError", params);
      }

      @Override
      public void onExit() {
        sendEvent(getReactApplicationContext(), prefix + "onExit", null);
      }

      @Override
      public void onFinished() {
        sendEvent(getReactApplicationContext(), prefix + "onFinished", null);
      }

      @Override
      public void onEvent(String eventName, String merchantName, String taskId) {
        WritableMap params = Arguments.createMap();
        params.putString("event", eventName);
        params.putString("merchant", merchantName);
        if (taskId != null && !taskId.trim().isEmpty()) {
           params.putString("taskId", taskId);
        }
        sendEvent(getReactApplicationContext(), prefix + "onEvent", params);
      }
    };
  }

  public Environment getEnvironment(@Nullable String env) {
    switch (env) {
      case "sandbox":
        return Environment.SANDBOX;
      case "development":
        return Environment.DEVELOPMENT;
      default:
        return Environment.PRODUCTION;
    }
  }

  public Configuration getConfiguration(ReadableMap params) {
    String sessionId = params.getString("sessionId");
    String clientId = params.getString("clientId");
    Environment environment = params.hasKey("environment") ? getEnvironment(params.getString("environment")) : null;
    Configuration configuration = new Configuration(environment, clientId, sessionId);
    return configuration;
  }

  public @Nullable Options getOptions(ReadableMap params) {
    boolean useCategories = params.hasKey("useCategories") ? params.getBoolean("useCategories") : false;
    boolean useSearch = params.hasKey("useSearch") ? params.getBoolean("useSearch") : false;
    boolean amount = params.hasKey("amount") ? params.getBoolean("amount") : false;
    @Nullable Options options = new Options();

    @Nullable int[] merchantIdsArr;
    if (params.hasKey("merchantIds")) {
      ReadableArray merchantIds = params.getArray("merchantIds");
      // convert ReadableArray merchants to array of int
      merchantIdsArr = new int[merchantIds.size()];
      for (int i = 0; i < merchantIds.size(); i++) {
        merchantIdsArr[i] = merchantIds.getInt(i);
      }
    } else {
      merchantIdsArr = new int[]{};
    }

    @Nullable String[] merchantNamesArr;
    if (params.hasKey("merchantNames")) {
      ReadableArray merchantNames = params.getArray("merchantNames");
      // convert ReadableArray merchants to array of int
      merchantNamesArr = new String[merchantNames.size()];
      for (int i = 0; i < merchantNames.size(); i++) {
        merchantNamesArr[i] = merchantNames.getString(i);
      }
    } else {
      merchantNamesArr = new String[]{};
    }

    options.setMerchantIds(merchantIdsArr);
    options.setMerchantNames(merchantNamesArr);
    options.setUseCategories(useCategories);
    options.setUseSearch(useSearch);

    return options;
  }

  // Example method
  // See https://reactnative.dev/docs/native-modules-android
  @ReactMethod
  public void openCardSwitcher(ReadableMap params) {
    String entryPoint = params.getString("entryPoint");
    Configuration configuration = getConfiguration(params);
    Options options = getOptions(params);
    cardOnFileSwitcher = CardOnFileSwitcher.getInstance();
    cardOnFileSwitcher.init(context, configuration, options, getOnSessionEventListener("CardSwitcher-"));
    if (entryPoint != null && !entryPoint.equals("")) {
      cardOnFileSwitcher.openCardOnFileSwitcher(entryPoint);
    } else {
      cardOnFileSwitcher.openCardOnFileSwitcher();
    }
  }

  @ReactMethod
  public void updateCardSwitcherSessionId(String sessionId) {
    cardOnFileSwitcher.updateSession(sessionId);
  }

  @ReactMethod
  public void openSubscriptionCanceler(ReadableMap params) {
        // TODO: Invoke subscription canceler
  }
}
