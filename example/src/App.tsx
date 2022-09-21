import * as React from 'react';

import {Pressable, StyleSheet, View, Text} from 'react-native';
import {openCardOnFileSwitcher} from 'react-native-knotapi';

export default function App() {

    const handleOpenCardSwitcher = () => {
        void openCardOnFileSwitcher({
            sessionId: "e64b25c2-d307-41bf-abc1-d3dd1803e2f2",
            environment: "production",
            merchants: [1, 2, 3],
            customization: {
                companyName: "Millions",
                primaryColor: "#5b138c",
                textColor: "#7b5a91"
            }
        })
    }

    return (
        <View style={styles.container}>
            <Pressable onPress={handleOpenCardSwitcher} style={styles.button}>
                <Text style={styles.textButton}>Open Card on file switcher</Text>
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
    }
});
