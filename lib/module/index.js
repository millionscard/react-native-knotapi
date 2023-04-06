import { NativeModules, Platform, NativeEventEmitter, InteractionManager } from 'react-native';
const LINKING_ERROR = `The package 'react-native-knotapi' doesn't seem to be linked. Make sure: \n\n` + Platform.select({
  ios: "- You have run 'pod install'\n",
  default: ''
}) + '- You rebuilt the app after installing the package\n' + '- You are not using Expo managed workflow\n';
const Knotapi = NativeModules.Knotapi ? NativeModules.Knotapi : new Proxy({}, {
  get() {
    throw new Error(LINKING_ERROR);
  }

});
export const eventNames = {
  onSuccess: 'onSuccess',
  onError: 'onError',
  onEvent: 'onEvent',
  onExit: 'onExit',
  onFinished: 'onFinished'
};
const eventEmitter = new NativeEventEmitter(Knotapi);
export const openCardOnFileSwitcher = params => {
  InteractionManager.runAfterInteractions(() => {
    setTimeout(() => {
      Knotapi === null || Knotapi === void 0 ? void 0 : Knotapi.openCardSwitcher(params);
    }, 50);
  });
};
export const openSubscriptionCanceler = params => {
  InteractionManager.runAfterInteractions(() => {
    setTimeout(() => {
      Knotapi === null || Knotapi === void 0 ? void 0 : Knotapi.openSubscriptionCanceler(params);
    }, 50);
  });
};
export const addSubscriptionCancelerListener = (eventName, callback) => {
  return eventEmitter.addListener(`SubscriptionCanceler-${eventName}`, callback);
};
export const addCardSwitcherListener = (eventName, callback) => {
  return eventEmitter.addListener(`CardSwitcher-${eventName}`, callback);
};
export default Knotapi;
//# sourceMappingURL=index.js.map