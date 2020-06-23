package com.example.coronavirusproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
    FirebaseAuth auth;
    EditText email_edtxt2;
    EditText password_edtxt2;
    Button login_btn2;
    TextView signup_txtv2;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference parentReference;
    DatabaseReference usersReference;
    DatabaseReference chatsReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        auth= FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        parentReference = firebaseDatabase.getReference();
        usersReference = parentReference.child("users");

        email_edtxt2=findViewById(R.id.email_edtxt2);
        password_edtxt2=findViewById(R.id.password_edtxt2);
        login_btn2=findViewById(R.id.login_btn2);
        signup_txtv2=findViewById(R.id.signup_txtv2);

        login_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!email_edtxt2.getText().toString().trim().isEmpty()&& !password_edtxt2.getText().toString().trim().isEmpty()){
                    final Task<AuthResult> Task=auth.createUserWithEmailAndPassword(email_edtxt2.getText().toString(),password_edtxt2.getText().toString());
                    Task.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull com.google.android.gms.tasks.Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                FirebaseUser user=auth.getCurrentUser();
                                if(user !=null){
                                    String userName=user.getEmail();
                                    String userUid= user.getUid();
                                    User cr_user = new User(userName, userUid);
                                    Task task1 = usersReference.push().setValue(cr_user);
                                        Intent intent=new Intent(getApplicationContext(), HomeActivity.class);
                                        intent.putExtra("userName",userName);
                                        intent.putExtra("userUid",userUid);
                                        startActivity(intent);
                                }
                            }
                        }
                    });
                }else{
                    Toast.makeText(getApplicationContext(),"Error !",Toast.LENGTH_LONG).show();
                }
            }
        });

        signup_txtv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
