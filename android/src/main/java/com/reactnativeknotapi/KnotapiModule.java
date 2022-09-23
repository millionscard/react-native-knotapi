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
import com.knotapi.cardonfileswitcher.model.Customization;

@ReactModule(name = KnotapiModule.NAME)
public class KnotapiModule extends ReactContextBaseJavaModule {
  public static final String NAME = "Knotapi";
  CardOnFileSwitcher cardOnFileSwitcher;
  Context context;

  public KnotapiModule(ReactApplicationContext reactContext) {
    super(reactContext);
    context = (Context) reactContext;
  }

  @Override
  @NonNull
  public String getName() {
    return NAME;
  }


  private final OnSessionEventListener onSessionEventListener = new OnSessionEventListener() {
    @Override
    public void onInvalidSession(String sessionId, String errorMessage) {
      Log.d("onInvalidSession", errorMessage);
    }

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
    boolean isCancel = false;
    if (params.hasKey("isCancel")){
      isCancel = params.getBoolean("isCancel");
    }
    // handle customization object
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
    if (params.hasKey("environment")) {
      String environment = params.getString("environment");
      if (environment.equals("sandbox")) {
        cardOnFileSwitcher.init(context, clientId, Environment.SANDBOX);
      } else {
        cardOnFileSwitcher.init(context, clientId, Environment.PRODUCTION);
      }
    } else {
      cardOnFileSwitcher.init(context, clientId, Environment.PRODUCTION);
    }
    cardOnFileSwitcher.setCustomization(customizationObj);
    cardOnFileSwitcher.setOnSessionEventListener(onSessionEventListener);
    cardOnFileSwitcher.openCardOnFileSwitcher(sessionId, merchantsArr, isCancel);
  }

}
