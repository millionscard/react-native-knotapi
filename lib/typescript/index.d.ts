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
declare type CommonConfig = {
    sessionId: string;
    clientId: string;
    merchantIds?: number[];
    merchantNames?: string[];
    customization?: CustomizationType;
    environment: 'production' | 'sandbox' | 'development';
    useCategories?: boolean;
    logo?: string;
    useSelection?: boolean;
    useSingleFlow?: boolean;
    primaryColor?: string;
    textColor?: string;
    companyName?: string;
};
declare type CardOnFileSwitcherParams = CommonConfig;
declare type SubscriptionCancelerParams = {
    amount?: boolean;
} & CommonConfig;
export declare const openCardOnFileSwitcher: (params: CardOnFileSwitcherParams) => Promise<any>;
export declare const openSubscriptionCanceler: (params: SubscriptionCancelerParams) => Promise<any>;
declare type EventNames = keyof typeof eventNames;
export declare const addSubscriptionCancelerListener: (eventName: EventNames, callback: (event: any) => void) => import("react-native").EmitterSubscription;
export declare const addCardSwitcherListener: (eventName: EventNames, callback: (event: any) => void) => import("react-native").EmitterSubscription;
export default Knotapi;
