package upv.dadm.cubatometro.entidades;

import java.util.Calendar;

public class Registro {
    private int numBotellas;
    private int numMediasBotellas;
    private int numJarrasCerveza;
    private int numLitrosCerveza;
    private int numBotellasVino;
    private int numLatasCerveza;
    private int numChupitos;

    private String fecha;

    public Registro(){
        this.numBotellas = 0;
        this.numBotellasVino = 0;
        this.numChupitos = 0;
        this.numJarrasCerveza = 0;
        this.numLitrosCerveza = 0;
        this.numMediasBotellas = 0;
        this.numLatasCerveza = 0;

        Calendar fechaActual = Calendar.getInstance();
        this.fecha = Integer.toString(fechaActual.get(Calendar.DATE)) + "/" + Integer.toString(fechaActual.get(Calendar.MONTH)) + "/" + Integer.toString(fechaActual.get(Calendar.YEAR));

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
