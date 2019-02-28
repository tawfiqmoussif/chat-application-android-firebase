package com.moussif.tawfi.myapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SigneUpActivity extends AppCompatActivity implements View.OnClickListener{
    private FirebaseAuth mAuth;
    DatabaseReference reference;
    Intent i;
    EditText editTextEmail, editTextPassword,editTextCPassword,editUserName;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signe_up);
        editTextEmail = (EditText) findViewById(R.id.editEmail);
        editTextPassword = (EditText) findViewById(R.id.editPassword);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        editTextCPassword=(EditText)findViewById(R.id.checkPassword);
        editUserName=(EditText)findViewById(R.id.username);
        findViewById(R.id.signup).setOnClickListener(this);
        findViewById(R.id.textViewLogin).setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
    }
    private void registerUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String cpassword=editTextCPassword.getText().toString().trim();
        final String username=editUserName.getText().toString().trim();
        if (email.isEmpty()) {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please enter a valid email");
            editTextEmail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }
        if(username.length()<4){
            editTextPassword.setError("Minimum length of userName should be 4");
            editTextPassword.requestFocus();
            return;
        }
        if (password.length() < 6) {
            editTextPassword.setError("Minimum length of password should be 6");
            editTextPassword.requestFocus();
            return;
        }
        if(!password.equals(cpassword)){
            editTextCPassword.setError("password does not match confirm password");
            editTextCPassword.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    FirebaseUser firebaseUser = mAuth.getCurrentUser();
                    assert firebaseUser != null;
                    String userid = firebaseUser.getUid();

                    reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("id", userid);
                    hashMap.put("username", username);
                    hashMap.put("imageURL", "default");
                    hashMap.put("search",username.toLowerCase());
                    reference.setValue(hashMap);
                    finish();
                    startActivity(new Intent(SigneUpActivity.this, MainActivity.class));
                    //Toast.makeText(getApplicationContext(),"success heeeeee",Toast.LENGTH_LONG).show();
                } else {

                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(getApplicationContext(), "You are already registered", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signup:
                registerUser();
                break;
            case R.id.textViewLogin:
                finish();
                startActivity(new Intent(this, LoginActivity.class));
                break;
            default:
                break;
        }
    }
}
