package upv.dadm.cubatometro.Lib;

import android.widget.ImageView;

import java.util.ArrayList;

public class Grupo {
    private ImageView image;
    private String name;
    private ArrayList<User> members;

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

}
