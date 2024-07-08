declare const Knotapi: any;
export declare const eventNames: {
    readonly onSuccess: "onSuccess";
    readonly onError: "onError";
    readonly onEvent: "onEvent";
    readonly onExit: "onExit";
    readonly onFinished: "onFinished";
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
export declare const openCardOnFileSwitcher: (params: CommonConfig) => void;
export declare const openSubscriptionManager: (params: CommonConfig) => void;
declare type EventNames = keyof typeof eventNames;
export declare const addSubscriptionManagerListener: (eventName: EventNames, callback: (event: any) => void) => import("react-native").EmitterSubscription;
export declare const addCardSwitcherListener: (eventName: EventNames, callback: (event: any) => void) => import("react-native").EmitterSubscription;
export declare const updateCardSwitcherSessionId: (sessionId: string) => void;
export default Knotapi;
