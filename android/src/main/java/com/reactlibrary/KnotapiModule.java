// KnotapiModule.java

package com.reactlibrary;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.content.Intent;
import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.BaseActivityEventListener;

import com.knotapi.cardonfileswitcher.CardOnFileSwitcher;
import com.knotapi.cardonfileswitcher.OnSessionEventListener;
import com.knotapi.cardonfileswitcher.model.CreateSessionParameters;
import com.google.gson.Gson;

public class KnotapiModule extends ReactContextBaseJavaModule {

    public static final String NAME = "Knotapi";
    CardOnFileSwitcher cardOnFileSwitcher;
    Context context;

    private final ActivityEventListener mActivityEventListener = new BaseActivityEventListener() {
        @Override
        public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
            super.onActivityResult(activity, requestCode, resultCode, data);
            Log.d("onActivityResult", requestCode + " " + resultCode);
        }
    };

    private final OnSessionEventListener onSessionEventListener = new OnSessionEventListener() {
        @Override
        public void onUserCreated(String knotAccessToken, String client_id, String secret) {
            Log.d("knotAccessToken", knotAccessToken);
            createSession(knotAccessToken, client_id, secret);
        }

        @Override
        public void onSessionCreated(String sessionId) {
            cardOnFileSwitcher.openCardOnFileSwitcher(sessionId);
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

    public KnotapiModule(ReactApplicationContext reactContext) {
        super(reactContext);
        context = (Context) reactContext;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @ReactMethod
    public void openCardSwitcher(String params) {
        cardOnFileSwitcher = CardOnFileSwitcher.getInstance();
        cardOnFileSwitcher.init(context);
        cardOnFileSwitcher.setOnSessionEventListener(onSessionEventListener);
        cardOnFileSwitcher.createUser(onSessionEventListener, params);
    }

    public void createSession(String knotAccessToken, String client_id, String secret) {
        CreateSessionParameters createSessionParameters = new CreateSessionParameters(client_id, secret, knotAccessToken);
        Gson gson = new Gson();
        String params = gson.toJson(createSessionParameters);
        cardOnFileSwitcher.createSession(onSessionEventListener, params);
    }

}
