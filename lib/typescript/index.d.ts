declare const Knotapi: any;
export declare const eventNames: {
    readonly onSuccess: "onSuccess";
    readonly onError: "onError";
    readonly onEvent: "onEvent";
    readonly onExit: "onExit";
};

declare type CommonConfig = {
    sessionId: string;
    clientId: string;
    merchantIds?: number[];
    merchantNames?: string[];
    environment: 'production' | 'sandbox' | 'development';
    useCategories?: boolean;
    useSearch?: boolean;
    entryPoint?: string;
};
declare type CardOnFileSwitcherParams = CommonConfig;
declare type SubscriptionCancelerParams = {
    amount?: boolean;
} & CommonConfig;
export declare const openCardOnFileSwitcher: (params: CardOnFileSwitcherParams) => void;
export declare const openSubscriptionCanceler: (params: SubscriptionCancelerParams) => void;
declare type EventNames = keyof typeof eventNames;
export declare const addSubscriptionCancelerListener: (eventName: EventNames, callback: (event: any) => void) => import("react-native").EmitterSubscription;
export declare const addCardSwitcherListener: (eventName: EventNames, callback: (event: any) => void) => import("react-native").EmitterSubscription;
export declare const updateCardSwitcherSessionId: (sessionId: string) => void;
export default Knotapi;
