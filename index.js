// main index.js

import { NativeModules } from 'react-native';

const { Knotapi } = NativeModules;

const open = async (sessionId) => {
    return Knotapi?.openCardSwitcher(sessionId);
}

export {open}
export default Knotapi;
