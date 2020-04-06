package upv.dadm.cubatometro.Lib;

public class User {
    private int image;
    private String name;
    private String password;

    public User(int image, String name, String password){
        this.image = image;
        this.name = name;
        this.password = password;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
