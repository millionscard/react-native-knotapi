import {NativeModules, Platform, NativeEventEmitter} from 'react-native';

const LINKING_ERROR =
    `The package 'react-native-knotapi' doesn't seem to be linked. Make sure: \n\n` +
    Platform.select({ios: "- You have run 'pod install'\n", default: ''}) +
    '- You rebuilt the app after installing the package\n' +
    '- You are not using Expo managed workflow\n';

const Knotapi = NativeModules.Knotapi ? NativeModules.Knotapi : new Proxy(
    {},
    {
        get() {
            throw new Error(LINKING_ERROR);
        },
    }
);

export const eventNames = {
    onSuccess: 'onSuccess',
    onError: 'onError',
    onEvent: 'onEvent',
    onExit: 'onExit',
    onFinished: 'onFinished'
} as const;

const eventEmitter = new NativeEventEmitter(Knotapi)

type CustomizationType = {
    primaryColor?: string
    textColor?: string
    companyName?: string
}
type CardOnFileSwitcherParams = { sessionId: string, clientId: string, merchants?: number[], customization: CustomizationType, environment: 'production' | 'sandbox' }
type SubscriptionCancelerParams = { sessionId: string, customization: CustomizationType }
export const openCardOnFileSwitcher = async (params: CardOnFileSwitcherParams) => {
    return Knotapi?.openCardSwitcher(params);
}

export const openSubscriptionCanceler = async (params: SubscriptionCancelerParams) => {
    return Knotapi?.openSubscriptionCanceler(params);
}

type EventNames = keyof typeof eventNames
export const addListener = (eventName: EventNames, callback: () => void) => {
    eventEmitter.addListener(eventName, callback)
}

export default Knotapi;
