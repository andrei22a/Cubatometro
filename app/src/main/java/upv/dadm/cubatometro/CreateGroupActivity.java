package upv.dadm.cubatometro;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.UUID;

import upv.dadm.cubatometro.Database.DAO;
import upv.dadm.cubatometro.adapter.DividerItemDecoration;
import upv.dadm.cubatometro.entidades.Grupo;
import upv.dadm.cubatometro.entidades.Registro;
import upv.dadm.cubatometro.entidades.User;
import upv.dadm.cubatometro.adapter.CreateGroupAdapter;

public class CreateGroupActivity extends AppCompatActivity {
    private final static int PICK_IMAGE = 1;
    private ImageView groupIcon;
    private EditText groupNameInput;
    private SearchView searchView;
    private ArrayList<User> members = new ArrayList<>();
    private static RecyclerView recyclerView;
    public static CreateGroupAdapter adapter;
    private static Context context;
    private static ArrayList<User> data = new ArrayList<>();
    private static ArrayList<User> originalData = new ArrayList<>();
    private static FirebaseAuth mAuth;
    private StorageReference mStorageRef;
    private Uri selectedImageUri;
    private DAO dao = new DAO();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);
        CreateGroupActivity.context = getApplicationContext();

        groupNameInput = findViewById(R.id.groupname_edittext_creategroup);
        searchView = findViewById(R.id.searchmember_searchview_creategroup);

        mAuth = FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();

        //Este va a ser el id del grupo
        final String groupID = UUID.randomUUID().toString();

        data.clear();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText == null || newText.length() == 0){
                    data.clear();
                    dao.getAllUsers();
                    Collections.sort(data);
                }
                adapter.getFilter().filter(newText);
                return true;
            }
        });

        Button crearGrupo = findViewById(R.id.button_creategroup);
        crearGrupo.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                getSelectedMembers();
                addCurrentUserToGroup();
                if(!groupNameInput.getText().toString().equals("")) {
                    String groupName = groupNameInput.getText().toString();
                    Grupo nuevoGrupo = new Grupo(groupIcon, groupName, groupID, members);
                    /************** Añadir grupo a Firebase *********************/
                    Registro registroInicial = new Registro();
                    dao.insertNewGroup(groupID, groupName, members, registroInicial);
                    if(selectedImageUri != null){
                        try {
                            uploadImageToFirebase(groupID);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    startActivity(new Intent(CreateGroupActivity.this, GroupsActivity.class));
                } else {
                    toastMessage("Debes elegir un nombre para tu grupo!");
                }

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
        recyclerView.addItemDecoration(new DividerItemDecoration(this));
        recyclerView.setHasFixedSize(true);

        /* Array de prueba. Hay que sustituir por llamada a método getAllUsers() */
        dao.getAllUsers();
        originalData = data;


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
        members = adapter.getSelectedIds();
    }

    private void uploadImageToFirebase(String groupID) throws IOException {
        StorageReference storageReference = mStorageRef.child("images/groups/" + groupID + "/groupPic.jpg");
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
        if(!user.getUserID().equals(mAuth.getCurrentUser().getUid())) {
            data.add(user);
            originalData.add(user);
            adapter.notifyItemInserted(data.size());
        }
        Collections.sort(data);
        Collections.sort(originalData);
    }

    public void addCurrentUserToGroup(){
        FirebaseUser currentUser = mAuth.getCurrentUser();
        assert currentUser != null;
        ImageView image = new ImageView(context);
        image.setImageURI(currentUser.getPhotoUrl());
        User user = new User(image, currentUser.getDisplayName(), currentUser.getUid());
        members.add(user);
    }

    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
