
#if __has_include(<React/RCTBridgeModule.h>)
#import <React/RCTBridgeModule.h>
#import <React/RCTEventEmitter.h>
#import <WebKit/WebKit.h>
#else
#import "RCTBridgeModule.h"
#import "RCTEventEmitter.h"
#endif
@interface Knotapi : RCTEventEmitter <RCTBridgeModule>

@end

