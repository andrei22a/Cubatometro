package upv.dadm.cubatometro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;

import upv.dadm.cubatometro.Lib.Grupo;
import upv.dadm.cubatometro.Lib.User;
import upv.dadm.cubatometro.adapter.CustomRecyclerView;

public class CreateGroupActivity extends AppCompatActivity {
    private final static int PICK_IMAGE = 1;
    private ImageView groupIcon;
    private EditText groupName;
    private ArrayList<User> members;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

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

        RecyclerView recyclerView = findViewById(R.id.addmembers_recyclerview_creategroup);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);

        /* Array de prueba. Hay que sustituir por llamada a método getAllUsers() */
        ArrayList<User> data = new ArrayList<>();
        data.add(new User(android.R.drawable.ic_menu_compass, "David", "1234"));
        data.add(new User(android.R.drawable.ic_menu_help, "0000", "9999"));
        data.add(new User(android.R.drawable.ic_menu_compass, "David", "1234"));
        data.add(new User(android.R.drawable.ic_menu_help, "0000", "9999"));
        data.add(new User(android.R.drawable.ic_menu_compass, "David", "1234"));
        data.add(new User(android.R.drawable.ic_menu_help, "0000", "9999"));
        data.add(new User(android.R.drawable.ic_menu_compass, "David", "1234"));
        data.add(new User(android.R.drawable.ic_menu_help, "0000", "9999"));
        data.add(new User(android.R.drawable.ic_menu_compass, "David", "1234"));
        data.add(new User(android.R.drawable.ic_menu_help, "0000", "9999"));

        CustomRecyclerView adapter = new CustomRecyclerView(data);
        recyclerView.setAdapter(adapter);

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


}
