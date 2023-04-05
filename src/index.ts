import {NativeModules, Platform, NativeEventEmitter, InteractionManager} from 'react-native';

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
    companyName?: string,
    buttonCorners?: string,
    buttonFontSize?: string,
    buttonPaddings?: string,
}
type CommonConfig = {
    sessionId: string,
    clientId: string,
    merchantIds?: number[],
    merchantNames?: string[],
    customization?: CustomizationType,
    environment: 'production' | 'sandbox' | 'development',
    useCategories?: boolean,
    logo?: string,
    useSelection?: boolean,
    useSingleFlow?: boolean,
    primaryColor?: string,
    textColor?: string,
    companyName?: string,
    buttonCorners?: string,
    buttonFontSize?: string,
    buttonPaddings?: string,
}
type CardOnFileSwitcherParams = CommonConfig
type SubscriptionCancelerParams = { amount?: boolean, } & CommonConfig
export const openCardOnFileSwitcher = (params: CardOnFileSwitcherParams) => {
    InteractionManager.runAfterInteractions(() => {
        setTimeout(() => {
            Knotapi?.openCardSwitcher(params);
        }, 50)
    });
}

export const openSubscriptionCanceler = (params: SubscriptionCancelerParams) => {
    InteractionManager.runAfterInteractions(() => {
        setTimeout(() => {
            Knotapi?.openSubscriptionCanceler(params);
        }, 50)
    });
}

type EventNames = keyof typeof eventNames
export const addSubscriptionCancelerListener = (eventName: EventNames, callback: (event: any) => void) => {
    return eventEmitter.addListener(`SubscriptionCanceler-${eventName}`, callback)
}
export const addCardSwitcherListener = (eventName: EventNames, callback: (event: any) => void) => {
    return eventEmitter.addListener(`CardSwitcher-${eventName}`, callback)
}

export default Knotapi;
