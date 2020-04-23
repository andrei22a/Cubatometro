package upv.dadm.cubatometro.entidades;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class Registro {
    private int numBotellas;
    private int numMediasBotellas;
    private int numJarrasCerveza;
    private int numLitrosCerveza;
    private int numBotellasVino;
    private int numLatasCerveza;
    private int numChupitos;

    private String fecha;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Registro(){
        this.numBotellas = 0;
        this.numBotellasVino = 0;
        this.numChupitos = 0;
        this.numJarrasCerveza = 0;
        this.numLitrosCerveza = 0;
        this.numMediasBotellas = 0;
        this.numLatasCerveza = 0;

        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        this.fecha = localDate.getDayOfMonth() + "/" + localDate.getMonthValue() + "/" + localDate.getYear();

    }

    public int getNumBotellas() {
        return numBotellas;
    }

    public int getNumMediasBotellas() {
        return numMediasBotellas;
    }

    public int getNumJarrasCerveza() {
        return numJarrasCerveza;
    }

    public int getNumLitrosCerveza() {
        return numLitrosCerveza;
    }

    public int getNumBotellasVino() {
        return numBotellasVino;
    }

    public int getNumLatasCerveza() {
        return numLatasCerveza;
    }

    public int getNumChupitos() {
        return numChupitos;
    }

    public String getFecha() {
        return fecha;
    }

    public void setNumBotellas(int numBotellas) {
        this.numBotellas = numBotellas;
    }

    public void setNumMediasBotellas(int numMediasBotellas) {
        this.numMediasBotellas = numMediasBotellas;
    }

    public void setNumJarrasCerveza(int numJarrasCerveza) {
        this.numJarrasCerveza = numJarrasCerveza;
    }

    public void setNumLitrosCerveza(int numLitrosCerveza) {
        this.numLitrosCerveza = numLitrosCerveza;
    }

    public void setNumBotellasVino(int numBotellasVino) {
        this.numBotellasVino = numBotellasVino;
    }

    public void setNumLatasCerveza(int numLatasCerveza) {
        this.numLatasCerveza = numLatasCerveza;
    }

    public void setNumChupitos(int numChupitos) {
        this.numChupitos = numChupitos;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
