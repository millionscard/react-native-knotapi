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
            NSLog(@"this is error: %@", merchant);
        }];
        [vc setOnErrorOnError:^(NSString *error, NSString *message) {
            NSLog(@"this is error: %@", error);
            NSLog(@"this is message: %@", message);
        }];
        [vc setOnEventOnEvent:^(NSString *event, NSString *message) {
            NSLog(@"this is event: %@", event);
            NSLog(@"this is message: %@", message);
        }];
        [vc setOnExitOnExit:^{
            NSLog(@"this is exit");
        }];
        [self.presentingViewController presentViewController:vc animated:NO completion:nil];

    });
}

@end
