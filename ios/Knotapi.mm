// Knotapi.m

#import "Knotapi.h"
#import <React/RCTConvert.h>
#import "KnotAPI/KnotAPI-Swift.h"

@interface Knotapi () <CardOnFileDelegate>
@property (nonatomic, strong) UIViewController* presentingViewController;
@end

@implementation Knotapi

RCT_EXPORT_MODULE()

RCT_EXPORT_METHOD(openCardSwitcher:(NSDictionary *)params){
  dispatch_async(dispatch_get_main_queue(), ^{
      NSString *sessionId = [params objectForKey:@"sessionId"];
      NSArray<NSNumber*> *merchants = [params objectForKey:@"merchants"];
      NSDictionary *customization = [params objectForKey:@"customization"];
      NSString *companyName = [customization objectForKey:@"companyName"];
      CardOnFileSwitcherSession *session = [[CardOnFileSwitcherSession alloc] initWithSessionId:sessionId];
      [session setDelegateWithDelegate:self];
      [session setCompanyNameWithCompanyName:companyName];
      NSLog(@"merchants: %@",merchants);
      NSLog(@"companyName %@",companyName);
      NSLog(@"customization %@",customization);
      [session openOnCardFileSwitcherWithMerchants:merchants];
  });
}

- (NSArray<NSString *> *)supportedEvents
{
  return @[@"onSuccess", @"onError", @"onEvent", @"onExit", @"onFinished"];
}

- (void)onSuccessWithMerchant:(NSString *)merchant{
  [self sendEventWithName:@"onSuccess" body:@{@"merchant": merchant}];
}

- (void)onEventWithEvent:(NSString *)event message:(NSString *)message{
  [self sendEventWithName:@"onEvent" body:@{@"event": event, @"merchant": message}];
}

- (void)onErrorWithError:(NSString *)error message:(NSString *)message{
  [self sendEventWithName:@"onError" body:@{@"errorCode": error, @"errorMessage": message }];
}

- (void)onExit{
  [self sendEventWithName:@"onExit" body:nil];
}

- (void)onFinished{
  [self sendEventWithName:@"onFinished" body:nil];
}

@end
