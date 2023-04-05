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
import com.knotapi.cardonfileswitcher.SubscriptionCanceler;
import com.knotapi.cardonfileswitcher.models.Options;

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
    @Nullable String companyName = params.hasKey("companyName") ? params.getString("companyName") : null;
    float buttonCorners = params.hasKey("buttonCorners") ? Float.parseFloat(params.getString("buttonCorners")) : Float.parseFloat("0.0");
    float buttonFontSize = params.hasKey("buttonFontSize") ? Float.parseFloat(params.getString("buttonFontSize")) : Float.parseFloat("0.0");
    float buttonPaddings = params.hasKey("buttonPaddings") ? Float.parseFloat(params.getString("buttonPaddings")) : Float.parseFloat("0.0");
    @Nullable String primaryColor = params.hasKey("primaryColor") ? params.getString("primaryColor") : null;
    @Nullable String textColor = params.hasKey("textColor") ? params.getString("textColor") : null;
    boolean useCategories = params.hasKey("useCategories") ? params.getBoolean("useCategories") : false;
    boolean useSelection = params.hasKey("useSelection") ? params.getBoolean("useSelection") : false;
    boolean useSingleFlow = params.hasKey("useSingleFlow") ? params.getBoolean("useSingleFlow") : false;
    boolean amount = params.hasKey("amount") ? params.getBoolean("amount") : false;
    @Nullable String logo = params.hasKey("logo") ? params.getString("logo") : null;
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

    if (params.hasKey("customization")) {
      ReadableMap customization = params.getMap("customization");
      options.setPrimaryColor(customization.hasKey("primaryColor") ? customization.getString("primaryColor") : null);
      options.setTextColor(customization.hasKey("textColor") ? customization.getString("textColor") : null);
      options.setCompanyName(customization.hasKey("companyName") ? customization.getString("companyName") : null);
      options.setButtonCorners(customization.hasKey("buttonCorners") ? Float.parseFloat(customization.getString("buttonCorners")) : Float.parseFloat("0.0"));
      options.setButtonFontSize(customization.hasKey("buttonFontSize") ? Float.parseFloat(customization.getString("buttonFontSize")) : Float.parseFloat("0.0"));
      options.setButtonPaddings(customization.hasKey("buttonPaddings") ? Float.parseFloat(customization.getString("buttonPaddings")) : Float.parseFloat("0.0"));
    } else {
      options.setPrimaryColor(primaryColor);
      options.setTextColor(textColor);
      options.setCompanyName(companyName);
      options.setButtonCorners(buttonCorners);
      options.setButtonFontSize(buttonFontSize);
      options.setButtonPaddings(buttonPaddings);
    }

    options.setMerchantIds(merchantIdsArr);
    options.setMerchantNames(merchantNamesArr);
    options.setUseCategories(useCategories);
    options.setUseSelection(useSelection);
    options.setUseSingleFlow(useSingleFlow);
    options.setLogo(logo);

    return options;
  }

  // Example method
  // See https://reactnative.dev/docs/native-modules-android
  @ReactMethod
  public void openCardSwitcher(ReadableMap params) {
    Configuration configuration = getConfiguration(params);
    Options options = getOptions(params);
    cardOnFileSwitcher = CardOnFileSwitcher.getInstance();
    cardOnFileSwitcher.init(context, configuration, options, getOnSessionEventListener("CardSwitcher-"));
    cardOnFileSwitcher.openCardOnFileSwitcher();
  }

  @ReactMethod
  public void openSubscriptionCanceler(ReadableMap params) {
    Configuration configuration = getConfiguration(params);
    Options options = getOptions(params);
    subscriptionCanceler = subscriptionCanceler.getInstance();
    subscriptionCanceler.init(context, configuration, options, getOnSessionEventListener("SubscriptionCanceler-"));
    subscriptionCanceler.openSubscriptionCanceller();
  }
}
