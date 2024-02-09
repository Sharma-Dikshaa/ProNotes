
package pro.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    EditText email, pass, repass;
    Button register;
    TextView login_txt;
    ProgressBar progressBar;

    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = findViewById(R.id.email);
        pass = findViewById(R.id.password);
        repass = findViewById(R.id.repass);
        register = findViewById(R.id.register_btn);
        login_txt = findViewById(R.id.login_text_view_btn);
        progressBar = findViewById(R.id.progress_bar);

        firebaseAuth = FirebaseAuth.getInstance();



        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_txt = email.getText().toString().trim();
                String password = pass.getText().toString().trim();
                String userRePassword = repass.getText().toString().trim();

                // checking validation

                if (TextUtils.isEmpty(email_txt)) {
                    Toast.makeText(Register.this, "Please enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(Register.this, "Please enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(userRePassword)) {
                    Toast.makeText(Register.this, "Please enter Re-password", Toast.LENGTH_SHORT).show();
                    return;
                }

                //checking length of password

                if (password.length() < 6) {
                    Toast.makeText(Register.this, "Password is too short", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!(password.equals(userRePassword))) {
                    Toast.makeText(Register.this, "Password does not match", Toast.LENGTH_SHORT).show();
                }

                if (password.equals(userRePassword)) {
                    changeInProgress(true);
                    firebaseAuth.createUserWithEmailAndPassword(email_txt, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            changeInProgress(false);
                            if (task.isSuccessful()) {
                                Toast.makeText(Register.this, "Registration completed, Please login", Toast.LENGTH_SHORT).show();
                                firebaseAuth.signOut();
                                finish();
                                startActivity(new Intent(getApplicationContext(), Login.class));

                            } else {
                                Toast.makeText(Register.this, "Registration failed, error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        login_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });
    }


    void changeInProgress(boolean inProgress) {
        if (inProgress) {
            progressBar.setVisibility(View.VISIBLE);
            register.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            register.setVisibility(View.VISIBLE);
        }
    }
}