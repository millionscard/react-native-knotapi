declare const Knotapi: any;
export declare const eventNames: {
    readonly onSuccess: "onSuccess";
    readonly onError: "onError";
    readonly onEvent: "onEvent";
    readonly onExit: "onExit";
    readonly onFinished: "onFinished";
};
declare type CustomizationType = {
    primaryColor?: string;
    textColor?: string;
    companyName?: string;
};
declare type CardOnFileSwitcherParams = {
    sessionId: string;
    clientId: string;
    merchantIds?: number[];
    merchantNames?: string[];
    customization: CustomizationType;
    environment: 'production' | 'sandbox';
};
declare type SubscriptionCancelerParams = {
    sessionId: string;
    clientId: string;
    merchantIds?: number[];
    merchantNames?: string[];
    amount?: boolean;
    customization: CustomizationType;
    environment: 'production' | 'sandbox';
};
export declare const openCardOnFileSwitcher: (params: CardOnFileSwitcherParams) => Promise<any>;
export declare const openSubscriptionCanceler: (params: SubscriptionCancelerParams) => Promise<any>;
declare type EventNames = keyof typeof eventNames;
export declare const addSubscriptionCancelerListener: (eventName: EventNames, callback: (event: any) => void) => import("react-native").EmitterSubscription;
export declare const addCardSwitcherListener: (eventName: EventNames, callback: (event: any) => void) => import("react-native").EmitterSubscription;
export default Knotapi;
