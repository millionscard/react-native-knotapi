// main index.js

import { NativeModules, NativeEventEmitter } from 'react-native';

export const eventNames = {
    onSuccess: 'onSuccess',
    onError: 'onError',
    onEvent: 'onEvent',
    onExit: 'onExit',
    onFinished: 'onFinished'
}
const { Knotapi } = NativeModules;
const eventEmitter = new NativeEventEmitter(Knotapi)

const open = async (sessionId, merchants, isCancel, customization) => {
    return Knotapi?.openCardSwitcher(sessionId, merchants, isCancel, customization);
}

const addListener = (eventName, callback) => {
    eventEmitter.addListener(eventName, callback)
}

export {open, addListener}
export default Knotapi;
