package upv.dadm.cubatometro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;

import upv.dadm.cubatometro.Database.FirebaseIni;

public class LoginActivity extends AppCompatActivity{
    private EditText username;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username_edittext_login);
        password = findViewById(R.id.password_edittext_login);

        Button iniciarSesion = findViewById(R.id.inicio_button_login);
        Button registrarUsuario = findViewById(R.id.registar_button_login);

        iniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkLogin()) {
                    Intent intent = new Intent(LoginActivity.this, GroupsActivity.class);
                    intent.putExtra("user", username.getText().toString());
                    intent.putExtra("password", password.getText().toString());
                    startActivity(intent);
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

    protected boolean checkLogin(){
        /* Check if user and password match

        */

        return true;
    }
}
