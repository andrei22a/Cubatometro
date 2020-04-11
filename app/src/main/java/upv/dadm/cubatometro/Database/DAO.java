package upv.dadm.cubatometro.Database;

import com.google.firebase.database.DatabaseReference;

import upv.dadm.cubatometro.entidades.Registro;

public class DAO {
    public void insertNewUser(String userID, Registro registro) {
        DatabaseReference firebaseRef = FirebaseIni.getInstance().getReference("Users").child(userID);
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
