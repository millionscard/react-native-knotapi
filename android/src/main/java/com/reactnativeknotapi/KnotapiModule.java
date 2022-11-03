package com.reactnativeknotapi;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.module.annotations.ReactModule;
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


  private final OnSessionEventListener onSessionEventListener = new OnSessionEventListener() {
    @Override
    public void onSuccess(String merchant) {
      Log.d("onSuccess from main", merchant);
    }

    @Override
    public void onError(String errorCode, String errorMessage) {
      Log.d("onError", errorCode + " " + errorMessage);
    }

    @Override
    public void onExit() {
      Log.d("onExit", "exit");
    }

    @Override
    public void onFinished() {
      Log.d("onFinished", "finished");
    }

    @Override
    public void onEvent(String eventName, String merchantName) {
      Log.d("onEvent", eventName + " " + merchantName);
    }
  };


  // Example method
  // See https://reactnative.dev/docs/native-modules-android
  @ReactMethod
  public void openCardSwitcher(ReadableMap params) {
    String sessionId = params.getString("sessionId");
    String clientId = params.getString("clientId");
    Customization customizationObj = new Customization();
    int[] merchantsArr;
    String[] merchantNamesArr;
    if (params.hasKey("customization")) {
      ReadableMap customization = params.getMap("customization");
      customizationObj.setPrimaryColor(customization.getString("primaryColor"));
      customizationObj.setTextColor(customization.getString("textColor"));
      customizationObj.setCompanyName(customization.getString("companyName"));
    }
    if (params.hasKey("merchantIds")) {
      ReadableArray merchantIds = params.getArray("merchantIds");
      // convert ReadableArray merchants to array of int
      merchantsArr = new int[merchantIds.size()];
      for (int i = 0; i < merchantIds.size(); i++) {
        merchantsArr[i] = merchantIds.getInt(i);
      }
    } else {
      merchantsArr = new int[]{};
    }
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
    cardOnFileSwitcher = CardOnFileSwitcher.getInstance();
    cardOnFileSwitcher.setMerchantIds(merchantsArr);
    cardOnFileSwitcher.setMerchantNames(merchantNamesArr);
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
    cardOnFileSwitcher.setOnSessionEventListener(onSessionEventListener);
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
    int[] merchantsArr;
    String[] merchantNamesArr;
    if (params.hasKey("customization")) {
      ReadableMap customization = params.getMap("customization");
      customizationObj.setPrimaryColor(customization.getString("primaryColor"));
      customizationObj.setTextColor(customization.getString("textColor"));
      customizationObj.setCompanyName(customization.getString("companyName"));
    }

    if (params.hasKey("merchantIds")) {
      ReadableArray merchantIds = params.getArray("merchantIds");
      // convert ReadableArray merchants to array of int
      merchantsArr = new int[merchantIds.size()];
      for (int i = 0; i < merchantIds.size(); i++) {
        merchantsArr[i] = merchantIds.getInt(i);
      }
    } else {
      merchantsArr = new int[]{};
    }
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

    subscriptionCanceler = subscriptionCanceler.getInstance();
    subscriptionCanceler.setMerchantIds(merchantsArr);
    subscriptionCanceler.setMerchantNames(merchantNamesArr);
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
    subscriptionCanceler.setOnSessionEventListener(onSessionEventListener);
    subscriptionCanceler.openSubscriptionCanceller(amount);
  }

}
