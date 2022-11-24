import * as React from 'react';

import {Pressable, StyleSheet, View, Text} from 'react-native';
import {openCardOnFileSwitcher, openSubscriptionCanceler, addCardSwitcherListener, addSubscriptionCancelerListener} from 'react-native-knotapi';
import {useEffect} from "react";

export default function App() {

    const handleOpenCardSwitcher = () => {
        void openCardOnFileSwitcher({
            sessionId: "cc144c8f-d248-43dc-9ac1-b82d4f5b6a97",
            clientId: "3f4acb6b-a8c9-47bc-820c-b0eaf24ee771",
            environment: "sandbox",
            customization: {
                companyName: "Millions",
                primaryColor: "#5b138c",
                textColor: "#e0e0e0"
            }
        })
    }
    const handleOpenSubscriptionCanceler = () => {
        void openSubscriptionCanceler({
            sessionId: "44d5c96d-cd64-468f-a6d9-758fb24902b2",
            clientId: "3f4acb6b-a8c9-47bc-820c-b0eaf24ee771",
            environment: "sandbox",
            amount: true,
            customization: {
                companyName: "Millions",
                primaryColor: "#5b138c",
                textColor: "#e0e0e0"
            },
            merchantIds: [44],
            merchantNames: ["Netflix"]
        })
    }

    useEffect(() => {
        const emitterSubscription = addCardSwitcherListener("onSuccess", (event) => {
            console.log("onSuccess", "event", event)
        });
        const emitterSwitcher = addSubscriptionCancelerListener("onSuccess", (event) => {
            console.log("onSuccess subscription", "event", event)
        });

        return () => {
            emitterSubscription.remove()
            emitterSwitcher.remove()
        };
    }, []);


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
