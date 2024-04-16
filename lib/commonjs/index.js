"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.updateCardSwitcherSessionId = exports.openSubscriptionCanceler = exports.openCardOnFileSwitcher = exports.eventNames = exports.default = exports.addSubscriptionCancelerListener = exports.addCardSwitcherListener = void 0;

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
const eventNames = {
  onSuccess: 'onSuccess',
  onError: 'onError',
  onEvent: 'onEvent',
  onExit: 'onExit',
  onFinished: 'onFinished'
};
exports.eventNames = eventNames;
const eventEmitter = new _reactNative.NativeEventEmitter(Knotapi);

const openCardOnFileSwitcher = params => {
  _reactNative.InteractionManager.runAfterInteractions(() => {
    setTimeout(() => {
      Knotapi === null || Knotapi === void 0 ? void 0 : Knotapi.openCardSwitcher(params);
    }, 50);
  });
};

exports.openCardOnFileSwitcher = openCardOnFileSwitcher;

const openSubscriptionCanceler = params => {
  _reactNative.InteractionManager.runAfterInteractions(() => {
    setTimeout(() => {
      Knotapi === null || Knotapi === void 0 ? void 0 : Knotapi.openSubscriptionCanceler(params);
    }, 50);
  });
};

exports.openSubscriptionCanceler = openSubscriptionCanceler;

const addSubscriptionCancelerListener = (eventName, callback) => {
  return eventEmitter.addListener(`SubscriptionCanceler-${eventName}`, callback);
};

exports.addSubscriptionCancelerListener = addSubscriptionCancelerListener;

const addCardSwitcherListener = (eventName, callback) => {
  return eventEmitter.addListener(`CardSwitcher-${eventName}`, callback);
};

exports.addCardSwitcherListener = addCardSwitcherListener;

const updateCardSwitcherSessionId = sessionId => {
  Knotapi === null || Knotapi === void 0 ? void 0 : Knotapi.updateCardSwitcherSessionId(sessionId);
};

exports.updateCardSwitcherSessionId = updateCardSwitcherSessionId;
var _default = Knotapi;
exports.default = _default;
//# sourceMappingURL=index.js.map