package upv.dadm.cubatometro.Database;

import android.provider.ContactsContract;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

import upv.dadm.cubatometro.Lib.User;

public class DAO {
    public void insertNewUser(String username, String password) {
        DatabaseReference myRef = FirebaseIni.getInstance().getReference("Users");
        myRef.child(username).setValue(password);
    }

}
