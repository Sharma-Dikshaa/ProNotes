package pro.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;

public class SplashScreen extends AppCompatActivity {

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        firebaseAuth = FirebaseAuth.getInstance();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if(firebaseAuth.getCurrentUser()==null){
                    startActivity(new Intent(getApplicationContext(),Login.class));
                }else{
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                }
                finish();
            }
        },3000);
    }
}