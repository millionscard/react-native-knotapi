import * as React from 'react';

import {Pressable, StyleSheet, View, Text} from 'react-native';
import {openCardOnFileSwitcher, openSubscriptionCanceler} from 'react-native-knotapi';

export default function App() {

    const handleOpenCardSwitcher = () => {
        void openCardOnFileSwitcher({
            sessionId: "9c361eed-f86c-4db7-8adb-92caca93f8d9",
            clientId: "ab86955e-22f4-49c3-97d7-369973f4cb9e",
            environment: "sandbox",
            merchants: [1, 2, 3],
            customization: {
                companyName: "Millions",
                primaryColor: "#5b138c",
                textColor: "#e0e0e0"
            }
        })
    }
const handleOpenSubscriptionCanceler = () => {
        void openSubscriptionCanceler({
            sessionId: "9c361eed-f86c-4db7-8adb-92caca93f8d9",
            customization: {
                companyName: "Millions",
                primaryColor: "#5b138c",
                textColor: "#e0e0e0"
            }
        })
    }

    return (
        <View style={styles.container}>
            <Pressable onPress={handleOpenCardSwitcher} style={styles.button}>
                <Text style={styles.textButton}>Open Card on file switcher</Text>
            </Pressable>
            <Pressable onPress={handleOpenSubscriptionCanceler} style={styles.button}>
                <Text style={styles.textButton}>Open Subscription canceler</Text>
            </Pressable>
        </View>
    );
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        alignItems: 'center',
        justifyContent: 'center',
    },
    box: {
        width: 60,
        height: 60,
        marginVertical: 20,
    },
    textButton: {
        color: 'white',
        fontSize: 16,
        fontWeight: 'bold',
    },
    button: {
        backgroundColor: 'black',
        padding: 16,
        marginVertical: 8,
    }
});
