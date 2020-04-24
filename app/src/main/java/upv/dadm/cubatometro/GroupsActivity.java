package upv.dadm.cubatometro;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import upv.dadm.cubatometro.Database.DAO;
import upv.dadm.cubatometro.Listeners.MiembrosConRegistroListener;
import upv.dadm.cubatometro.Listeners.OnItemClickListener;
import upv.dadm.cubatometro.Listeners.OnItemLongClickListener;
import upv.dadm.cubatometro.Listeners.GroupsListener;
import upv.dadm.cubatometro.adapter.DividerItemDecoration;
import upv.dadm.cubatometro.adapter.ListGroupsAdapter;
import upv.dadm.cubatometro.entidades.Grupo;
import upv.dadm.cubatometro.entidades.User;

public class GroupsActivity extends AppCompatActivity {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private long backPressedTime;
    private ListGroupsAdapter adapter;
    private RecyclerView recyclerView;
    private ArrayList<Grupo> data = new ArrayList<>();
    private OnItemClickListener clickListener;
    private OnItemLongClickListener longClickListener;
    private DAO dao = new DAO();
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);

        mAuth = FirebaseAuth.getInstance();
        preferences = getSharedPreferences("groupDetails", MODE_PRIVATE);
        editor = preferences.edit();

        progressBar = findViewById(R.id.progressBar_groups);
        progressBar.setVisibility(View.VISIBLE);

        /* Listener que cambia de actividad cuando se pulsa sobre un grupo */
        clickListener = new OnItemClickListener() {
            @Override
            public void onClickListener(final int position) {
                String groupID = data.get(position).getGroupID();
                editor.putString("groupID", groupID);
                editor.apply();
                Intent intent = new Intent(GroupsActivity.this, RankingActivity.class);
                intent.putExtra("groupID", groupID);
                startActivity(intent);
            }
        };

        /* Listener que permite borrar un grupo cuando se mantiene pulsado sobre él */
        longClickListener = new OnItemLongClickListener() {
            @Override
            final public void onLongClickListener(int position) {
                showDeleteDialog(position);
            }
        };
        recyclerView = findViewById(R.id.recyclerview_groups);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this));

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        dao.getGroups(mAuth.getCurrentUser().getUid(), new GroupsListener() {
            @Override
            public void onGroupsReceived(List<Grupo> grupos) {
                progressBar.setVisibility(View.GONE);
                data = (ArrayList<Grupo>) grupos;
                adapter = new ListGroupsAdapter(data, clickListener, longClickListener);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onError(Throwable error) {

            }
        });


    }

    @Override
    public void onBackPressed() {
        if ((backPressedTime + 2000) > System.currentTimeMillis()){
            finishAffinity();
            return;
        } else {
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        backPressedTime = System.currentTimeMillis();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_create_group:
                startActivity(new Intent(GroupsActivity.this, CreateGroupActivity.class));
                return true;

            case R.id.menu_logout:
                startActivity(new Intent(GroupsActivity.this, LoginActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }



    public void showDeleteDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(GroupsActivity.this);
        builder.setMessage(R.string.dialog_message);

        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                /******* Borrar el grupo de firebase *******/
                dao.getMiembrosConRegistros(mAuth.getCurrentUser().getUid(), data.get(position).getGroupID(), new MiembrosConRegistroListener() {
                    @Override
                    public void onMiembrosReceived(List<User> miembros) {
                        List<String> memberIDs = new ArrayList<>();
                        for(User miembro : miembros){
                            memberIDs.add(miembro.getUserID());
                        }
                        dao.deleteGroup(mAuth.getCurrentUser().getUid(), memberIDs, data.get(position).getGroupID());
                        adapter.notifyItemRemoved(position);
                    }

                    @Override
                    public void onError(Throwable error) {

                    }
                });

            }
        });

        builder.setNegativeButton(R.string.no, null);

        builder.create().show();
    }
}
