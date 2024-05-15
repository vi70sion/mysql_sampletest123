package org.example;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public interface DuombazesValdymas {
    void naujasKlientas(Klientas klientas) throws SQLException;
    List<Klientas> klientuSarasas() throws SQLException;
    List<Klientas> paieskaKlientuSarase(String vardas, String pavard) throws SQLException;
    public void spausdintiKlientus() throws SQLException;
    public Klientas gautiKlientaPagalID(int klID) throws SQLException;
    public void naujaPaslauga(Paslauga paslauga) throws SQLException;
    public void spausdintiPaslaugas() throws SQLException;
    public Paslauga gautiPaslaugaPagalID(int klID) throws SQLException;
    public void naujasDarbuotojas(Darbuotojas darbuotojas) throws SQLException;
    public void spausdintiDarbuotojus() throws SQLException;
    public Darbuotojas gautiDarbuotojaPagalID(int klID) throws SQLException;
    public void ivestiMokejima(Klientas klientas, Darbuotojas darbuotojas, Paslauga paslauga, double suma) throws SQLException;
    public void spausdintiMokejimus() throws SQLException;
    public void registruotiVizita(Klientas klientas, Paslauga paslauga, LocalDateTime registrData) throws SQLException;
    public void pasalintiVizita(Vizitai vizitas) throws SQLException;
    public void spausdintiVizitus() throws SQLException;
    public void gautiArtimiausiaVizita() throws SQLException;
    public Vizitai gautiVizitaPagalID(int vizitID) throws SQLException;
    public Vizitai gautiVizitaPagaKlient(Klientas klientas) throws SQLException;
    public void ivestiMokejimaPagalDarbIrVizita(Darbuotojas darbuotojas, Vizitai vizitas) throws SQLException;
    public void nuskaitytiKlientusCSV(String filePath);
    public void irasytiKlientusCSV(String filePath);
    public void operacijosSuMokejimais() throws SQLException;


}
