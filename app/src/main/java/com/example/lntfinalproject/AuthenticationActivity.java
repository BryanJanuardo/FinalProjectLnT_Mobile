package com.example.lntfinalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class AuthenticationActivity extends AppCompatActivity {

    FirebaseAuth auth;
    LinearLayout registerButton, loginButton;
    TextView registerText, loginText;
    LinearLayout registerForm, loginForm;
    EditText bimbelId, emailRegister, name, passwordRegister, confirmedPasswordRegister;
    EditText emailLogin, passwordLogin;
    Button submitRegister, submitLogin;

    private void changeToLoginForm(){
        if(registerForm.getVisibility() == View.VISIBLE){
            registerForm.setVisibility(View.GONE);
            loginForm.setVisibility(View.VISIBLE);
            loginButton.setBackgroundResource(R.drawable.border);
            registerButton.setBackgroundColor(Color.parseColor("#565961"));
            registerText.setTextColor(Color.WHITE);
            loginText.setTextColor(Color.BLACK);
        }
    }

    private void changeToRegisterForm(){
        if(loginForm.getVisibility() == View.VISIBLE){
            loginForm.setVisibility(View.GONE);
            registerForm.setVisibility(View.VISIBLE);
            registerButton.setBackgroundResource(R.drawable.border);
            loginButton.setBackgroundColor(Color.parseColor("#565961"));
            registerText.setTextColor(Color.BLACK);
            loginText.setTextColor(Color.WHITE);
        }
    }

    private boolean registerFormValidation(String bimbelid, String email, String name, String password, String confirmedPassword){
        if(bimbelid.isEmpty()){
            Toast.makeText(this, "Bimbelid harus diisi!", Toast.LENGTH_SHORT).show();
            return false;
        }else if(email.isEmpty()){
            Toast.makeText(this, "Email harus diisi!", Toast.LENGTH_SHORT).show();
            return false;
        }else if(!email.contains("@")){
            Toast.makeText(this, "Email harus ada @!", Toast.LENGTH_SHORT).show();
            return false;
        }else if(!email.endsWith(".com")){
            Toast.makeText(this, "Email harus diakhiri .com!", Toast.LENGTH_SHORT).show();
            return false;
        }else if(name.isEmpty()){
            Toast.makeText(this, "Nama harus diisi!", Toast.LENGTH_SHORT).show();
            return false;
        }else if(name.length() < 5){
            Toast.makeText(this, "Nama harus minimal 5 kata!", Toast.LENGTH_SHORT).show();
            return false;
        }else if(password.isEmpty()){
            Toast.makeText(this, "Password harus diisi!", Toast.LENGTH_SHORT).show();
            return false;
        }else if(confirmedPassword.isEmpty()){
            Toast.makeText(this, "Confirmed Password harus diisi!", Toast.LENGTH_SHORT).show();
            return false;
        }else if(!confirmedPassword.equals(password)){
            Toast.makeText(this, "Password harus sama dengan Confirmed Password!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean loginFormValidation(String email, String password){
        if(email.isEmpty()){
            Toast.makeText(this, "Email harus diisi!", Toast.LENGTH_SHORT).show();
            return false;
        }else if(password.isEmpty()){
            Toast.makeText(this, "Password harus diisi!", Toast.LENGTH_SHORT).show();
            return  false;
        }
        return true;
    }

    private void submitRegisterForm(){
        String tempBimbelId, tempEmail, tempName, tempPassword, tempConfirmedPassword;
        tempBimbelId = bimbelId.getText().toString();
        tempEmail = emailRegister.getText().toString();
        tempName = name.getText().toString();
        tempPassword = passwordRegister.getText().toString();
        tempConfirmedPassword = confirmedPasswordRegister.getText().toString();

        if(!registerFormValidation(tempBimbelId, tempEmail, tempName, tempPassword, tempConfirmedPassword)) return;

        auth.createUserWithEmailAndPassword(tempEmail, tempPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(AuthenticationActivity.this, "Register successful.", Toast.LENGTH_SHORT).show();
                            changeToLoginForm();
                        } else {
                            Toast.makeText(AuthenticationActivity.this, "Register failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void submitLoginForm(){
        String tempEmail, tempPassword;
        tempEmail = emailLogin.getText().toString();
        tempPassword = passwordLogin.getText().toString();

        if(!loginFormValidation(tempEmail, tempPassword)) return;

        auth.signInWithEmailAndPassword(tempEmail, tempPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(AuthenticationActivity.this, "Authentication successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AuthenticationActivity.this, MainActivity.class);
                            intent.putExtra("EMAIL", tempEmail);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(AuthenticationActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authentication_form);

        auth = FirebaseAuth.getInstance();
        registerButton = findViewById(R.id.RegisterButton);
        loginButton = findViewById(R.id.LoginButton);

        registerText = findViewById(R.id.register_text);
        loginText = findViewById(R.id.login_text);

        registerForm = findViewById(R.id.register_form);
        loginForm = findViewById(R.id.login_form);

        bimbelId = findViewById(R.id.InputIdBimbel);
        emailRegister = findViewById(R.id.InputEmail);
        name = findViewById(R.id.InputName);
        passwordRegister = findViewById(R.id.InputPassword);
        confirmedPasswordRegister = findViewById(R.id.InputConfirmedPassword);

        emailLogin = findViewById(R.id.InputEmailLogin);
        passwordLogin = findViewById(R.id.InputPasswordLogin);

        submitRegister = findViewById(R.id.register_submit_button);
        submitLogin = findViewById(R.id.login_submit_button);

        registerButton.setOnClickListener(v -> {
            changeToRegisterForm();
        });

        loginButton.setOnClickListener(v -> {
            changeToLoginForm();
        });

        submitRegister.setOnClickListener(v -> {
            submitRegisterForm();
        });

        submitLogin.setOnClickListener(v -> {
            submitLoginForm();
        });
    }
}