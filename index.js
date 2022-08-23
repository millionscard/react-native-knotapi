// main index.js

import { NativeModules } from 'react-native';

const { Knotapi } = NativeModules;

const createSession = async () => {
    return Knotapi?.createSession();
}

export {createSession}
export default Knotapi;
