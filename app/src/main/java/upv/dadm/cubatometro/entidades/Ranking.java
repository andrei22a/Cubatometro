package upv.dadm.cubatometro.entidades;

import android.widget.ImageView;

import de.hdodenhof.circleimageview.CircleImageView;

public class Ranking implements Comparable<Ranking>{
    private String name;
    private int points;
    private String userID;
    private ImageView userIcon;

    public Ranking(String name, int points, String userID, ImageView userIcon) {
        this.name = name;
        this.points = points;
        this.userID = userID;
        this.userIcon = userIcon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public ImageView getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(CircleImageView userIcon) {
        this.userIcon = userIcon;
    }

    @Override
    public int compareTo(Ranking o) {
        if(this.points < o.points) { return 1; }
        else if (this.points == o.points) { return 0; }
        else { return -1; }

    }
}
