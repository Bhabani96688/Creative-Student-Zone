package com.crtv.creativetechnocollege;


import android.R.id;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.creativetechnocollege.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
//import com.google.android.play.core.appupdate.AppUpdateInfo;
//import com.google.android.play.core.appupdate.AppUpdateManager;
//import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
//import com.google.android.play.core.install.InstallStateUpdatedListener;
//import com.google.android.play.core.install.model.AppUpdateType;
//import com.google.android.play.core.install.model.InstallStatus;
//import com.google.android.play.core.install.model.UpdateAvailability;
//import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.firebase.messaging.FirebaseMessaging;



public class MainActivity extends AppCompatActivity {
    public static int UPDATE_CODE = 22;
//    AppUpdateManager appUpdateManager; //Update manager variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseMessaging.getInstance().subscribeToTopic("topicName").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });


        // generate unique token for user.
        // this token can be used to send notifications for single or multiple users
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {

                // check if token has generated successfully.
                if (task.isSuccessful()) {

                    // you can send this token to user database
                    final String token = task.getResult();

                    Log.e("firebaseToken", token);

                }
            }
        });


//        inAppUp();

        openFragment();
    }


    private void openFragment() {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, new Login_page()).commit();

    }
}

//    private void inAppUp() {
//        appUpdateManager = AppUpdateManagerFactory.create(this);
//        com.google.android.play.core.tasks.Task<AppUpdateInfo> task = appUpdateManager.getAppUpdateInfo();
//        task.addOnSuccessListener((OnSuccessListener<? super AppUpdateInfo>) appUpdateInfo -> {
//            if(appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
//            && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE))
//            {
//                try {
//                    appUpdateManager.startUpdateFlowForResult(appUpdateInfo,AppUpdateType.IMMEDIATE,
//                            MainActivity.this,UPDATE_CODE);
//                } catch (IntentSender.SendIntentException e) {
//                    throw new RuntimeException(e);
//                    //Log.d("Updateerror","onSuccess: " + e.toString());
//                }
//            }
//        });
//        appUpdateManager.registerListener(listener);
//    }
//
//    InstallStateUpdatedListener listener = installState -> {
//        if(installState.installStatus() == InstallStatus.DOWNLOADED)
//        {
//            popUp();
//        }
//    };

//    private void popUp() {
//
//        Snackbar snackbar = Snackbar.make(
//                findViewById(id.content),
//                "App Update Almoste Done.",
//                Snackbar.LENGTH_INDEFINITE
//        );

        //Snackbar Action
//        snackbar.setAction("Reload", new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                appUpdateManager.completeUpdate();
//            }
//        });
//        snackbar.setTextColor(Color.parseColor("#FF000"));
//        snackbar.show();
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if(requestCode == UPDATE_CODE){
//            if(resultCode != RESULT_OK)
//            {
//                //Tost message
//            }
//        }
//    }
//}
