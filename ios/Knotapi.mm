// Knotapi.m

#import "Knotapi.h"
#import <React/RCTConvert.h>
#import "KnotAPI/KnotAPI-Swift.h"
#import <WebKit/WebKit.h>

@interface Knotapi ()
@property (nonatomic, strong) UIViewController* presentingViewController;
@property (nonatomic, strong) KnotSession *cardOnFileSwitcherSession;
@end

@implementation Knotapi

RCT_EXPORT_MODULE()

RCT_EXPORT_METHOD(openCardSwitcher:(NSDictionary *)params){
  dispatch_async(dispatch_get_main_queue(), ^{
      NSString *sessionId = [params objectForKey:@"sessionId"];
      NSString *clientId = [params objectForKey:@"clientId"];
      NSArray<NSNumber*> *merchantIds = [params objectForKey:@"merchantIds"];
      NSArray<NSString*> *merchantNames = [params objectForKey:@"merchantNames"];
      NSString *environmentString = [params objectForKey:@"environment"];
      NSString *entryPoint = [params objectForKey:@"entryPoint"];
      BOOL useCategories = [[params objectForKey:@"useCategories"] boolValue];
      BOOL useSearch = [[params objectForKey:@"useSearch"] boolValue];

      Environment environment = EnvironmentProduction;
      if ([environmentString isEqualToString:@"sandbox"]) {
          environment = EnvironmentSandbox;
      }
      if ([environmentString isEqualToString:@"development"]) {
          environment = EnvironmentDevelopment;
      }

      if (!self.cardOnFileSwitcherSession) {
          self.cardOnFileSwitcherSession = [Knot createCardSwitcherSessionWithId:sessionId clientId:clientId environment:environment];
      }

      __weak Knotapi * weakSelf = self;

      self.cardOnFileSwitcherSession.onSuccess = ^(NSString *merchant) {
          Knotapi * strongSelf = weakSelf;
          [strongSelf sendEventWithName:@"CardSwitcher-onSuccess" body:@{@"merchant": merchant}];
      };

      self.cardOnFileSwitcherSession.onEvent = ^(NSString * event, NSString * message, NSString * _Nullable taskId) {
          NSMutableDictionary *body = [@{@"event": event, @"merchant": message} mutableCopy];
           if (taskId != nil) {
               body[@"taskId"] = taskId;
           }
          Knotapi * strongSelf = weakSelf;
          [strongSelf sendEventWithName:@"CardSwitcher-onEvent" body:body];
      };

      self.cardOnFileSwitcherSession.onError = ^(NSString * error, NSString * message) {
          Knotapi * strongSelf = weakSelf;
          [strongSelf sendEventWithName:@"CardSwitcher-onError" body:@{@"errorCode": error, @"errorMessage": message }];
      };

      self.cardOnFileSwitcherSession.onExit = ^{
          [self sendEventWithName:@"CardSwitcher-onExit" body:nil];
      };

      self.cardOnFileSwitcherSession.onFinished = ^{
          [self sendEventWithName:@"CardSwitcher-onFinished" body:nil];
      };

      self.cardOnFileSwitcherSession.merchantIds = merchantIds;
      self.cardOnFileSwitcherSession.merchantNames = merchantNames;
      self.cardOnFileSwitcherSession.useCategories = useCategories;
      self.cardOnFileSwitcherSession.useSearch = useSearch;
      self.cardOnFileSwitcherSession.entryPoint = entryPoint;

      [Knot openWithSession:self.cardOnFileSwitcherSession];
  });
}

RCT_EXPORT_METHOD(updateCardSwitcherSessionId:(NSString *)sessionId){
    self.cardOnFileSwitcherSession.sessionId = sessionId;
}

RCT_EXPORT_METHOD(openSubscriptionCanceler:(NSDictionary *)params){
  dispatch_async(dispatch_get_main_queue(), ^{
      //TODO: invoke native method for subscription manager
  });
}

- (NSArray<NSString *> *)supportedEvents
{
  return @[@"CardSwitcher-onSuccess", @"CardSwitcher-onError", @"CardSwitcher-onEvent", @"CardSwitcher-onExit", @"CardSwitcher-onFinished", @"SubscriptionCanceler-onSuccess", @"SubscriptionCanceler-onError", @"SubscriptionCanceler-onEvent", @"SubscriptionCanceler-onExit", @"SubscriptionCanceler-onFinished"];
}

@end
