import { Pressable, StyleSheet, View, Text } from 'react-native';
import {
  openCardOnFileSwitcher,
  openSubscriptionManager,
  addCardSwitcherListener,
  addSubscriptionManagerListener,
} from '../../src/index';
import { useEffect } from 'react';

export default function App() {
  const handleOpenCardSwitcher = () => {
    openCardOnFileSwitcher({
      sessionId: '5484d1d3-6740-494f-af07-33d520afb0f1',
      clientId: '3f4acb6b-a8c9-47bc-820c-b0eaf24ee771',
      environment: 'sandbox',
    });
  };
  const handleOpenSubscriptionManager = () => {
    openSubscriptionManager({
      sessionId: '1803be4d-cd44-49b5-8f69-21a48ed19a45',
      clientId: 'ab86955e-22f4-49c3-97d7-369973f4cb9e',
      environment: 'sandbox',
    });
  };

  useEffect(() => {
    const emitterSubscription = addCardSwitcherListener(
      'onSuccess',
      (event) => {
        console.log('onSuccess', 'event', event);
      }
    );
    const emitterSwitcher = addSubscriptionManagerListener(
      'onSuccess',
      (event) => {
        console.log('onSuccess subscription', 'event', event);
      }
    );

    const emitterSwitcherEvent = addCardSwitcherListener('onEvent', (event) => {
      console.log('onEvent switcher', 'event', event);
    });
    return () => {
      emitterSubscription.remove();
      emitterSwitcher.remove();
      emitterSwitcherEvent.remove();
    };
  }, []);

  return (
    <View style={styles.container}>
      <Pressable onPress={handleOpenCardSwitcher} style={styles.button}>
        <Text style={styles.textButton}>Open Card on file switcher</Text>
      </Pressable>
      <Pressable onPress={handleOpenSubscriptionManager} style={styles.button}>
        <Text style={styles.textButton}>Open Subscription manager</Text>
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
  },
});
