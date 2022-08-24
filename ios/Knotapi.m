// Knotapi.m

#import "Knotapi.h"
#import <React/RCTConvert.h>
@import CardOnFileSwitcher;

@interface Knotapi ()
@property (nonatomic, strong) UIViewController* presentingViewController;
@end

@implementation Knotapi

RCT_EXPORT_MODULE()

RCT_EXPORT_METHOD(openCardSwitcher:(NSString *)sessionId)
{
    dispatch_async(dispatch_get_main_queue(), ^{
        self.presentingViewController = RCTPresentedViewController();
        CardOnFileSwitcherViewController *vc = [[CardOnFileSwitcherViewController alloc] initWithSessionId:sessionId];
        [vc setOnSuccessOnSuccess:^(NSString * merchant) {
            [self sendEventWithName:@"onSuccess" body:@{@"merchant": merchant}];
        }];
        [vc setOnErrorOnError:^(NSString *error, NSString *message) {
            [self sendEventWithName:@"onError" body:@{@"errorCode": error, @"errorMessage": message }];
        }];
        [vc setOnEventOnEvent:^(NSString *event, NSString *merchant) {
            [self sendEventWithName:@"onEvent" body:@{@"event": event, @"merchant": merchant}];
        }];
        [vc setOnExitOnExit:^{
            [self sendEventWithName:@"onExit" body:nil];
        }];
        [vc setOnFinishedOnFinished:^{
            [self sendEventWithName:@"onFinished" body:nil];
        }];
        [self.presentingViewController presentViewController:vc animated:NO completion:nil];

    });
}

@end
