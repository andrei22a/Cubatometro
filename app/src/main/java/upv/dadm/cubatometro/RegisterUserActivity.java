package upv.dadm.cubatometro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.FirebaseDatabase;

import upv.dadm.cubatometro.Database.DAO;
import upv.dadm.cubatometro.Database.FirebaseIni;

public class RegisterUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        final EditText username = findViewById(R.id.username_edittext_register);
        final EditText password = findViewById(R.id.password_edittext_register);
        Button button = findViewById(R.id.user_button_register);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DAO().insertNewUser(username.getText().toString(), password.getText().toString());
                startActivity(new Intent(RegisterUserActivity.this, GroupsActivity.class));
            }
        });
    }
}
