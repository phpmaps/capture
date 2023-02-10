package com.incode.capture;

import androidx.appcompat.app.AppCompatActivity;
import com.incode.welcome_sdk.IncodeWelcome;
import com.incode.welcome_sdk.OnboardingConfigV2;
import com.incode.welcome_sdk.modules.IdScan;
import com.incode.welcome_sdk.modules.SelfieScan;
import com.incode.welcome_sdk.modules.exceptions.ModuleConfigurationException;
import com.incode.welcome_sdk.results.IdProcessResult;
import com.incode.welcome_sdk.results.IdScanResult;
import com.incode.welcome_sdk.results.IdValidationResult;
import com.incode.welcome_sdk.results.SelfieScanResult;
import com.incode.welcome_sdk.SdkMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btnIdOrPassportCapture)
    Button btnIdOrPassportCapture;

    @BindView(R.id.btnIdCapture)
    Button btnIdCapture;

    @BindView(R.id.btnPassportCapture)
    Button btnPassportCapture;

    @BindView(R.id.btnSelfieCapture)
    Button btnSelfieCapture;

    @BindView(R.id.btnIdAndSelfieCapture)
    Button btnIdAndSelfieCapture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        checkLicense();
        //enableButtons();
    }

    private void checkLicense() {
        IncodeWelcome.getInstance().setSdkMode(com.incode.welcome_sdk.SdkMode.CAPTURE_ONLY);
        IncodeWelcome.getInstance().verifyApiKey(new IncodeWelcome.VerifyListener() { // license needs to be verified only once per application startup.
            // You can omit this call and license will be verified first time when you call capture SDK API.
            @Override
            public void onVerified() {
                enableButtons();
            }

            @Override
            public void onError() {
                //enableButtons();
                Timber.e("License verification error occurred. Please make sure you have working internet connection and valid API key.");
            }
        });
    }

    private void enableButtons() {
        btnIdOrPassportCapture.setEnabled(true);
        btnIdCapture.setEnabled(true);
        btnPassportCapture.setEnabled(true);
        btnSelfieCapture.setEnabled(true);
        btnIdAndSelfieCapture.setEnabled(true);
    }

//    @OnClick({R.id.btnIdOrPassportCapture})
//    public void onBtnIdOrPassportCaptureClicked() {
//        IdScan idScan = new IdScan.Builder()
//                .setShowIdTutorials(true)
//                .build();
//        startIdScan(idScan);
//    }

    @OnClick({R.id.btnIdCapture})
    public void onBtnIdCaptureClicked() {
        IdScan idScan = new IdScan.Builder()
                .setIdType(IdScan.IdType.ID)
                .setShowIdTutorials(false)
                .build();
        startIdScan(idScan);
    }

