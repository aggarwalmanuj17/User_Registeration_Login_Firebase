package com.manuj.user_registeration_login_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    EditText Name,phone,Add,email;
    Button Out;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Name=findViewById(R.id.namee);
        phone=findViewById(R.id.Myphone);
        Add=findViewById(R.id.add);
        email=findViewById(R.id.Maill);

        getSupportActionBar().setTitle("Welcome");

        Out=findViewById(R.id.logout);
        user=new User();

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();


        Out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth auth=FirebaseAuth.getInstance();
                auth.signOut();
                Intent intent=new Intent(MainActivity.this,Login.class);
                startActivity(intent);
                Toast.makeText(MainActivity.this, "Logged Out", Toast.LENGTH_SHORT).show();
            }
        });

        String uid = firebaseUser.getUid();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("users").document(uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot documentSnapshot=task.getResult();
                            if(documentSnapshot.exists() && documentSnapshot!=null) {
                                Log.i("Task", "" + task.getResult());
                                user = documentSnapshot.toObject(User.class);
                                Log.i("User",user.toString());
                                String name=user.Name;
                                String mob=user.Phone;
                                String add=user.Address;
                                String mail=user.Email;

                                Name.setText(""+name);
                                phone.setText(""+mob);
                                Add.setText(""+add);
                                email.setText(""+mail);

                            }
                        }else {
                            Log.i("Username", "Error getting documents.", task.getException());
                        }
                    }
                });







    }
}