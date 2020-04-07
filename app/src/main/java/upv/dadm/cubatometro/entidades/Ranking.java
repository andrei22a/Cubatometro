package upv.dadm.cubatometro.entidades;

public class Ranking {
    private String position;
    private String name;
    private String points;

    public Ranking(String position, String name, String points) {
        this.position = position;
        this.name = name;
        this.points = points;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }
}
