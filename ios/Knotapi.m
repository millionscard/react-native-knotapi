// Knotapi.m

#import "Knotapi.h"
#import "CardOnFileSwitcher-Swift.h"


@implementation Knotapi

RCT_EXPORT_MODULE()

RCT_EXPORT_METHOD(createSession:(NSString *)stringArgument numberParameter:(nonnull NSNumber *)numberArgument callback:(RCTResponseSenderBlock)callback)
{
    CardOnFileSwitcherSession * session = [CardOnFileSwitcherSession new];
        [session createSessionWithCompletionHandler:^(NSString *sessionId) {
            NSLog(@"this is session id: %@", sessionId);
            dispatch_async(dispatch_get_main_queue(), ^{
                PasswordChangerViewController *vc = [[PasswordChangerViewController alloc] initWithSessionId:sessionId];
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
                [self presentViewController:vc animated:NO completion:nil];

            });
        }];
}

@end
