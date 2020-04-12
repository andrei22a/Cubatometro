package upv.dadm.cubatometro.Database;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageException;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import upv.dadm.cubatometro.CreateGroupActivity;
import upv.dadm.cubatometro.Listeners.GroupsListener;
import upv.dadm.cubatometro.Listeners.MembersIDListener;
import upv.dadm.cubatometro.Listeners.MembersListener;
import upv.dadm.cubatometro.entidades.Grupo;
import upv.dadm.cubatometro.entidades.Registro;
import upv.dadm.cubatometro.entidades.User;

public class DAO {

    public void insertNewUser(String userID, Registro registro, String username) {
        DatabaseReference firebaseRef = FirebaseIni.getInstance().getReference("Users").child(userID);
        firebaseRef.child("NombreUsuario").setValue(username);
        DatabaseReference newRegistroRef = firebaseRef.child("Registros").push();
        newRegistroRef.child("NumeroBotellas").setValue(registro.getNumBotellas());
        newRegistroRef.child("NumeroMediasBotellas").setValue(registro.getNumMediasBotellas());
        newRegistroRef.child("NumeroBotellasVino").setValue(registro.getNumBotellasVino());
        newRegistroRef.child("NumeroChupitos").setValue(registro.getNumChupitos());
        newRegistroRef.child("NumeroJarrasCerveza").setValue(registro.getNumJarrasCerveza());
        newRegistroRef.child("NumeroLatasCerveza").setValue(registro.getNumLatasCerveza());
        newRegistroRef.child("NumeroLitrosCerveza").setValue(registro.getNumLitrosCerveza());

        newRegistroRef.child("FechaRegistro").setValue(registro.getFecha());

    }

    public void insertNewGroup(String groupID, String name, List<User> members){
        for(int i = 0; i < members.size(); i++) {
            DatabaseReference memberIDRef = FirebaseIni.getInstance().getReference("Users").child(members.get(i).getUserID())
                    .child("Groups").child(groupID);
            memberIDRef.child("NombreGrupo").setValue(name);
            DatabaseReference newMembersListRef = memberIDRef.child("Miembros");
            for(int j = 0; j < members.size(); j++){
                newMembersListRef.child("Miembro " + j).setValue(members.get(j).getUserID());
            }
        }
    }

   /* public List<String> getMembersIDOfGroup(String groupID, final MembersIDListener listener) {
        final List<String> userIDs = new ArrayList<>();
        DatabaseReference groupMembersRef = FirebaseIni.getInstance().getReference("Groups").child(groupID).child("Miembros");
        groupMembersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()) {
                    userIDs.add(data.getKey());

                    listener.onMembersIDReceived(userIDs);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                listener.onError(databaseError.toException());
            }
        });

    }*/

    public void getMembersOfGroup(final List<String> memberIDs, final MembersListener callback){
        final List<User> members = new ArrayList<>();
        DatabaseReference usersRef = FirebaseIni.getInstance().getReference("Users");
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()) {
                    if(memberIDs.contains(data.getKey())){
                        members.add(new User(data.child("NombreUsuario").getValue().toString(), data.getKey()));
                    }
                }

                callback.onMembersReceived(members);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

   public void getGroups(String userID, final GroupsListener callback){
       final List<Grupo> userGroups = new ArrayList<>();
       DatabaseReference userGroupsRef = FirebaseIni.getInstance().getReference("Users").child(userID).child("Groups");
       userGroupsRef.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               for(DataSnapshot data : dataSnapshot.getChildren()){
                   final String groupID = data.getKey();
                   final String groupName = data.child("NombreGrupo").getValue().toString();
                   List<String> memberIDs = new ArrayList<>();
                   List<User> members = new ArrayList<>();

                   for(DataSnapshot userID : data.child("Miembros").getChildren()){
                       memberIDs.add(userID.getValue().toString());
                   }
                   getMembersOfGroup(memberIDs, new MembersListener() {
                       @Override
                       public void onMembersReceived(List<User> members) {
                           userGroups.add(new Grupo(null, groupName, groupID, (ArrayList<User>) members));
                       }

                       @Override
                       public void onError(Throwable error) {

                       }
                   });

               }

               callback.onGroupsReceived(userGroups);
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

    public void getUserProfilePics(final String userID,  final ImageView profilePic) {

        StorageReference storageReference = FirebaseStorage.getInstance().getReference("images/users/" + userID + "/profilePic.jpg");

        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override //El problema es que setAdapter tiene que ejecutarse despues de la ejecucion del listener pero no se como hacerlo
            public void onSuccess(Uri uri) {
                String imageURL = uri.toString();
                Picasso.get().load(imageURL).into(profilePic);
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
