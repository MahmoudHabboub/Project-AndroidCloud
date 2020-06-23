package com.example.coronavirusproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.annotation.NonNull;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    FirebaseAuth auth;
    EditText email_edtxt;
    EditText password_edtxt;
    Button login_btn;
    TextView signup_txtv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth=FirebaseAuth.getInstance();
        email_edtxt=findViewById(R.id.email_edtxt);
        password_edtxt=findViewById(R.id.password_edtxt);
        login_btn=findViewById(R.id.login_btn);
        signup_txtv=findViewById(R.id.signup_txtv2);

        FirebaseUser user=auth.getCurrentUser();
        if(user !=null){
            Intent intent=new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
            finish();
        }

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!email_edtxt.getText().toString().trim().isEmpty()&& !password_edtxt.getText().toString().trim().isEmpty()){
                    Task<AuthResult> Task=auth.signInWithEmailAndPassword(email_edtxt.getText().toString(),password_edtxt.getText().toString());
                    Task.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull com.google.android.gms.tasks.Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                FirebaseUser user=auth.getCurrentUser();
                                if(user !=null){
                                    String userName=user.getEmail();
                                    String userUid= user.getUid();
                                    Intent intent=new Intent(getApplicationContext(), HomeActivity.class);
                                    intent.putExtra("userName",userName);
                                    intent.putExtra("userUid",userUid);
                                    startActivity(intent);
                                }
                            }
                        }
                    });
                    Task.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                        }
                    });

                }else{
                    Toast.makeText(getApplicationContext(),"Error !",Toast.LENGTH_LONG).show();
                }
            }
        });

        signup_txtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(),SignUpActivity.class);
                startActivity(intent);
            }
        });

    }
}
