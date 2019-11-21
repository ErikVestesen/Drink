package com.example.opskrift.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.opskrift.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements
        View.OnClickListener {

    private FirebaseAuth mAuth;

    TextView errorText;
    EditText emailText, passwordText;
    Button emailSignInButton, emailCreateAccountButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Firebase
        mAuth = FirebaseAuth.getInstance();

        //UI
        emailText = findViewById(R.id.txt_login_email);
        passwordText = findViewById(R.id.txt_login_password);
        emailSignInButton = findViewById(R.id.emailSignInButton);
        emailCreateAccountButton = findViewById(R.id.emailCreateAccountButton);
        errorText = findViewById(R.id.loginErrorText);

        //Events
        emailSignInButton.setOnClickListener(this);
        emailCreateAccountButton.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // User is signed in
            // Name, email address, and profile photo Url
            String name = currentUser.getDisplayName();
            String email = currentUser.getEmail();
            Uri photoUrl = currentUser.getPhotoUrl();

            // Check if user's email is verified
            boolean emailVerified = currentUser.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = currentUser.getUid();
        } else {
            // No user is signed in
        }
    }


    public void Login(View v){
        if(getEmail().length() > 0 && getPassword().length() > 0) { //Very secure
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    public void createAccount(String email, String password) {
        if(email.length() > 0 && password.length() > 0) {
            if(password.length() > 5) {
                resetErrorText();
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("myTag", "createUserWithEmail:success");
                                    Toast.makeText(getApplicationContext(), "Authentication success! \nPlease sign in.",
                                            Toast.LENGTH_LONG).show();
                                    FirebaseUser user = mAuth.getCurrentUser();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("myTag", "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(getApplicationContext(), "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    if(task.getException() instanceof FirebaseAuthUserCollisionException) {
                                        setErrorText(4); //email taken
                                    }
                                }
                            }
                        });
            } else {
                setErrorText(3); //password too short
            }
        } else {
            setErrorText(1);
        }

    }

    public void signInAccount(String email, String password) {
        if(email.length() > 0 && password.length() > 0) {
            resetErrorText();
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("myTag", "signInWithEmail:success");
                                Toast.makeText(getApplicationContext(), "Authentication success!",
                                        Toast.LENGTH_LONG).show();
                                FirebaseUser user = mAuth.getCurrentUser();

                                //Change activity
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);

                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("myTag", "signInWithEmail:failure", task.getException());


                                Toast.makeText(getApplicationContext(), "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                setErrorText(2);
                            }
                        }
                    });
        } else {
            setErrorText(1); //missing one or both fields
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.emailCreateAccountButton) {
            createAccount(emailText.getText().toString(), passwordText.getText().toString());
        } else if (i == R.id.emailSignInButton) {
            signInAccount(emailText.getText().toString(), passwordText.getText().toString());
        }
    }

    public String getEmail() {
        return emailText.getText().toString();
    }

    public String getPassword() {
        return passwordText.getText().toString();
    }

    public void setErrorText(int errorID) {
        String result = "";
        switch (errorID){
            case 1:
                result = "Please write an email and password";
                break;
            case 2:
                result = "Account not found, please try again";
                break;
            case 3:
                result = "Password too short, it should be at least 6 characters";
                break;
            case 4:
                result = "The email address is already in use ";
                break;
            default:
                result = "Error";
                break;
        }
        errorText.setText(result);
    }
    public void resetErrorText() {
        errorText.setText("");
    }

}
