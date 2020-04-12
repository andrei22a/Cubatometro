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
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import upv.dadm.cubatometro.CreateGroupActivity;
import upv.dadm.cubatometro.entidades.Registro;
import upv.dadm.cubatometro.entidades.User;

public class DAO {

    public void insertNewUser(String userID, Registro registro, String username) {
        DatabaseReference firebaseRef = FirebaseIni.getInstance().getReference("Users").child(userID);
        firebaseRef.child("NombreUsuario").setValue(username);
        DatabaseReference newRegistroRef = firebaseRef.push();
        newRegistroRef.child("NumeroBotellas").setValue(registro.getNumBotellas());
        newRegistroRef.child("NumeroMediasBotellas").setValue(registro.getNumMediasBotellas());
        newRegistroRef.child("NumeroBotellasVino").setValue(registro.getNumBotellasVino());
        newRegistroRef.child("NumeroChupitos").setValue(registro.getNumChupitos());
        newRegistroRef.child("NumeroJarrasCerveza").setValue(registro.getNumJarrasCerveza());
        newRegistroRef.child("NumeroLatasCerveza").setValue(registro.getNumLatasCerveza());
        newRegistroRef.child("NumeroLitrosCerveza").setValue(registro.getNumLitrosCerveza());

        newRegistroRef.child("FechaRegistro").setValue(registro.getFecha());

    }

    public void getAllUsers(){
        DatabaseReference firebaseRef = FirebaseIni.getInstance().getReference("Users");
        firebaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(final DataSnapshot data : dataSnapshot.getChildren()) {
                    final String username = data.child("NombreUsuario").getValue().toString();

                    getUserProfilePics(data.getKey(), username);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static void getUserProfilePics(final String userID, final String username) {
        List<StorageReference> storageReferences = new ArrayList<>();
        storageReferences.add(FirebaseStorage.getInstance().getReference("images/users/" + userID + "/profilePic.jpg"));
        for(StorageReference storageReference : storageReferences) {
            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override //El problema es que setAdapter tiene que ejecutarse despues de la ejecucion del listener pero no se como hacerlo
                public void onSuccess(Uri uri) {
                    ImageView auxImage = new ImageView(CreateGroupActivity.getAppContext());
                    User user = new User(auxImage, username, userID);
                    String imageURL = uri.toString();
                    Picasso.get().load(imageURL).into(user.getProfilePic());

                    CreateGroupActivity.loadUser(user);
                }
            });
        }

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
