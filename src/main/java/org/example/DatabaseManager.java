package org.example;

import java.io.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DatabaseManager implements DuombazesValdymas {
    private Connection _connection;
    public DatabaseManager(String URL, String USERNAME, String PASSWORD) throws SQLException {
        _connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

    }

    @Override
    public void naujasKlientas(Klientas klientas) throws SQLException {
        if(klientas == null){
            System.out.println("Nerastas klientas.");
            return;
        }
        String sql = "INSERT INTO klientai (vardas, pavarde, gimimo_data, registracijos_data, vip) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement statement = _connection.prepareStatement(sql);
        klientas.registracijosData = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        statement.setString(1, klientas.vardas);
        statement.setString(2, klientas.pavarde);
        statement.setString(3, klientas.gimimoData);
        statement.setString(4, klientas.registracijosData);
        statement.setBoolean(5, klientas.vip);

        int rowsInserted = statement.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("Klientas sėkmingai įregistruotas.");
        } else {
            System.out.println("Kliento registracija nepavyko.");
        }
    }

    @Override
    public List<Klientas> klientuSarasas() throws SQLException {
        String sql = "SELECT * FROM klientai";
        PreparedStatement statement = _connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        List<Klientas> klientuSarasas = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String vardas = resultSet.getString("vardas");
            String pavarde = resultSet.getString("pavarde");
            String gimimoData = resultSet.getString("gimimo_data");
            String registracijosData = resultSet.getString("registracijos_data");
            boolean vip = resultSet.getBoolean("vip");
            Klientas klientas = new Klientas(id,vardas,pavarde,gimimoData,registracijosData,vip);
            klientuSarasas.add(klientas);
        }
        return klientuSarasas;
    }

    @Override
    public List<Klientas> paieskaKlientuSarase(String vard, String pavard) throws SQLException {
        String sql = "SELECT * FROM klientai WHERE vardas LIKE ? OR pavarde LIKE ?";
        PreparedStatement statement = _connection.prepareStatement(sql);
        statement.setString(1, "%" + vard + "%");
        statement.setString(2, "%" + pavard + "%");
        ResultSet resultSet = statement.executeQuery();
        List<Klientas> klientuSarasas = new ArrayList<>();

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String vardas = resultSet.getString("vardas");
            String pavarde = resultSet.getString("pavarde");
            String gimimoData = resultSet.getString("gimimo_data");
            String registracijosData = resultSet.getString("registracijos_data");
            boolean vip = resultSet.getBoolean("vip");
            Klientas klientas = new Klientas(id,vardas,pavarde,gimimoData,registracijosData,vip);
            klientuSarasas.add(klientas);
        }
        return klientuSarasas;
    }

    @Override
    public Klientas gautiKlientaPagalID(int klID) throws SQLException {
        String sql = "SELECT * FROM klientai WHERE id = ?";
        PreparedStatement statement = _connection.prepareStatement(sql);
        statement.setInt(1, klID);
        ResultSet resultSet = statement.executeQuery();
        boolean hasResults = resultSet.next();
        if(!hasResults){
            System.out.println("Pagal pateiktą ID klientų nerasta.");
            return null;
        }
        int id = resultSet.getInt("id");
        String vardas = resultSet.getString("vardas");
        String pavarde = resultSet.getString("pavarde");
        String gimimoData = resultSet.getString("gimimo_data");
        String registracijosData = resultSet.getString("registracijos_data");
        boolean vip = resultSet.getBoolean("vip");
        Klientas klientas = new Klientas(id,vardas,pavarde,gimimoData,registracijosData,vip);
        return klientas;
    }

    @Override
    public void spausdintiKlientus() throws SQLException {
        String sql = "SELECT * FROM klientai";
        PreparedStatement statement = _connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String klVardas = resultSet.getString("vardas");
            String klPavarde = resultSet.getString("pavarde");
            String klGimData = resultSet.getString("gimimo_data");
            System.out.println("ID: " + id + ", Vardas: " + klVardas + ", Pavardė: " + klPavarde + ", Gimimo data: "+ klGimData);
        }
    }

    @Override
    public void naujaPaslauga(Paslauga paslauga) throws SQLException {
        if(paslauga == null){
            System.out.println("Nerasta paslauga.");
            return;
        }
        String sql = "INSERT INTO paslaugos (pavadinimas) VALUES (?)";
        PreparedStatement statement = _connection.prepareStatement(sql);
        statement.setString(1, paslauga.paslaugosPavadinimas);
        int rowsInserted = statement.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("Paslauga sėkmingai įregistruotas.");
        } else {
            System.out.println("Paslaugos registracija nepavyko.");
        }
    }

    @Override
    public void spausdintiPaslaugas() throws SQLException {
        String sql = "SELECT * FROM paslaugos";
        PreparedStatement statement = _connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            int id = resultSet.getInt("paslaugos_id");
            String pavadinimas = resultSet.getString("pavadinimas");
            System.out.println("ID: " + id + ", Paslaugos pavadinimas: " + pavadinimas);
        }
    }

    @Override
    public Paslauga gautiPaslaugaPagalID(int klID) throws SQLException {
        String sql = "SELECT * FROM paslaugos WHERE paslaugos_id = ?";
        PreparedStatement statement = _connection.prepareStatement(sql);
        statement.setInt(1, klID);
        ResultSet resultSet = statement.executeQuery();
        boolean hasResults = resultSet.next();
        if(!hasResults){
            System.out.println("Pagal pateiktą ID paslaugų nerasta.");
            return null;
        }
        int id = resultSet.getInt("paslaugos_id");
        String vardPavard = resultSet.getString("pavadinimas");
        Paslauga paslauga = new Paslauga(id,vardPavard);
        return paslauga;
    }

    @Override
    public void naujasDarbuotojas(Darbuotojas darbuotojas) throws SQLException {
        if(darbuotojas == null){
            System.out.println("Nerastas darbuotojas.");
            return;
        }
        String sql = "INSERT INTO darbuotojai (vardas_pavarde) VALUES (?)";
        PreparedStatement statement = _connection.prepareStatement(sql);
        statement.setString(1, darbuotojas.darbVardasPavarde);
        int rowsInserted = statement.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("Darbuotojas sėkmingai įregistruotas.");
        } else {
            System.out.println("Darbuotojo registracija nepavyko.");
        }
    }

    @Override
    public void spausdintiDarbuotojus() throws SQLException {
        String sql = "SELECT * FROM darbuotojai";
        PreparedStatement statement = _connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            int id = resultSet.getInt("darbuotojo_id");
            String pavadinimas = resultSet.getString("vardas_pavarde");
            System.out.println("ID: " + id + ", Vardas Pavardė: " + pavadinimas);
        }
    }

    @Override
    public Darbuotojas gautiDarbuotojaPagalID(int klID) throws SQLException {
        String sql = "SELECT * FROM darbuotojai WHERE darbuotojo_id = ?";
        PreparedStatement statement = _connection.prepareStatement(sql);
        statement.setInt(1, klID);
        ResultSet resultSet = statement.executeQuery();
        boolean hasResults = resultSet.next();
        if(!hasResults){
            System.out.println("Pagal pateiktą ID darbuotojų nerasta.");
            return null;
        }
        int id = resultSet.getInt("darbuotojo_id");
        String vardPavard = resultSet.getString("vardas_pavarde");
        Darbuotojas darbuotojas = new Darbuotojas(id,vardPavard);
        return darbuotojas;
    }

    @Override
    public void ivestiMokejima(Klientas klientas, Darbuotojas darbuotojas, Paslauga paslauga, double suma) throws SQLException {
        if(klientas == null || darbuotojas == null || paslauga == null){
            System.out.println("Nerastas klientas arba darbuotojas, arba paslauga.");
            return;
        }
        if(klientas.vip){
            suma = suma * 0.75;
        }
        String sql = "INSERT INTO mokejimai (kliento_id, darbuotojo_id, paslaugos_id, mokejimo_suma) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = _connection.prepareStatement(sql);
        statement.setInt(1, klientas.id);
        statement.setInt(2, darbuotojas.darbuotojoId);
        statement.setInt(3, paslauga.id);
        statement.setDouble(4, suma);
        int rowsInserted = statement.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("Mokėjimas sėkmingai įregistruotas.");
        } else {
            System.out.println("Mokėjimo registracija nepavyko.");
        }
    }

    @Override
    public void spausdintiMokejimus() throws SQLException {
        String sql = "SELECT * FROM mokejimai";
        PreparedStatement statement = _connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            int mokID = resultSet.getInt("mokejimo_id");
            int klID = resultSet.getInt("kliento_id");
            int darbID = resultSet.getInt("darbuotojo_id");
            int paslID = resultSet.getInt("paslaugos_id");
            double suma = resultSet.getDouble("mokejimo_suma");
            System.out.println("Mokėjimo ID: " + mokID + ", Kliento ID: " + klID + ", Darbuotojo ID: " + darbID +
                    ", Paslaugos ID:" + paslID + ", Mokėjimo suma: " + suma);
        }
    }

    @Override
    public void registruotiVizita(Klientas klientas, Paslauga paslauga, LocalDateTime registrData) throws SQLException {
        if(klientas == null || paslauga == null){
            System.out.println("Nerastas klientas arba paslauga.");
            return;
        }
        String sql = "INSERT INTO vizitai (kliento_id, paslaugos_id, rezervuotas_laikas) VALUES (?, ?, ?)";
        PreparedStatement statement = _connection.prepareStatement(sql);
        statement.setInt(1, klientas.id);
        statement.setInt(2, paslauga.id);
        statement.setString(3, registrData.toString());
        int rowsInserted = statement.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("Vizitas sėkmingai įregistruotas.");
        } else {
            System.out.println("Vizito registracija nepavyko.");
        }
    }

    @Override
    public void pasalintiVizita(Vizitai vizitas) throws SQLException {
        if(vizitas == null){
            System.out.println("Nerastas vizitas.");
            return;
        }
        String sql = "DELETE FROM vizitai WHERE vizito_id = ?";
        PreparedStatement statement = _connection.prepareStatement(sql);
        statement.setInt(1, vizitas.vizitoID);
        int rowsDeleted = statement.executeUpdate();
        if (rowsDeleted > 0) {
            System.out.println("Vizitas sėkmingai pašalintas.");
        } else {
            System.out.println("Vizito pašalinti nepavyko.");
        }
    }

    @Override
    public void spausdintiVizitus() throws SQLException {
        String sql = "SELECT * FROM vizitai";
        PreparedStatement statement = _connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            int visitoID = resultSet.getInt("vizito_id");
            int klientoID = resultSet.getInt("kliento_id");
            int paslID = resultSet.getInt("paslaugos_id");
            String rezData = resultSet.getString("rezervuotas_laikas");
            System.out.println("Vizito ID: " + visitoID + ", Kliento ID: " + klientoID +
                    ", Paslaugos ID:" + paslID + ", Rezervavimo data: " + rezData);
        }
    }

    @Override
    public void gautiArtimiausiaVizita() throws SQLException {
        String sql = "SELECT * FROM vizitai " +
                "ORDER BY ABS(TIMESTAMPDIFF(SECOND, rezervuotas_laikas, NOW())) LIMIT 1";
        PreparedStatement statement = _connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        boolean hasResults = resultSet.next();
        if(!hasResults){
            System.out.println("Nerasta rezervuotų vizitų.");
            return;
        }
        int visitoID = resultSet.getInt("vizito_id");
        int klientoID = resultSet.getInt("kliento_id");
        int paslID = resultSet.getInt("paslaugos_id");
        String rezData = resultSet.getString("rezervuotas_laikas");
        System.out.println("Artimiausias vizitas: ");
        System.out.println("Vizito ID: " + visitoID + ", \nKlientas: " + gautiKlientaPagalID(klientoID) + ", \nPaslauga : " +
                gautiPaslaugaPagalID(paslID) + " \nRezervuotas laikas: " + rezData);
    }

    @Override
    public Vizitai gautiVizitaPagalID(int vizitID) throws SQLException {
        String sql = "SELECT * FROM vizitai WHERE vizito_id = ?";
        PreparedStatement statement = _connection.prepareStatement(sql);
        statement.setInt(1,vizitID);
        ResultSet resultSet = statement.executeQuery();
        boolean hasResults = resultSet.next();
        if(!hasResults){
            System.out.println("Pagal pateiktą ID rezervuotų vizitų nerasta.");
            return null;
        }
        int visitoID = resultSet.getInt("vizito_id");
        int klientoID = resultSet.getInt("kliento_id");
        int paslID = resultSet.getInt("paslaugos_id");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String data = resultSet.getString("rezervuotas_laikas");
        LocalDateTime vizitoData = LocalDateTime.parse(data, formatter);

        Vizitai vizitas = new Vizitai(visitoID, klientoID, paslID, vizitoData);
        return vizitas;
    }

    @Override
    public Vizitai gautiVizitaPagaKlient(Klientas klientas) throws SQLException {
        if(klientas == null){
            System.out.println("Nerastas klientas.");
            return null;
        }
        String sql = "SELECT * FROM vizitai\n" +
                "WHERE kliento_id = ? \n" +
                "ORDER BY ABS(TIMESTAMPDIFF(SECOND, rezervuotas_laikas, NOW()))\n" +
                "LIMIT 1";
        PreparedStatement statement = _connection.prepareStatement(sql);
        statement.setInt(1,klientas.id);
        ResultSet resultSet = statement.executeQuery();
        boolean hasResults = resultSet.next();
        if(!hasResults){
            System.out.println("Šiam klientui nerasta rezervuotų vizitų.");
            return null;
        }
        int vizitoID = resultSet.getInt("vizito_id");
        int klientoID = resultSet.getInt("kliento_id");
        int paslID = resultSet.getInt("paslaugos_id");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String data = resultSet.getString("rezervuotas_laikas");
        LocalDateTime vizitoData = LocalDateTime.parse(data, formatter);

        Vizitai vizitas = new Vizitai(vizitoID, klientoID, paslID, vizitoData);
        return vizitas;
    }

    @Override
    public void ivestiMokejimaPagalDarbIrVizita(Darbuotojas darbuotojas, Vizitai vizitas) throws SQLException {
        if(darbuotojas == null || vizitas == null){
            System.out.println("Nerastas darbuotojas arba vizitas.");
            return;
        }
        String sql = "SELECT * FROM vizitai AS v\n" +
                "INNER JOIN paslaugos_kaina AS p ON p.paslaugos_id = v.paslaugos_id\n" +
                "WHERE v.vizito_id = ?";
        PreparedStatement statement = _connection.prepareStatement(sql);
        statement.setInt(1, vizitas.vizitoID);
        ResultSet resultSet = statement.executeQuery();
        boolean hasResults = resultSet.next();
        if(!hasResults){
            System.out.println("Įvesti nepavyko. Šiam klientui nerasta rezervuotų vizitų arba nėra priskirta paslaugos kaina.");
            return;
        }
        //int visitoID = resultSet.getInt("vizito_id");
        int klientoID = resultSet.getInt("kliento_id");
        int paslID = resultSet.getInt("paslaugos_id");
        double paslKaina = resultSet.getDouble("paslaugos_kaina");
        ivestiMokejima(gautiKlientaPagalID(klientoID), darbuotojas, gautiPaslaugaPagalID(paslID), paslKaina);
    }
    @Override
    public void nuskaitytiKlientusCSV(String filePath) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
            Klientas klientas;
            String line;
            while((line = bufferedReader.readLine()) != null){
                String[] lineValues = line.split(",");
                klientas = new Klientas(lineValues[0], lineValues[1], lineValues[2],null,
                        Boolean.parseBoolean(lineValues[3]));
                naujasKlientas(klientas);
            }
            bufferedReader.close();
        } catch (IOException e){
            System.err.println("Nepavyko skaityti failo: " + e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void irasytiKlientusCSV(String filePath) {
        try{
            FileWriter fileWriter = new FileWriter(filePath, false);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            List<Klientas> klietuList = klientuSarasas();
            for(Klientas item: klietuList){
                bufferedWriter.write(item.id + "," + item.vardas + "," + item.pavarde + "," +
                        item.gimimoData + "," + item.registracijosData + "," + item.vip);
                bufferedWriter.newLine();
            }
            System.out.println("Eksportuota sėkmingai.");
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e){
            System.err.println("Nepavyko įrašyti į failą: " + e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void operacijosSuMokejimais() throws SQLException {
        String sql = "SELECT SUM(mokejimo_suma) AS suma FROM mokejimai";
        PreparedStatement statement = _connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        System.out.println("Visų mokėjimų suma yra: " + resultSet.getDouble("suma"));
        System.out.println();

        sql = "SELECT * FROM mydatabase.mokejimai ORDER BY mokejimo_suma ASC LIMIT 1";
        statement = _connection.prepareStatement(sql);
        resultSet = statement.executeQuery();
        resultSet.next();
        int mokID = resultSet.getInt("mokejimo_id");
        int klID = resultSet.getInt("kliento_id");
        int darbID = resultSet.getInt("darbuotojo_id");
        int paslID = resultSet.getInt("paslaugos_id");
        double suma = resultSet.getDouble("mokejimo_suma");
        System.out.println("Mažiausias mokėjimas yra:");
        System.out.println("Mokėjimo ID: " + mokID + ", Kliento ID: " + klID + ", Darbuotojo ID: " + darbID +
                ", Paslaugos ID:" + paslID + ", Mokėjimo suma: " + suma);
        System.out.println();

        sql = "SELECT * FROM mydatabase.mokejimai ORDER BY mokejimo_suma DESC LIMIT 1";
        statement = _connection.prepareStatement(sql);
        resultSet = statement.executeQuery();
        resultSet.next();
        mokID = resultSet.getInt("mokejimo_id");
        klID = resultSet.getInt("kliento_id");
        darbID = resultSet.getInt("darbuotojo_id");
        paslID = resultSet.getInt("paslaugos_id");
        suma = resultSet.getDouble("mokejimo_suma");
        System.out.println("Didžiausias mokėjimas yra:");
        System.out.println("Mokėjimo ID: " + mokID + ", Kliento ID: " + klID + ", Darbuotojo ID: " + darbID +
                ", Paslaugos ID:" + paslID + ", Mokėjimo suma: " + suma);
        System.out.println();



        /*
        String sql = "SELECT * FROM klientai";
        PreparedStatement statement = _connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        List<Klientas> klientuSarasas = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String vardas = resultSet.getString("vardas");
            String pavarde = resultSet.getString("pavarde");
            String gimimoData = resultSet.getString("gimimo_data");
            String registracijosData = resultSet.getString("registracijos_data");
            boolean vip = resultSet.getBoolean("vip");
            Klientas klientas = new Klientas(id,vardas,pavarde,gimimoData,registracijosData,vip);
            klientuSarasas.add(klientas);
        }*/
    }
}


