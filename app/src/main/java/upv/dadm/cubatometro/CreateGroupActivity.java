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
                    dao.getAllUsers();
                }
                adapter.getFilter().filter(newText);
                return true;
            }
        });

        Button crearGrupo = findViewById(R.id.button_creategroup);
        crearGrupo.setOnClickListener(new View.OnClickListener() {
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
                    try {
                        uploadImageToFirebase(groupID);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    startActivity(new Intent(CreateGroupActivity.this, GroupsActivity.class));
                } else {
                    toastMessage("You must type a name for your group");
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

        /* Array de prueba. Hay que sustituir por llamada a método getAllUsers() */
        dao.getAllUsers();

        adapter = new CreateGroupAdapter(data);
        recyclerView.setAdapter(adapter);
    }

    public static Context getAppContext() {
        return CreateGroupActivity.context;
    }


    private ArrayList<User> filter(ArrayList<User> datas, String newText) {
        newText = newText.toLowerCase();

        final ArrayList<User> filteredModelList = new ArrayList<>();
        for (User data : datas) {
            final String text = data.getUsername().toLowerCase();
            if (text.contains(newText)) {
                filteredModelList.add(data);
            }
        }
        return filteredModelList;
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
        /*for (int i = 0; i < selectedUsers.size(); i++) {
            //Log.d("SELECTED IDS", data.get(selectedUsers.get(i)).getUsername());
            String id = data.get(selectedUsers.get(i)).getUserID();
            String username = data.get(selectedUsers.get(i)).getUsername();
            ImageView profilePic = data.get(selectedUsers.get(i)).getProfilePic();
            members.add(new User(profilePic, username, id));
        }*/
        //Log.d("ARRAY MEMBERS", members.toString());
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
            adapter.notifyItemInserted(data.size());
        }
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