//    @OnClick({R.id.btnPassportCapture})
//    public void onBtnPassportCaptureClicked() {
//        IdScan idScan = new IdScan.Builder()
//                .setIdType(IdScan.IdType.PASSPORT)
//                .setShowIdTutorials(false)
//                .build();
//        startIdScan(idScan);
//    }

    private void startIdScan(IdScan idScan) {
        IncodeWelcome.getInstance().setSdkMode(com.incode.welcome_sdk.SdkMode.CAPTURE_ONLY);
        try {
            OnboardingConfigV2 configV2 = new OnboardingConfigV2.OnboardingConfigBuilderV2()
                    .setFlowTag("ID scan section")
                    .addID(idScan)
                    .build();

            IncodeWelcome.getInstance().startOnboardingSection(this, null, configV2, new IncodeWelcome.OnboardingListenerV2() {

                        @Override
                        public void onIdFrontCompleted(IdScanResult frontIdScanResult) {
                            Timber.d("onIdFrontCompleted called, IdScanResult: %s", frontIdScanResult);
                        }

                        @Override
                        public void onIdBackCompleted(IdScanResult backIdScanResult) {
                            Timber.d("onIdBackCompleted called, IdScanResult: %s", backIdScanResult);
                        }

                @Override
                public void onIdProcessed(IdProcessResult idProcessResult) {
                    super.onIdProcessed(idProcessResult);
                }

                @Override
                        public void onError(Throwable error) {
                            Timber.e(error, "startIdValidation onError called");
                        }

                        @Override
                        public void onUserCancelled() {
                            Timber.d("startIdValidation onUserCancelled called");
                        }

                        @Override
                        public void onOnboardingSectionCompleted(String flowTag) {
                            Timber.d("onOnboardingSectionCompleted called: %s", flowTag);
                        }
                    }
            );
        } catch (ModuleConfigurationException e) {
            Timber.e(e);
        }
    }

    @OnClick({R.id.btnSelfieCapture})
    public void onBtnSelfieCaptureClicked() {
        IncodeWelcome.getInstance().setSdkMode(com.incode.welcome_sdk.SdkMode.CAPTURE_ONLY);
        SelfieScan selfieScan = new SelfieScan.Builder()
                .setShowTutorials(false)
                .build();
        try {
            OnboardingConfigV2 configV2 = new OnboardingConfigV2.OnboardingConfigBuilderV2()
                    .setFlowTag("Selfie scan section")

                    .addSelfieScan(selfieScan)
                    .build();

            IncodeWelcome.getInstance().startOnboardingSection(this, null, configV2, new IncodeWelcome.OnboardingListenerV2() {
                        @Override
                        public void onSelfieScanCompleted(SelfieScanResult selfieScanResult) {
                            Timber.d("onSelfieScanCompleted called, idValidationResult: %s", selfieScanResult);
                        }

                        @Override
                        public void onError(Throwable error) {
                            Timber.e(error, "startIdValidation onError called");
                        }

                        @Override
                        public void onUserCancelled() {
                            Timber.d("startIdValidation onUserCancelled called");
                        }

                        @Override
                        public void onOnboardingSectionCompleted(String flowTag) {
                            Timber.d("onOnboardingSectionCompleted called: %s", flowTag);
                        }
                    }
            );
        } catch (ModuleConfigurationException e) {
            Timber.e(e);
        }
    }

//    @OnClick({R.id.btnIdAndSelfieCapture})
//    public void onBtnIdAndSelfieCaptureClicked() {
//        IncodeWelcome.getInstance().setSdkMode(com.incode.welcome_sdk.SdkMode.CAPTURE_ONLY);
//        OnboardingConfigV2.OnboardingConfigBuilderV2 builder = new OnboardingConfigV2.OnboardingConfigBuilderV2();
//        IdScan idScan = new IdScan.Builder()
//                .setShowIdTutorials(true)
//                .build();
//        builder.addID(idScan);
//        SelfieScan selfieScan = new SelfieScan.Builder()
//                .setShowTutorials(true)
//                .build();
//        builder.addSelfieScan(selfieScan);
//        try {
//            OnboardingConfigV2 configV2 = new OnboardingConfigV2.OnboardingConfigBuilderV2()
//                    .setFlowTag("ID and Selfie scan section")
//                    .addID(idScan) // new API
//                    .addSelfieScan(selfieScan)
//                    .build();
//
//            IncodeWelcome.getInstance().startOnboardingSection(this, null, configV2, new IncodeWelcome.OnboardingListenerV2() {
//                        @Override
//                        public void onIdValidationCompleted(IdValidationResult idValidationResult) {
//                            Timber.d("onIdValidationCompleted called, idValidationResult: %s", idValidationResult);
//                        }
//
//                        @Override
//                        public void onSelfieScanCompleted(SelfieScanResult selfieScanResult) {
//                            Timber.d("onSelfieScanCompleted called, idValidationResult: %s", selfieScanResult);
//                        }
//
//                        @Override
//                        public void onError(Throwable error) {
//                            Timber.e(error, "startIdValidation onError called");
//                        }
//
//                        @Override
//                        public void onUserCancelled() {
//                            Timber.d("startIdValidation onUserCancelled called");
//                        }
//
//                        @Override
//                        public void onOnboardingSectionCompleted(String flowTag) {
//                            Timber.d("onOnboardingSectionCompleted called: %s", flowTag);
//                        }
//                    }
//            );
//        } catch (ModuleConfigurationException e) {
//            Timber.e(e);
//        }
//    }
}