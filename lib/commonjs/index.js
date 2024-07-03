"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.updateSubscriptionManagerSessionId = exports.updateCardSwitcherSessionId = exports.openSubscriptionManager = exports.openCardOnFileSwitcher = exports.eventNames = exports.default = exports.addSubscriptionManagerListener = exports.addCardSwitcherListener = void 0;

var _reactNative = require("react-native");
const LINKING_ERROR = `The package 'react-native-knotapi' doesn't seem to be linked. Make sure: \n\n` + _reactNative.Platform.select({
  ios: "- You have run 'pod install'\n",
  default: ''
}) + '- You rebuilt the app after installing the package\n' + '- You are not using Expo managed workflow\n';
const Knotapi = _reactNative.NativeModules.Knotapi ? _reactNative.NativeModules.Knotapi : new Proxy({}, {
  get() {
    throw new Error(LINKING_ERROR);
  }
});
const eventNames = exports.eventNames = {
  onSuccess: 'onSuccess',
  onError: 'onError',
  onEvent: 'onEvent',
  onExit: 'onExit',
  onFinished: 'onFinished'
};
const eventEmitter = new _reactNative.NativeEventEmitter(Knotapi);
const openCardOnFileSwitcher = params => {
  _reactNative.InteractionManager.runAfterInteractions(() => {
    setTimeout(() => {
      Knotapi === null || Knotapi === void 0 ? void 0 : Knotapi.openCardSwitcher(params);
    }, 50);
  });
};
exports.openCardOnFileSwitcher = openCardOnFileSwitcher;

const openSubscriptionManager = params => {
  _reactNative.InteractionManager.runAfterInteractions(() => {
    setTimeout(() => {
      Knotapi === null || Knotapi === void 0 ? void 0 : Knotapi.openSubscriptionManager(params);
    }, 50);
  });
};

exports.openSubscriptionManager = openSubscriptionManager;

const addSubscriptionManagerListener = (eventName, callback) => {
  return eventEmitter.addListener(`SubscriptionManager-${eventName}`, callback);
};

exports.addSubscriptionManagerListener = addSubscriptionManagerListener;


const addCardSwitcherListener = (eventName, callback) => {
  return eventEmitter.addListener(`CardSwitcher-${eventName}`, callback);
};
exports.addCardSwitcherListener = addCardSwitcherListener;
const updateCardSwitcherSessionId = sessionId => {
  Knotapi === null || Knotapi === void 0 ? void 0 : Knotapi.updateCardSwitcherSessionId(sessionId);
};
exports.updateCardSwitcherSessionId = updateCardSwitcherSessionId;
var _default = exports.default = Knotapi;

const updateSubscriptionManagerSessionId = sessionId => {
  Knotapi === null || Knotapi === void 0 ? void 0 : Knotapi.updateSubscriptionManagerSessionId(sessionId);
};
exports.updateSubscriptionManagerSessionId = updateSubscriptionManagerSessionId;
var _default = exports.default = Knotapi;
//# sourceMappingURL=index.js.map