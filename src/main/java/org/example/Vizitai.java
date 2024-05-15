package org.example;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Vizitai {
    public int vizitoID;
    public int klientoID;
    public int paslaugosID;
    public LocalDateTime registracijosLaikas;
    public Vizitai(int klientoID, int paslaugosID, LocalDateTime registracijosLaikas) {
        this.vizitoID = 0;
        this.klientoID = klientoID;
        this.paslaugosID = paslaugosID;
        this.registracijosLaikas = registracijosLaikas;
    }

    public Vizitai(int vizitoID, int klientoID, int paslaugosID, LocalDateTime registracijosLaikas) {
        this.vizitoID = vizitoID;
        this.klientoID = klientoID;
        this.paslaugosID = paslaugosID;
        this.registracijosLaikas = registracijosLaikas;
    }

    public String toString(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = registracijosLaikas.format(formatter);
        return "Vizito ID: " + vizitoID + ", Klientas: " + klientoID + ", Paslaugos ID: " +
                paslaugosID + ", Registracijos laikas: " + formattedDateTime;
    }

}
