package upv.dadm.cubatometro.Database;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class FirebaseIni {
    private static FirebaseDatabase firebaseDatabase;
    private static DatabaseReference databaseReference;

    private FirebaseIni(){
        this.firebaseDatabase = FirebaseDatabase.getInstance();
    }

    public static FirebaseDatabase getInstance() {
        if(firebaseDatabase == null) {
            FirebaseIni firebaseInstance = new FirebaseIni();
        }
        databaseReference = firebaseDatabase.getReference();
        return firebaseDatabase;
    }
}
