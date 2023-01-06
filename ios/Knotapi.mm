// Knotapi.m

#import "Knotapi.h"
#import <React/RCTConvert.h>
#import "KnotAPI/KnotAPI-Swift.h"

@interface Knotapi ()
@property (nonatomic, strong) UIViewController* presentingViewController;
@end

@implementation Knotapi

RCT_EXPORT_MODULE()

RCT_EXPORT_METHOD(openCardSwitcher:(NSDictionary *)params){
  dispatch_async(dispatch_get_main_queue(), ^{
      NSString *companyName = nil;
      NSString *textColor = nil;
      NSString *primaryColor = nil;
      NSString *sessionId = [params objectForKey:@"sessionId"];
      NSString *clientId = [params objectForKey:@"clientId"];
      NSArray<NSNumber*> *merchantIds = [params objectForKey:@"merchantIds"];
      NSArray<NSString*> *merchantNames = [params objectForKey:@"merchantNames"];
      NSString *environmentString = [params objectForKey:@"environment"];
      BOOL useCategories = [[params objectForKey:@"useCategories"] boolValue];
      BOOL useSelection = [[params objectForKey:@"useSelection"] boolValue];
      BOOL useSingleFlow = [[params objectForKey:@"useSingleFlow"] boolValue];
      NSString *logo = [params objectForKey:@"logo"];
      if (params[@"customization"]) {
        NSDictionary *customization = [params objectForKey:@"customization"];
        companyName = [customization objectForKey:@"companyName"];
        textColor = [customization objectForKey:@"textColor"];
        primaryColor = [customization objectForKey:@"primaryColor"];
      } else {
        companyName = [params objectForKey:@"companyName"];
        textColor = [params objectForKey:@"textColor"];
        primaryColor = [params objectForKey:@"primaryColor"];
      }
      Environment environment = EnvironmentProduction;
      if ([environmentString isEqualToString:@"sandbox"]) {
          environment = EnvironmentSandbox;
      }
      if ([environmentString isEqualToString:@"development"]) {
          environment = EnvironmentDevelopment;
      }
      CardOnFileSwitcherSession *session = [[CardOnFileSwitcherSession alloc] initWithSessionId:sessionId clientId:clientId environment:environment];
      CardSwitcherConfiguration *config = [[CardSwitcherConfiguration alloc] init];
      [config setOnSuccessOnSuccess:^(NSString *merchant) {
          [self sendEventWithName:@"CardSwitcher-onSuccess" body:@{@"merchant": merchant}];
      }];
      [config setOnEventOnEvent:^(NSString * event, NSString * message) {
          [self sendEventWithName:@"CardSwitcher-onEvent" body:@{@"event": event, @"merchant": message}];
      }];
      [config setOnErrorOnError:^(NSString * error, NSString * message) {
          [self sendEventWithName:@"CardSwitcher-onError" body:@{@"errorCode": error, @"errorMessage": message }];
      }];
      [config setOnExitOnExit:^{
          [self sendEventWithName:@"CardSwitcher-onExit" body:nil];
      }];
      [config setOnFinishedOnFinished:^{
          [self sendEventWithName:@"CardSwitcher-onFinished" body:nil];
      }];
      [session setConfigurationWithConfig:config];
      [session setCompanyNameWithCompanyName:companyName];
      [session setTextColorWithTextColor:textColor];
      [session setPrimaryColorWithPrimaryColor:primaryColor];
      [session setMerchantIdsWithMerchantIds:merchantIds];
      [session setMerchantNamesWithMerchantNames:merchantNames];
      [session setUseSelectionWithUseSelection: useSelection];
      [session setUseCategoriesWithUseCategories: useCategories];
      [session setUseSingleFlowWithUseSingleFlow: useSingleFlow];
      [session setLogoWithLogo: logo];
      [session openCardOnFileSwitcher];
  });
}

RCT_EXPORT_METHOD(openSubscriptionCanceler:(NSDictionary *)params){
  dispatch_async(dispatch_get_main_queue(), ^{
      NSString *companyName = nil;
      NSString *textColor = nil;
      NSString *primaryColor = nil;
      NSString *sessionId = [params objectForKey:@"sessionId"];
      NSString *clientId = [params objectForKey:@"clientId"];
      NSString *environmentString = [params objectForKey:@"environment"];
      BOOL useCategories = [[params objectForKey:@"useCategories"] boolValue];
      BOOL useSelection = [[params objectForKey:@"useSelection"] boolValue];
      BOOL useSingleFlow = [[params objectForKey:@"useSingleFlow"] boolValue];
      NSString *logo = [params objectForKey:@"logo"];
      if (params[@"customization"]) {
        NSDictionary *customization = [params objectForKey:@"customization"];
        companyName = [customization objectForKey:@"companyName"];
        textColor = [customization objectForKey:@"textColor"];
        primaryColor = [customization objectForKey:@"primaryColor"];
      } else {
        companyName = [params objectForKey:@"companyName"];
        textColor = [params objectForKey:@"textColor"];
        primaryColor = [params objectForKey:@"primaryColor"];
      }
      Environment environment = EnvironmentProduction;
      if ([environmentString isEqualToString:@"sandbox"]) {
          environment = EnvironmentSandbox;
      }
      if ([environmentString isEqualToString:@"development"]) {
          environment = EnvironmentDevelopment;
      }

      bool amount = true;
      amount = [[params objectForKey:@"amount"] boolValue];

      SubscriptionCancelerSession *session = [[SubscriptionCancelerSession alloc] initWithSessionId:sessionId clientId:clientId environment:environment];
      SubscriptionCancelerConfiguration *config = [[SubscriptionCancelerConfiguration alloc] init];
      [config setOnSuccessOnSuccess:^(NSString *merchant) {
          [self sendEventWithName:@"SubscriptionCanceler-onSuccess" body:@{@"merchant": merchant}];
      }];
      [config setOnEventOnEvent:^(NSString * event, NSString * message) {
          [self sendEventWithName:@"SubscriptionCanceler-onEvent" body:@{@"event": event, @"merchant": message}];
      }];
      [config setOnErrorOnError:^(NSString * error, NSString * message) {
          [self sendEventWithName:@"SubscriptionCanceler-onError" body:@{@"errorCode": error, @"errorMessage": message }];
      }];
      [config setOnExitOnExit:^{
          [self sendEventWithName:@"SubscriptionCanceler-onExit" body:nil];
      }];
      [config setOnFinishedOnFinished:^{
          [self sendEventWithName:@"SubscriptionCanceler-onFinished" body:nil];
      }];
      [session setConfigurationWithConfiguration: config];
      [session setCompanyNameWithCompanyName:companyName];
      [session setTextColorWithTextColor:textColor];
      [session setAmountWithAmount:amount];
      [session setPrimaryColorWithPrimaryColor:primaryColor];
      [session setUseSelectionWithUseSelection: useSelection];
      [session setUseCategoriesWithUseCategories: useCategories];
      [session setUseSingleFlowWithUseSingleFlow: useSingleFlow];
      [session setLogoWithLogo: logo];
      [session openSubscriptionCanceler];
  });
}

- (NSArray<NSString *> *)supportedEvents
{
  return @[@"CardSwitcher-onSuccess", @"CardSwitcher-onError", @"CardSwitcher-onEvent", @"CardSwitcher-onExit", @"CardSwitcher-onFinished", @"SubscriptionCanceler-onSuccess", @"SubscriptionCanceler-onError", @"SubscriptionCanceler-onEvent", @"SubscriptionCanceler-onExit", @"SubscriptionCanceler-onFinished"];
}

@end
