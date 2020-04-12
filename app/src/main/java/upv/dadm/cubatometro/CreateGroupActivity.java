package upv.dadm.cubatometro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import upv.dadm.cubatometro.Database.DAO;
import upv.dadm.cubatometro.entidades.Grupo;
import upv.dadm.cubatometro.entidades.User;
import upv.dadm.cubatometro.adapter.CreateGroupAdapter;

public class CreateGroupActivity extends AppCompatActivity {
    private final static int PICK_IMAGE = 1;
    private ImageView groupIcon;
    private EditText groupName;
    private ArrayList<User> members = new ArrayList<>();
    private static RecyclerView recyclerView;
    public static CreateGroupAdapter adapter;
    private static Context context;
    private static ArrayList<User> data = new ArrayList<>();
    private FirebaseAuth mAuth;
    private StorageReference mStorageRef;
    private Uri selectedImageUri;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);
        CreateGroupActivity.context = getApplicationContext();

        groupName = findViewById(R.id.groupname_edittext_creategroup);

        mAuth = FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();

        //Este va a ser el id del grupo
        final String groupID = UUID.randomUUID().toString();


        Button crearGrupo = findViewById(R.id.button_creategroup);
        crearGrupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    uploadImageToFirebase(groupID);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                getSelectedMembers();
                Grupo nuevoGrupo = new Grupo(groupIcon, groupID, members);
                /************** Añadir grupo a Firebase *********************/

                startActivity(new Intent(CreateGroupActivity.this, RankingActivity.class));
            }
        });

        groupIcon = findViewById(R.id.groupicon_imageview_creategroup);
        groupIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, PICK_IMAGE);
            }
        });

        recyclerView = findViewById(R.id.addmembers_recyclerview_creategroup);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);

        /* Array de prueba. Hay que sustituir por llamada a método getAllUsers() */
        new DAO().getAllUsers();

        adapter = new CreateGroupAdapter(data);
        recyclerView.setAdapter(adapter);
    }

    public static Context getAppContext() {
        return CreateGroupActivity.context;
    }

    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            selectedImageUri = data.getData();
            groupIcon.setImageURI(selectedImageUri);
        }
    }

    protected void getSelectedMembers() {
        ArrayList<Integer> selectedUsers = adapter.getSelectedIds();
        for (int i = 0; i < selectedUsers.size(); i++) {
            //Log.d("SELECTED IDS", data.get(selectedUsers.get(i)).getUsername());
            String id = data.get(selectedUsers.get(i)).getUserID();
            String username = data.get(selectedUsers.get(i)).getUsername();
            ImageView profilePic = data.get(selectedUsers.get(i)).getProfilePic();
            members.add(new User(profilePic, username, id));
        }
        //Log.d("ARRAY MEMBERS", members.toString());
    }

    private void uploadImageToFirebase(String groupID) throws IOException {
        StorageReference storageReference = mStorageRef.child("images/groups/" + groupID + "/profilePic.jpg");
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

    public static void loadUser(User user){
        data.add(user);
        adapter.notifyItemInserted(data.size());
    }

    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
