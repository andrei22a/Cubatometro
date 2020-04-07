package upv.dadm.cubatometro.Database;

import com.google.firebase.database.DatabaseReference;

public class DAO {
    public void insertNewUser(/* imagen */ String username, String password) {
        DatabaseReference myRef = FirebaseIni.getInstance().getReference("Users");
        myRef.child(username).setValue(password);

        /* createGroup - inserta un grupo en la base de datos
            argumentos - imagen , String groupName, ArrayList<User>
            return void                                           */


        /*  userExists - comprueba si un usuario está registrado
            argumentos - String username
            return - boolean                            */


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

}
