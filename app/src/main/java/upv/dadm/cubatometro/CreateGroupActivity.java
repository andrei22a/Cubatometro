package upv.dadm.cubatometro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashMap;

import upv.dadm.cubatometro.Database.DAO;
import upv.dadm.cubatometro.entidades.Grupo;
import upv.dadm.cubatometro.entidades.User;
import upv.dadm.cubatometro.adapter.CreateGroupAdapter;

public class CreateGroupActivity extends AppCompatActivity {
    private final static int PICK_IMAGE = 1;
    private ImageView groupIcon;
    private EditText groupName;
    private ArrayList<User> members;
    private static RecyclerView recyclerView;
    public static CreateGroupAdapter adapter;
    private static Context context;
    private static ArrayList<User> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);
        CreateGroupActivity.context = getApplicationContext();

        groupName = findViewById(R.id.groupname_edittext_creategroup);


        Button crearGrupo = findViewById(R.id.button_creategroup);
        crearGrupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Grupo newGroup = new Grupo(groupIcon, groupName.getText().toString(), members);
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
            Uri Selected_Image_Uri = data.getData();
            groupIcon.setImageURI(Selected_Image_Uri);
        }
    }

    protected ArrayList<User> getSelectedMembers(){
        /* Acceder al RecyclerView y seleccionar todos los items con el Switch=true


         */

        return this.members;
    }

    protected ArrayList<User> getAllUsers(){
        ArrayList<User> users = new ArrayList<>();
        /* Acceder a la base de datos y recuperar todos los usuarios


         */

        return users;
    }

    public static void loadUser(User user){
        data.add(user);
        adapter.notifyItemInserted(data.size());
    }

    public static void updateUsersRecycler(){
        adapter = new CreateGroupAdapter(data);
        recyclerView.setAdapter(adapter);
    }
}
