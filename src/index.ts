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
type CardOnFileSwitcherParams = { sessionId: string, clientId: string, merchantIds?: number[], merchantNames?: string[], customization: CustomizationType, environment: 'production' | 'sandbox' }
type SubscriptionCancelerParams = { sessionId: string, clientId: string, merchantIds?: number[], merchantNames?: string[], amount?: boolean, customization: CustomizationType, environment: 'production' | 'sandbox' }
export const openCardOnFileSwitcher = async (params: CardOnFileSwitcherParams) => {
    return Knotapi?.openCardSwitcher(params);
}

export const openSubscriptionCanceler = async (params: SubscriptionCancelerParams) => {
    return Knotapi?.openSubscriptionCanceler(params);
}

type EventNames = keyof typeof eventNames
export const addSubscriptionCancelerListener = (eventName: EventNames, callback: (event: any) => void) => {
    return eventEmitter.addListener(`SubscriptionCanceler-${eventName}`, callback)
}
export const addCardSwitcherListener = (eventName: EventNames, callback: (event: any) => void) => {
    return eventEmitter.addListener(`CardSwitcher-${eventName}`, callback)
}

export default Knotapi;
