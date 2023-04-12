package dev.kevprom.ticketevento;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /*startActivity(new Intent(SplashScreen.this, MainActivity.class));*/
                startActivity(new Intent(SplashScreen.this, Acceso.class));
                finish();
            }
        }, 2000);
    }

}
