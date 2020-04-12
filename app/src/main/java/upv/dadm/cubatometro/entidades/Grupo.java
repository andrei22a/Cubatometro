package upv.dadm.cubatometro.entidades;

import android.widget.ImageView;

import java.util.ArrayList;

public class Grupo {
    private ImageView image;
    private String name;
    private ArrayList<User> members;
    private String groupID;

    public Grupo(ImageView image, String name, ArrayList<User> members){
        this.image = image;
        this.name = name;
        this.members = members;
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<User> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<User> members) {
        this.members = members;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }
}
