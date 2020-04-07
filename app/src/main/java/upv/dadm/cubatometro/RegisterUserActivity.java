package upv.dadm.cubatometro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import upv.dadm.cubatometro.Database.DAO;
import upv.dadm.cubatometro.entidades.User;

public class RegisterUserActivity extends AppCompatActivity {
    private final static int PICK_IMAGE = 1;

    private EditText username;
    private EditText password;
    private ImageView userIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        username = findViewById(R.id.username_edittext_register);
        password = findViewById(R.id.password_edittext_register);
        userIcon = findViewById(R.id.usericon_imageview_register);

        Button registerUser = findViewById(R.id.user_button_register);
        registerUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!userExists(username.getText().toString())) {
                    User newUser = new User(userIcon.getId(), username.getText().toString(), password.getText().toString());
                    new DAO().insertNewUser(username.getText().toString(), password.getText().toString());
                    startActivity(new Intent(RegisterUserActivity.this, GroupsActivity.class));
                }
            }
        });

        userIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, PICK_IMAGE);
            }
        });
    }

    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Uri Selected_Image_Uri = data.getData();
            userIcon.setImageURI(Selected_Image_Uri);
        }
    }

    protected boolean userExists(String username){
        /* Acceder a la base de datos y comprobar que el nombre de usuario no esté en uso


         */

        return false;
    }
}
