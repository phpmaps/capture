package com.incode.capture;


import android.content.Context;
import android.preference.PreferenceManager;

import androidx.multidex.MultiDexApplication;

//import com.crashlitics.android.Crashlytics;
import com.incode.welcome_sdk.IncodeWelcome;
import com.incode.welcome_sdk.SdkMode;

public class IncodeWelcomeApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        if (!BuildConfig.DEBUG) {
        }

        new IncodeWelcome.Builder(this, Constants.WELCOME_API_URL, Constants.WELCOME_API_KEY).build();

    }

    public static void clearApplicantData(Context applicationContext) {
        PreferenceManager.getDefaultSharedPreferences(applicationContext)
                .edit()
                .remove(Constants.Prefs.APPLICATION_EXISTS)
                .remove(Constants.Prefs.APPLICANT_NAME)
                .apply();
    }
}
