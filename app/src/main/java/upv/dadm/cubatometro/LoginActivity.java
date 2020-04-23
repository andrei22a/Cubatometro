package upv.dadm.cubatometro;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity{
    private FirebaseAuth mAuth;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private EditText usernameInput;
    private EditText passwordInput;
    private CheckBox recordarLoginCheckbox;
    private boolean recordarLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        usernameInput = findViewById(R.id.username_edittext_login);
        passwordInput = findViewById(R.id.password_edittext_login);
        recordarLoginCheckbox = findViewById(R.id.recordar_usuario_checkbox_login);
        preferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        editor = preferences.edit();

        Button iniciarSesion = findViewById(R.id.inicio_button_login);
        final Button registrarUsuario = findViewById(R.id.registrar_button_login);

        recordarLogin = preferences.getBoolean("recordarLogin", false);
        if (recordarLogin){
            usernameInput.setText(preferences.getString("username", ""));
            passwordInput.setText(preferences.getString("password", ""));
            recordarLoginCheckbox.setChecked(true);
        }

        iniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = usernameInput.getText().toString();
                String pass = passwordInput.getText().toString();

                if (recordarLoginCheckbox.isChecked()){
                    editor.putString("username", email);
                    editor.putString("password", pass);
                    editor.putBoolean("recordarLogin", true);
                    editor.apply();
                } else {
                    editor.clear();
                    editor.commit();
                }

                if(!email.equals("") && !pass.equals("")){
                    mAuth.signInWithEmailAndPassword(email, pass)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        toastMessage("Logged correctly");
                                        startActivity(new Intent(getApplicationContext(), GroupsActivity.class));
                                    } else {
                                        toastMessage("Login failed\n" + task.getException().getMessage());
                                    }
                                }
                            });                }else{
                    toastMessage("You didn't fill in all the fields.");
                }
            }
        });

        registrarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterUserActivity.class);
                startActivity(intent);
            }
        });
    }

    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
