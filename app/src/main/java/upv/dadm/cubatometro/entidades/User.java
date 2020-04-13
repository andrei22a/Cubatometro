package upv.dadm.cubatometro.entidades;

import android.widget.ImageView;

import java.util.List;

public class User {
    private String userID;
    private ImageView profilePic;
    private String username;
    private List<Registro> registros;

    public User(ImageView profilePic, String name, String userID, List<Registro> registros){
        this.profilePic = profilePic;
        this.username = name;
        this.userID = userID;
        this.registros = registros;
    }

    public User(ImageView profilePic, String name, String userID){
        this.profilePic = profilePic;
        this.username = name;
        this.userID = userID;
    }

    public User(String name, String userID, List<Registro> registros){
        this.username = name;
        this.userID = userID;
        this.registros = registros;
    }

    public User(String name, String userID){
        this.username = name;
        this.userID = userID;
    }

    public User(){}




    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
    public ImageView getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(ImageView profilePic) {
        this.profilePic = profilePic;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public List<Registro> getRegistros() {
        return registros;
    }

    public void setRegistros(List<Registro> registros) {
        this.registros = registros;
    }
}
