package TestBased;

public class OnScreenExpectedStringValue {
    public static final String CountryPageTitle = "Where do you live?";
    public static final String CountryPageSGDescription = "You’ll need an NRIC or a FIN to apply for a YouTrip Singapore account.";
    public static final String CountryPageTHDescription = "You must have a Thailand ID to apply for a Thailand YouTrip account (powered by KBank).";

    public static final String GetStartPageGetStartButton = "Get Started";

    public static final String EnterMobileNumberPageTitle = "Login / Sign Up";
    public static final String EnterMobileNumberPageTHTitle = "Enter Mobile Number";

    public static final String OTPPageTitle = "Mobile Verification";
    public static final String OTPPageTHTitle = "Enter Code from SMS";

    public static final String EnterEmailPageTitle = "Enter Your Email Address";

    public static final String LimitedHomePageTitle = "Welcome";
    public static final String LimitedHomePageSGPCRegistrationButton = "Sign Up For Free";
    public static final String LimitedHomePageSGNPCRegistrationButton = "Activate My Card";
    public static final String LimitedHomePageSGKYCSubmittedTitle = "Thank You for Your Application";
    public static final String LimitedHomePageSGKYCRejectedTitle = "Attention";
    public static String constructLimitedHomePageSGKYCRejectedResult(String rejectReason)
    {
        return "Sorry, we were unable to process your application due to the following reasons: \n\n" + rejectReason + "\n\nYou’ll also be required to take photos of your ID again to verify your identity.";
    }
    public static String constructLimitedHomePageSGKYCPartialRejectResult(String rejectReason)
    {
        return "We have noticed errors in your application and have edited it for you. These are the following errors:\n\n" + rejectReason + "\n\nPlease check all the data to make sure they are accurate and submit again.";
    }
    public static final String LimitedHomePageSGPCKYCApprovedTitle = "Your Card is On Its Way";
    public static final String LimitedHomePageSGPCKYCApprovedActiveButton = "My Card Arrived";
    public static final String LimitedHomePageSGNPCKYCApprovedTitle = "Verification Complete";
    public static final String LimitedHomePageSGNPCKYCApprovedActiveButton = "Activate Card";

    public static final String KYCEnterYNumberPageTitle = "Enter Y-Number";
    public static final String KYCSGStepsPageTitle = "Follow These Steps";
    public static final String KYCSGStepsPageStep1 = "Capture the FRONT of your NIRC";
    public static final String KYCSGStepsPageStep2 = "Capture the BACK of your NIRC";
    public static final String KYCSGStepsPageStep3 = "Enter your personal details and address";
    public static final String KYCIdentityVerificationPageTitle = "Verify Your Identity";
    public static final String KYCIdentityVerificationPageSingaporeanButton = "For Singaporean / PR";
    public static final String KYCIdentityVerificationPageForeignerButton = "Foreigner";
    public static final String KYCSubmitDocumentPageTitle = "Submit Documents";
    public static final String KYCBackOfNRICPopUpTitle = "Back of NRIC";
    public static final String KYCBackOfNRICPopUpDescription = "Now capture the back of your NRIC.";
    public static final String KYCBackOfPassPopUpTitle = "Back of Pass / Permit";
    public static final String KYCBackOfPassPopUpDescription = "Now capture the back of Pass / Permit.";
    public static final String KYCProofOfAddressPopUpTitle = "Proof of Address";
    public static final String KYCProofOfAddressPopUpDescription = "Take a photo of one of the following documents:";
    public static final String KYCProofOfAddressPopUpDescriptionPt1 = "•      Phone Bill within 6 months";
    public static final String KYCProofOfAddressPopUpDescriptionPt2 = "•      Utilities Bill within 6 months";
    public static final String KYCProofOfAddressPopUpDescriptionPt3 = "•      Bank Statement within 6 months";
    public static final String KYCProofOfAddressPopUpDescriptionPt4 = "•      Other accepted documents";
    public static final String KYCEnterFullNamePageNRICTitle = "Full Name (as per NRIC)";
    public static final String KYCEnterFullNamePagePassTitle = "Full Name (as per Pass / Permit)";
    public static final String KYCCheckFullNamePopUpTitle = "Check and Confirm";
    public static final String KYCEnterNameOnCardPageTitle = "Preferred Name";
    public static final String KYCEnterPersonalInformationPageTitle = "Personal Information";
    public static final String KYCEnterResidentialAddressPageTitle = "Residential Address";
    public static final String KYCFinalStepPageTitle = "Final Step";
    public static final String KYCMarketingConsentPopUpTitle = "Be the First to Know";

    public static final String KYCEnterThaiIDPageTHTitle = "Enter ID Number";
    public static final String KYCKPlusRegistrationPageTHTitle = "Register with K PLUS";
    public static final String KYCKPlusRegistrationPageTHKPlusRegisterButton = "Register with K PLUS";
    public static final String KYCKPlusRegistrationPageTHNoKPlusButton = "Do not have K PLUS";

    public static final String ActiveCardConfirmEmailPageSGTitle = "Confirm Email Address";
    public static final String ActiveCardCheckEmailPageTitle = "Check Your Email";
    public static final String ActiveCardCreatePINPageSGTitle = "Create a PIN";
    public static final String ActiveCardCreatePINPageSGSummary = "For both unlocking the app and using the card in ATM";
    public static final String ActiveCardConfirmPINPageSGTitle = "Confirm Your PIN";

    public static final String ActiveCardStepsPageTHTitle = "Set App PIN and Card PIN";
    public static final String ActiveCardCreateAPPPINPageTHTitle = "Create an App PIN";
    public static final String ActiveCardConfirmAPPPINPageTHTitle = "Type Again to Confirm";
    public static final String ActiveCardSetCardPINPageTHTitle = "Step 2: Set a Card PIN";

    public static final String UnlockAppPINPageSGTitle = "Unlock YouTrip";
}
