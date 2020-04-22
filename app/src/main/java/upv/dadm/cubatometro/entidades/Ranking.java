package upv.dadm.cubatometro.entidades;

import android.widget.ImageView;

public class Ranking implements Comparable<Ranking>{
    private String name;
    private int points;

    public Ranking(String name, int points) {
        this.name = name;
        this.points = points;
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


    @Override
    public int compareTo(Ranking o) {
        if(this.points < o.points) { return 1; }
        else if (this.points == o.points) { return 0; }
        else { return -1; }

    }
}
