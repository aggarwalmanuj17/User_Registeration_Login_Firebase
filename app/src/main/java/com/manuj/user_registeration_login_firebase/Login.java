package com.manuj.user_registeration_login_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class Login extends AppCompatActivity {
    EditText eTxtEmail,eTxtPassword;
    Button Btnlogin;
    TextView Signup;

    User user;

    ProgressDialog progressDialog;

    void initViews() {
        eTxtEmail = findViewById(R.id.emailLogin);
        eTxtPassword = findViewById(R.id.passLogin);


        Signup = findViewById(R.id.goSign);
        Btnlogin = findViewById(R.id.LogButton);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);


        user = new User();

        Btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                user.Email = eTxtEmail.getText().toString();

                user.Password = eTxtPassword.getText().toString();

                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                boolean flag=true;
                if (!((user.Email.matches(emailPattern)) && (user.Email.length() > 0))){
                    flag=false;
                    eTxtEmail.setText("");
                    eTxtEmail.setFocusable(true);
                    eTxtEmail.setError("Invalid Email");
                    progressDialog.dismiss();
                }if(!(user.Password.length()>5)){
                    flag=false;
                    eTxtPassword.setText("");
                    eTxtPassword.setFocusable(true);
                    eTxtPassword.setError("Invalid Password");
                    progressDialog.dismiss();
                }

                if(flag==true){
                    loginUserfromFirebase();                }
            }
        });




        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, SignUp.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        initViews();
    }
    void loginUserfromFirebase() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(user.Email, user.Password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            Toast.makeText(Login.this, "Login Success!!", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(Login.this, MainActivity.class);
                            startActivity(intent);
                            finish();

                        } else {
                            progressDialog.dismiss();
                            if(task.getException() instanceof FirebaseAuthInvalidCredentialsException){
                                Toast.makeText(Login.this,"Invalid Credentials",Toast.LENGTH_LONG).show();
                                eTxtEmail.setText("");
                                eTxtPassword.setText("");
                            }else if(task.getException() instanceof FirebaseAuthInvalidUserException){
                                Toast.makeText(Login.this,"User Not Found",Toast.LENGTH_LONG).show();
                                eTxtEmail.setText("");
                                eTxtPassword.setText("");
                            }else {
                                Toast.makeText(Login.this, "Something Went Wrong !!", Toast.LENGTH_LONG).show();
                            }
                        }

                    }
                });

    }
}