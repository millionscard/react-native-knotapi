import {
  NativeModules,
  Platform,
  NativeEventEmitter,
  InteractionManager,
} from 'react-native';

const LINKING_ERROR =
  `The package 'react-native-knotapi' doesn't seem to be linked. Make sure: \n\n` +
  Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo managed workflow\n';

const Knotapi = NativeModules.Knotapi
  ? NativeModules.Knotapi
  : new Proxy(
      {},
      {
        get() {
          throw new Error(LINKING_ERROR);
        },
      }
    );

const eventEmitter = new NativeEventEmitter(Knotapi);

type CommonConfig = {
  sessionId: string;
  clientId: string;
  merchantIds?: number[];
  domainUrls?: string[];
  environment: 'production' | 'sandbox' | 'development';
  useCategories?: boolean;
  useSearch?: boolean;
  entryPoint?: string;
};

type ErrorCallback = (errorCode: string, message: string) => void;
type EventCallback = (
  event: string,
  merchant: string,
  payload?: Record<string, unknown>,
  taskId?: string
) => void;
type SuccessCallback = (merchant: string) => void;
type ExitCallback = () => void;

export const openCardOnFileSwitcher = (params: CommonConfig) => {
  InteractionManager.runAfterInteractions(() => {
    setTimeout(() => {
      Knotapi?.openCardSwitcher(params);
    }, 50);
  });
};

export const closeKnotSDK = () => {
  Knotapi?.closeKnotSDK();
};

export const openSubscriptionManager = (params: CommonConfig) => {
  InteractionManager.runAfterInteractions(() => {
    setTimeout(() => {
      Knotapi?.openSubscriptionManager(params);
    }, 50);
  });
};

type EventTypes = {
  onSuccess: SuccessCallback;
  onError: ErrorCallback;
  onEvent: EventCallback;
  onExit: ExitCallback;
};

export const addSubscriptionManagerListener = <T extends keyof EventTypes>(
  eventName: T,
  callback: (event: EventTypes[T]) => void
) => {
  return eventEmitter.addListener(`SubscriptionManager-${eventName}`, callback);
};
export const addCardSwitcherListener = <T extends keyof EventTypes>(
  eventName: T,
  callback: (event: EventTypes[T]) => void
) => {
  return eventEmitter.addListener(`CardSwitcher-${eventName}`, callback);
};

export default Knotapi;
