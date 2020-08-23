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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUp extends AppCompatActivity {
    EditText Name,email, password,Add,phone;
    Button signup;
    TextView signin;

    ProgressDialog progressDialog;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().hide();



        Name=findViewById(R.id.name);
        email=findViewById(R.id.mail);
        password=findViewById(R.id.password);
        Add=findViewById(R.id.Address);
        phone=findViewById(R.id.phone);

        signin=findViewById(R.id.reg);

        signup=findViewById(R.id.Reg);

        user = new User();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(Email_signup.this, Login_Email.class);
//                startActivity(intent);
//                finish();
            }
        });



        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.Email = email.getText().toString();
                user.Password = password.getText().toString();
                user.Name=Name.getText().toString();
                user.Address=Add.getText().toString();
                user.Phone=phone.getText().toString();

                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                // String namePattern = "[a-z]";
                boolean flag = true;
                progressDialog.show();
                progressDialog.setCancelable(false);
                if (!((user.Email.matches(emailPattern)) && (user.Email.length() > 0))) {
                    flag = false;
                    email.setText("");
                    email.setFocusable(true);
                    email.setError("Invalid Email");
                    progressDialog.dismiss();
                }
                if (!(user.Password.length() > 7)) {
                    flag = false;
                    password.setText("");
                    password.setFocusable(true);
                    password.setError("Invalid Password");
                    progressDialog.dismiss();
                }
                if (!(user.Phone.length() ==10)) {
                    flag = false;
                    phone.setText("");
                    phone.setFocusable(true);
                    phone.setError("Invalid Phone");
                    progressDialog.dismiss();
                }

                if (flag == true) {
                    registerUserInFirebase();
                    progressDialog.show();
                }


            }
        });
    }

    void registerUserInFirebase() {

        FirebaseAuth auth = FirebaseAuth.getInstance();
        Log.i("User Data =>", "" + user.Email + "=>" + user.Password);
        auth.createUserWithEmailAndPassword(user.Email, user.Password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isComplete()) {
                            progressDialog.dismiss();
                            if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                Toast.makeText(SignUp.this,"User Already Registered",Toast.LENGTH_LONG).show();
                            }else {
//                                Toast.makeText(SignUp.this, "Something Went Wrong !!", Toast.LENGTH_LONG).show();
                                saveUserInFirebase();
                                Toast.makeText(SignUp.this, "Registered Successfully!", Toast.LENGTH_LONG).show();

                            }

                        } else {
                            Toast.makeText(SignUp.this, "Something Went Wrong !!", Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }
    void saveUserInFirebase() {

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String uid = firebaseUser.getUid(); // This is uid of User which we have just created

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(uid).set(user)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isComplete()) {
                            progressDialog.dismiss();
                            Intent intent = new Intent(SignUp.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(SignUp.this, "Something Went Wrong !!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }




}