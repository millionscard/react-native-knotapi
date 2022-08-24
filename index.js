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

const open = async (sessionId) => {
    return Knotapi?.openCardSwitcher(sessionId);
}

const addListener = (eventName, callback) => {
    eventEmitter.addListener(eventName, callback)
}

export {open, addListener}
export default Knotapi;
