// Knotapi.h

#if __has_include(<React/RCTBridgeModule.h>)
#import <React/RCTBridgeModule.h>
#import <React/RCTEventEmitter.h>
#else
#import "RCTBridgeModule.h"
#import "RCTEventEmitter.h"
#endif
#import "CardOnFileSwitcher/CardOnFileSwitcher-Swift.h"
@interface Knotapi : RCTEventEmitter <RCTBridgeModule, CardOnFileDelegate>

@end
