# KnotAPI React Native SDK
### Reference for integrating with the KnotAPI React Native SDK
## Overview
KnotAPI for React Native is an embeddable framework that is bundled and distributed with your application used to create an easy and accessible experience for your customers to update their old saved cards across the web to your new card.

## Install React Native SDK

In your react-native project directory:
```sh
npm install react-native-knotapi --save
```

### Automatic installation

From React Native 0.60 and higher, <a href="https://github.com/react-native-community/cli/blob/main/docs/autolinking.md">linking is automatic</a>.

### Manual installation

You need to run:

```sh
react-native link react-native-knotapi
```

## Import SDK

To open the Card on File Switcher first you must import the SDK

### Import knotapi

```js
import { openCardOnFileSwitcher, addListener, eventNames } from "react-native-knotapi";
```

### Create a user
Next, create a [user](apis/users) and obtain the `knot_access_token`. We recommend, saving this `knot_access_token` for future debugging, logging, and connecting.

### Create a session
Then, create a [session](apis/sessions) and obtain the session ID. We recommend, saving this session ID for future debugging, logging, and connecting.

### Open Card On File Switcher
To open the Card on File Switcher, you can use the following:

```js
openCardOnFileSwitcher({sessionId: SESSION_ID, clientId: "CLIENT_ID", customization: {companyName: "COMPANY_NAME"}})
```
## onSuccess

The closure is called when a user successfully update payment info in one account. It should take a single argument, containing the Merchant name String .

```js
let listener = addListener(eventNames.onSuccess, merchant => {
  console.log(merchant);
});
```
## onError

This closure is called when an error occurs during Account updater initialization or one of the account has an issue when updating payment info. It should take a two String arguments errorCode and errorMessage.

```js
let listener = addListener(eventNames.onError, (errorCode, errorMessage) => {
  console.log(`Error ${errorCode}: ${errorMessage}`);
});
```

## onExit

This optional closure is called when a user exits Account updater without successfully updating all selected merchants, or when an unhandled error occurs during Account updater initialization or one of the account has an issue when updating payment info.

```js
let listener = addListener(eventNames.onExit, () => {
console.log("onExit");
});
```
## onEvent

This closure is called when certain events in the Account updater flow have occurred, for example, when the user start updating payment info of an account. This enables your application to gain further insight into what is going on as the user goes through the Account Updater flow. It should take a two String arguments eventName and merchantName.

```js
let listener = addListener(eventNames.onEvent, event => {
  console.log({event});
});
```
##### Events

###### login started
When the Account Updater starts login in an account.

###### login success
When the Account Updater successfully login in an account.

###### require otp
When the Account Updater requires the user to enter an OTP.

###### card error
When the Account Updater encounters an error related the card info when updating the card.
