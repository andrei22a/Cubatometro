package upv.dadm.cubatometro.Database;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;
import upv.dadm.cubatometro.CreateGroupActivity;
import upv.dadm.cubatometro.Listeners.GroupsListener;
import upv.dadm.cubatometro.Listeners.ImageListener;
import upv.dadm.cubatometro.Listeners.MiembrosConRegistroListener;
import upv.dadm.cubatometro.entidades.Grupo;
import upv.dadm.cubatometro.entidades.Registro;
import upv.dadm.cubatometro.entidades.User;

public class DAO {

    public void insertNewUser(String userID, String username) {
        DatabaseReference firebaseRef = FirebaseIni.getInstance().getReference("Users").child(userID);
        firebaseRef.child("NombreUsuario").setValue(username);


    }

    public void insertNewGroup(String groupID, String name, List<User> members, Registro registroInicial){
        for(int i = 0; i < members.size(); i++) {
            DatabaseReference memberIDRef = FirebaseIni.getInstance().getReference("Users").child(members.get(i).getUserID())
                    .child("Groups").child(groupID);
            memberIDRef.child("NombreGrupo").setValue(name);
            DatabaseReference newMembersListRef = memberIDRef.child("Miembros");
            for(int j = 0; j < members.size(); j++){
                newMembersListRef.child(members.get(j).getUserID()).child("NombreUsuario").setValue(members.get(j).getUsername());
                DatabaseReference newMemberRef = newMembersListRef.child(members.get(j).getUserID()).child("Registros").push();

                newMemberRef.child("NumeroBotellas").setValue(registroInicial.getNumBotellas());
                newMemberRef.child("NumeroMediasBotellas").setValue(registroInicial.getNumMediasBotellas());
                newMemberRef.child("NumeroBotellasVino").setValue(registroInicial.getNumBotellasVino());
                newMemberRef.child("NumeroChupitos").setValue(registroInicial.getNumChupitos());
                newMemberRef.child("NumeroJarrasCerveza").setValue(registroInicial.getNumJarrasCerveza());
                newMemberRef.child("NumeroLatasCerveza").setValue(registroInicial.getNumLatasCerveza());
                newMemberRef.child("NumeroLitrosCerveza").setValue(registroInicial.getNumLitrosCerveza());

                newMemberRef.child("FechaRegistro").setValue(registroInicial.getFecha());
            }
        }
    }

   public void getGroups(String userID, final GroupsListener callback){
       final List<Grupo> userGroups = new ArrayList<>();
       final List<User> members = new ArrayList<>();
       DatabaseReference userGroupsRef = FirebaseIni.getInstance().getReference("Users").child(userID).child("Groups");
       userGroupsRef.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               for(DataSnapshot data : dataSnapshot.getChildren()){
                   final String groupID = data.getKey();
                   final String groupName = data.child("NombreGrupo").getValue().toString();

                   userGroups.add(new Grupo(null, groupName, groupID));
               }
               callback.onGroupsReceived(userGroups);
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });
   }

   public void getMiembrosConRegistros(String userID, String groupID, final MiembrosConRegistroListener callback){
        final List<Registro> registros = new ArrayList<>();
        final List<User> miembrosConRegistro = new ArrayList<>();
        final DatabaseReference groupMembersRef = FirebaseIni.getInstance().getReference("Users").child(userID).child("Groups").child(groupID).child("Miembros");
        groupMembersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot userID : dataSnapshot.getChildren()){
                    final User member = new User();
                    member.setUserID(userID.toString());
                    member.setUsername(userID.child("NombreUsuario").getValue().toString());

                    for(DataSnapshot registrosID : userID.child("Registros").getChildren()) {
                        Registro registro = new Registro();

                        registro.setFecha(registrosID.child("FechaRegistro").getValue().toString());
                        registro.setNumBotellas(Integer.valueOf(registrosID.child("NumeroBotellas").getValue().toString()));
                        registro.setNumBotellasVino(Integer.valueOf(registrosID.child("NumeroBotellasVino").getValue().toString()));
                        registro.setNumChupitos(Integer.valueOf(registrosID.child("NumeroChupitos").getValue().toString()));
                        registro.setNumJarrasCerveza(Integer.valueOf(registrosID.child("NumeroJarrasCerveza").getValue().toString()));
                        registro.setNumLatasCerveza(Integer.valueOf(registrosID.child("NumeroLatasCerveza").getValue().toString()));
                        registro.setNumLitrosCerveza(Integer.valueOf(registrosID.child("NumeroLitrosCerveza").getValue().toString()));
                        registro.setNumMediasBotellas(Integer.valueOf(registrosID.child("NumeroMediasBotellas").getValue().toString()));
                        registros.add(registro);
                    }
                    member.setRegistros(registros);
                    miembrosConRegistro.add(member);
                }
                callback.onMiembrosReceived(miembrosConRegistro);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
   }

    public void getAllUsers(){
        DatabaseReference firebaseRef = FirebaseIni.getInstance().getReference("Users");
        firebaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(final DataSnapshot data : dataSnapshot.getChildren()) {
                    final String username = data.child("NombreUsuario").getValue().toString();

                    User user = new User(null, username, data.getKey());
                    CreateGroupActivity.loadUser(user);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getUserProfilePics(final String userID, final ImageView profilePic, final ImageListener callback) {

        StorageReference storageReference = FirebaseStorage.getInstance().getReference("images/users/" + userID + "/profilePic.jpg");

        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override //El problema es que setAdapter tiene que ejecutarse despues de la ejecucion del listener pero no se como hacerlo
            public void onSuccess(Uri uri) {
                String imageURI = uri.toString();
                callback.onImageReceived(imageURI);
            }
        });
    }

    public void getGroupPic(final String groupID, final ImageListener callback) {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference("images/groups/" + groupID + "/groupPic.jpg");

        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String imageURI = uri.toString();
                callback.onImageReceived(imageURI);
            }
        });
    }
        /* createGroup - inserta un grupo en la base de datos
            argumentos - imagen , String groupName, ArrayList<User>
            return void                                           */


        /*  getUser - devuelve el usuario asociado a ese nombre
            argumentos - String username
            return - User                               */


        /* getAllUsers - devuelve una lista con todos los usuarios registrados
            return - ArrayList<User>                    */



        /* getUserGroups - devuelve una lista con los grupos asociados a ese usuario
            argumentos - User user
            return - ArrayList<Grupo>
                                                        */


        /* getGroupMembers - devuelve una lista con los usuarios que forman parte de un grupo
            argumentos - String groupName
            return - ArrayList<User>                    */




}
