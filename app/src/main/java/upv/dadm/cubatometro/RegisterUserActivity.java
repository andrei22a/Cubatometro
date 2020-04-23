package upv.dadm.cubatometro;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import upv.dadm.cubatometro.Database.DAO;

public class RegisterUserActivity extends AppCompatActivity {
    private final static int PICK_IMAGE = 1;
    private static final String TAG = "RegisterUserActivity";

    private FirebaseAuth mAuth;
    private StorageReference mStorageRef;
    private Uri selectedImageUri;

    private EditText usernameInput;
    private EditText emailInput;
    private EditText passwordInput;
    private ImageView userIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        checkFilePermissions();

        mAuth = FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();

        usernameInput = findViewById(R.id.username_edittext_register);
        emailInput = findViewById(R.id.email_edittext_register);
        passwordInput = findViewById(R.id.password_edittext_register);
        userIcon = findViewById(R.id.usericon_imageview_register);

        Button registerUser = findViewById(R.id.user_button_register);

        registerUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            final String username = usernameInput.getText().toString();
            String email = emailInput.getText().toString();
            String password = passwordInput.getText().toString();

            if(!email.equals("") && !password.equals("")){
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete( Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    toastMessage("Register successfully");
                                    addUserToFirebase(username);
                                    if(selectedImageUri != null){
                                        try {
                                            uploadImageToFirebase();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }

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
            selectedImageUri = data.getData();
            userIcon.setImageURI(selectedImageUri);
        }
    }

    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void uploadImageToFirebase() throws IOException {
        FirebaseUser user = mAuth.getCurrentUser();
        String  userID = user.getUid();

        StorageReference storageReference = mStorageRef.child("images/users/" + userID + "/profilePic.jpg");
        Bitmap bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 25, baos);
        byte[] data = baos.toByteArray();
        //uploading the image
        UploadTask uploadTask2 = storageReference.putBytes(data);
        uploadTask2.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                toastMessage("Upload success");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                toastMessage("Upload failed");
            }
        });
    }

    private void checkFilePermissions() {
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
            int permissionCheck = RegisterUserActivity.this.checkSelfPermission("Manifest.permission.READ_EXTERNAL_STORAGE");
            permissionCheck += RegisterUserActivity.this.checkSelfPermission("Manifest.permission.WRITE_EXTERNAL_STORAGE");
            if (permissionCheck != 0) {
                this.requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE,android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1001); //Any number
            }
        }else{
            Log.d(TAG, "checkBTPermissions: No need to check permissions. SDK version < LOLLIPOP.");
        }
    }

    private void addUserToFirebase(String username){
        FirebaseUser user = mAuth.getCurrentUser();
        String userID = user.getUid();
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(username).build();
        user.updateProfile(profileUpdates);
        DAO dao = new DAO();
        dao.insertNewUser(userID,  username);
    }
}
