package upv.dadm.cubatometro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import upv.dadm.cubatometro.Database.DAO;
import upv.dadm.cubatometro.entidades.User;

public class RegisterUserActivity extends AppCompatActivity {
    private final static int PICK_IMAGE = 1;
    private FirebaseAuth mAuth;

    private EditText usernameInput;
    private EditText passwordInput;
    private ImageView userIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        mAuth = FirebaseAuth.getInstance();

        usernameInput = findViewById(R.id.username_edittext_register);
        passwordInput = findViewById(R.id.password_edittext_register);
        userIcon = findViewById(R.id.usericon_imageview_register);

        Button registerUser = findViewById(R.id.user_button_register);

        registerUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String email = usernameInput.getText().toString();
            String password = passwordInput.getText().toString();
            if(!email.equals("") && !password.equals("")){

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete( Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    toastMessage("Register successfully");
                                    startActivity(new Intent(getApplicationContext(), GroupsActivity.class));
                                } else {

                                    toastMessage(task.getException().getMessage());
                                }
                            }
                        });
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

    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
