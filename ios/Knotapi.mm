// Knotapi.m

#import "Knotapi.h"
#import <React/RCTConvert.h>
#import "KnotAPI/KnotAPI-Swift.h"
#import <WebKit/WebKit.h>

@interface Knotapi ()
@property (nonatomic, strong) UIViewController* presentingViewController;
@property (nonatomic, strong) CardOnFileSwitcherSession *cardOnFileSwitcherSession;
@property (nonatomic, strong) SubscriptionManagerSession *subscriptionManagerSession;
@end

@implementation Knotapi

RCT_EXPORT_MODULE()

RCT_EXPORT_METHOD(openCardSwitcher:(NSDictionary *)params){
  dispatch_async(dispatch_get_main_queue(), ^{
      NSString *companyName = nil;
      NSString *textColor = nil;
      NSString *primaryColor = nil;
      NSNumber *buttonCorners = @(0.0);
      NSNumber *buttonFontSize = @(0.0);
      NSNumber *buttonPaddings = @(0.0);
      NSString *sessionId = [params objectForKey:@"sessionId"];
      NSString *clientId = [params objectForKey:@"clientId"];
      NSArray<NSNumber*> *merchantIds = [params objectForKey:@"merchantIds"];
      NSArray<NSString*> *merchantNames = [params objectForKey:@"merchantNames"];
      NSString *environmentString = [params objectForKey:@"environment"];
      NSString *entryPoint = [params objectForKey:@"entryPoint"];
      BOOL useCategories = [[params objectForKey:@"useCategories"] boolValue];
      BOOL useSearch = [[params objectForKey:@"useSearch"] boolValue];
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

      if ([params objectForKey:@"buttonCorners"]) {
          buttonCorners = [params objectForKey:@"buttonCorners"];
      }

      if ([params objectForKey:@"buttonFontSize"]) {
          buttonFontSize = [params objectForKey:@"buttonFontSize"];
      }

      if ([params objectForKey:@"buttonPaddings"]) {
          buttonPaddings = [params objectForKey:@"buttonPaddings"];
      }

      Environment environment = EnvironmentProduction;
      if ([environmentString isEqualToString:@"sandbox"]) {
          environment = EnvironmentSandbox;
      }
      if ([environmentString isEqualToString:@"development"]) {
          environment = EnvironmentDevelopment;
      }
      if (!self.cardOnFileSwitcherSession) {
                self.cardOnFileSwitcherSession = [[CardOnFileSwitcherSession alloc] initWithSessionId:sessionId clientId:clientId environment:environment];
            }
      CardSwitcherConfiguration *config = [[CardSwitcherConfiguration alloc] init];
      [config setOnSuccessOnSuccess:^(NSString *merchant) {
          [self sendEventWithName:@"CardSwitcher-onSuccess" body:@{@"merchant": merchant}];
      }];
      [config setOnEventOnEvent:^(NSString * event, NSString * message, NSString * _Nullable taskId) {
          NSMutableDictionary *body = [@{@"event": event, @"merchant": message} mutableCopy];
           if (taskId != nil) {
               body[@"taskId"] = taskId;
           }
          [self sendEventWithName:@"CardSwitcher-onEvent" body:body];
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
      [self.cardOnFileSwitcherSession setConfigurationWithConfig:config];
      [self.cardOnFileSwitcherSession setCompanyNameWithCompanyName:companyName];

      [self.cardOnFileSwitcherSession setButtonCornersWithButtonCorners:buttonCorners];
      [self.cardOnFileSwitcherSession setButtonFontSizeWithButtonFontSize:buttonFontSize];
      [self.cardOnFileSwitcherSession setButtonPaddingsWithButtonPaddings:buttonPaddings];


      [self.cardOnFileSwitcherSession setTextColorWithTextColor:textColor];
      [self.cardOnFileSwitcherSession setPrimaryColorWithPrimaryColor:primaryColor];
      [self.cardOnFileSwitcherSession setMerchantIdsWithMerchantIds:merchantIds];
      [self.cardOnFileSwitcherSession setMerchantNamesWithMerchantNames:merchantNames];
      [self.cardOnFileSwitcherSession setUseSelectionWithUseSelection: useSelection];
      [self.cardOnFileSwitcherSession setUseCategoriesWithUseCategories: useCategories];
      [self.cardOnFileSwitcherSession setUseSearchWithUseSearch: useSearch];
      [self.cardOnFileSwitcherSession setUseSingleFlowWithUseSingleFlow: useSingleFlow];
      [self.cardOnFileSwitcherSession setLogoWithLogo: logo];
      [self.cardOnFileSwitcherSession openCardOnFileSwitcherWithEntryPoint:entryPoint];
  });
}

RCT_EXPORT_METHOD(updateCardSwitcherSessionId:(NSString *)sessionId){
    [self.cardOnFileSwitcherSession updateSessionWithSessionId:sessionId];
}

RCT_EXPORT_METHOD(openSubscriptionManager:(NSDictionary *)params){
  dispatch_async(dispatch_get_main_queue(), ^{
      NSString *companyName = nil;
      NSString *textColor = nil;
      NSString *primaryColor = nil;
      NSNumber *buttonCorners = @(0.0);
      NSNumber *buttonFontSize = @(0.0);
      NSNumber *buttonPaddings = @(0.0);
      NSString *sessionId = [params objectForKey:@"sessionId"];
      NSString *clientId = [params objectForKey:@"clientId"];
      NSArray<NSNumber*> *merchantIds = [params objectForKey:@"merchantIds"];
      NSArray<NSString*> *merchantNames = [params objectForKey:@"merchantNames"];
      NSString *environmentString = [params objectForKey:@"environment"];
      NSString *entryPoint = [params objectForKey:@"entryPoint"];
      BOOL useCategories = [[params objectForKey:@"useCategories"] boolValue];
      BOOL useSearch = [[params objectForKey:@"useSearch"] boolValue];
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

      if ([params objectForKey:@"buttonCorners"]) {
          buttonCorners = [params objectForKey:@"buttonCorners"];
      }

      if ([params objectForKey:@"buttonFontSize"]) {
          buttonFontSize = [params objectForKey:@"buttonFontSize"];
      }

      if ([params objectForKey:@"buttonPaddings"]) {
          buttonPaddings = [params objectForKey:@"buttonPaddings"];
      }

      Environment environment = EnvironmentProduction;
      if ([environmentString isEqualToString:@"sandbox"]) {
          environment = EnvironmentSandbox;
      }
      if ([environmentString isEqualToString:@"development"]) {
          environment = EnvironmentDevelopment;
      }
      if (!self.subscriptionManagerSession) {
                self.subscriptionManagerSession = [[SubscriptionManagerSession alloc] initWithSessionId:sessionId clientId:clientId environment:environment];
            }
      SubscriptionManagerConfiguration *config = [[SubscriptionManagerConfiguration alloc] init];
      [config setOnSuccessOnSuccess:^(NSString *merchant) {
          [self sendEventWithName:@"SubscriptionManager-onSuccess" body:@{@"merchant": merchant}];
      }];
      [config setOnEventOnEvent:^(NSString * event, NSString * message, NSString * _Nullable taskId) {
          NSMutableDictionary *body = [@{@"event": event, @"merchant": message} mutableCopy];
           if (taskId != nil) {
               body[@"taskId"] = taskId;
           }
          [self sendEventWithName:@"SubscriptionManager-onEvent" body:body];
      }];
      [config setOnErrorOnError:^(NSString * error, NSString * message) {
          [self sendEventWithName:@"SubscriptionManager-onError" body:@{@"errorCode": error, @"errorMessage": message }];
      }];
      [config setOnExitOnExit:^{
          [self sendEventWithName:@"SubscriptionManager-onExit" body:nil];
      }];
      [config setOnFinishedOnFinished:^{
          [self sendEventWithName:@"SubscriptionManager-onFinished" body:nil];
      }];
      [self.subscriptionManagerSession setConfigurationWithConfig:config];
      [self.subscriptionManagerSession setCompanyNameWithCompanyName:companyName];

      [self.subscriptionManagerSession setButtonCornersWithButtonCorners:buttonCorners];
      [self.subscriptionManagerSession setButtonFontSizeWithButtonFontSize:buttonFontSize];
      [self.subscriptionManagerSession setButtonPaddingsWithButtonPaddings:buttonPaddings];


      [self.subscriptionManagerSession setTextColorWithTextColor:textColor];
      [self.subscriptionManagerSession setPrimaryColorWithPrimaryColor:primaryColor];
      [self.subscriptionManagerSession setMerchantIdsWithMerchantIds:merchantIds];
      [self.subscriptionManagerSession setMerchantNamesWithMerchantNames:merchantNames];
      [self.subscriptionManagerSession setUseSelectionWithUseSelection: useSelection];
      [self.subscriptionManagerSession setUseCategoriesWithUseCategories: useCategories];
      [self.subscriptionManagerSession setUseSearchWithUseSearch: useSearch];
      [self.subscriptionManagerSession setUseSingleFlowWithUseSingleFlow: useSingleFlow];
      [self.subscriptionManagerSession setLogoWithLogo: logo];
      [self.subscriptionManagerSession openSubscriptionManagerWithEntryPoint:entryPoint];
  });
}

RCT_EXPORT_METHOD(updateSubscriptionManagerSessionId:(NSString *)sessionId){
    [self.subscriptionManagerSession updateSessionWithSessionId:sessionId];
}

- (NSArray<NSString *> *)supportedEvents
{
  return @[@"CardSwitcher-onSuccess", @"CardSwitcher-onError", @"CardSwitcher-onEvent", @"CardSwitcher-onExit", @"CardSwitcher-onFinished", @"SubscriptionManager-onSuccess", @"SubscriptionManager-onError", @"SubscriptionManager-onEvent", @"SubscriptionManager-onExit", @"SubscriptionManager-onFinished"];
}

@end
