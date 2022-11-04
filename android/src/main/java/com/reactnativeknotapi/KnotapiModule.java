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
import com.knotapi.cardonfileswitcher.Environment;
import com.knotapi.cardonfileswitcher.OnSessionEventListener;
import com.knotapi.cardonfileswitcher.SubscriptionCanceler;
import com.knotapi.cardonfileswitcher.model.Customization;

@ReactModule(name = KnotapiModule.NAME)
public class KnotapiModule extends ReactContextBaseJavaModule {
  public static final String NAME = "Knotapi";
  CardOnFileSwitcher cardOnFileSwitcher;
  SubscriptionCanceler subscriptionCanceler;
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
      public void onEvent(String eventName, String merchantName) {
        WritableMap params = Arguments.createMap();
        params.putString("event", eventName);
        params.putString("merchant", merchantName);
        sendEvent(getReactApplicationContext(), prefix + "onError", params);
      }
    };
  }

  // Example method
  // See https://reactnative.dev/docs/native-modules-android
  @ReactMethod
  public void openCardSwitcher(ReadableMap params) {
    String sessionId = params.getString("sessionId");
    String clientId = params.getString("clientId");
    Customization customizationObj = new Customization();
    int[] merchantsArr;
    if (params.hasKey("customization")) {
      ReadableMap customization = params.getMap("customization");
      customizationObj.setPrimaryColor(customization.getString("primaryColor"));
      customizationObj.setTextColor(customization.getString("textColor"));
      customizationObj.setCompanyName(customization.getString("companyName"));
    }
    if (params.hasKey("merchants")) {
      ReadableArray merchants = params.getArray("merchants");
      // convert ReadableArray merchants to array of int
      merchantsArr = new int[merchants.size()];
      for (int i = 0; i < merchants.size(); i++) {
        merchantsArr[i] = merchants.getInt(i);
      }
    } else {
      merchantsArr = new int[]{};
    }
    cardOnFileSwitcher = CardOnFileSwitcher.getInstance();
    cardOnFileSwitcher.setMerchantIds(merchantsArr);
    if (params.hasKey("environment")) {
      String environment = params.getString("environment");
      if (environment.equals("sandbox")) {
        cardOnFileSwitcher.init(context, sessionId, clientId, Environment.SANDBOX);
      } else {
        cardOnFileSwitcher.init(context, sessionId, clientId, Environment.PRODUCTION);
      }
    } else {
      cardOnFileSwitcher.init(context, sessionId, clientId, Environment.PRODUCTION);
    }
    cardOnFileSwitcher.setCustomization(customizationObj);
    cardOnFileSwitcher.setOnSessionEventListener(getOnSessionEventListener("CardSwitcher-"));
    cardOnFileSwitcher.openCardOnFileSwitcher();
  }

  @ReactMethod
  public void openSubscriptionCanceler(ReadableMap params) {
    String sessionId = params.getString("sessionId");
    String clientId = params.getString("clientId");
    boolean amount = false;
    if (params.hasKey("amount")) {
      amount = params.getBoolean("amount");
    }
    Customization customizationObj = new Customization();
    if (params.hasKey("customization")) {
      ReadableMap customization = params.getMap("customization");
      customizationObj.setPrimaryColor(customization.getString("primaryColor"));
      customizationObj.setTextColor(customization.getString("textColor"));
      customizationObj.setCompanyName(customization.getString("companyName"));
    }
    subscriptionCanceler = subscriptionCanceler.getInstance();
    if (params.hasKey("environment")) {
      String environment = params.getString("environment");
      if (environment.equals("sandbox")) {
        subscriptionCanceler.init(context, sessionId, clientId, Environment.SANDBOX);
      } else {
        subscriptionCanceler.init(context, sessionId, clientId, Environment.PRODUCTION);
      }
    } else {
      subscriptionCanceler.init(context, sessionId, clientId, Environment.PRODUCTION);
    }
    subscriptionCanceler.setCustomization(customizationObj);
    subscriptionCanceler.setOnSessionEventListener(getOnSessionEventListener("SubscriptionCanceler-"));
    subscriptionCanceler.openSubscriptionCanceller(amount);
  }

}
