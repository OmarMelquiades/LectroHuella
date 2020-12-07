
package com.example.lectrohuella;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import androidx.biometric.BiometricPrompt;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {


    private TextView AuthStatus;
    private Button btnAuth;

    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private androidx.biometric.BiometricPrompt.PromptInfo promptInfo;


    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AuthStatus = findViewById(R.id.AuthStatus);
        btnAuth = findViewById(R.id.btnAuth);

        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(MainActivity.this, executor, new BiometricPrompt.AuthenticationCallback(){
            @Override
            public void onAuthenticationError(int errorCode, CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                AuthStatus.setText("Error de autenticación, vuelve a intentarlo");
                Toast.makeText(MainActivity.this,"Error de autenticación"+errString, Toast.LENGTH_LONG).show();
            }

            /*
            @Override
            public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
                super.onAuthenticationHelp(helpCode, helpString);
                AuthStatus.setText("Ingrese su huella dactilar");
                Toast.makeText(MainActivity.this,"Ingres sue huella dactilar", Toast.LENGTH_LONG).show();
            }
             */

            @Override
            public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                AuthStatus.setText("Bienvenido");
                Toast.makeText(MainActivity.this,"Bienvenido", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(MainActivity.this,MainActivity2.class);
                startActivity(intent);
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                AuthStatus.setText("Autenticación fallida");
                Toast.makeText(MainActivity.this,"Autenticación fallida", Toast.LENGTH_LONG).show();
            }
        });

        promptInfo = new androidx.biometric.BiometricPrompt.PromptInfo.Builder()
                .setTitle("Autenticacion")
                .setSubtitle("Iniciar sisión usando huella dactilar")
                .setDescription("Coloca tu huella sobre el sensor para poder autenticar el acceso")
                .setNegativeButtonText("Usar contraseña")
                .build();

        btnAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                biometricPrompt.authenticate(promptInfo);
            }
        });
    }
}