package org.example;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {

        Scanner scanner = new Scanner(System.in);
        final String URL = "jdbc:mysql://localhost:3306/mydatabase";
        final String USERNAME = "root";
        System.out.println("Įveskite MySQL slaptažodį: ");
        final String PASSWORD = scanner.nextLine();
        DatabaseManager dbManager = new DatabaseManager(URL, USERNAME, PASSWORD);


        String filePathForImportCSV = "C:\\JavaTest\\KlientaiImportui.csv";
        String filePathForExportCSV = "C:\\JavaTest\\KlientaiEksportui.csv";

        String choice = "";
        do {
            System.out.println("(1)- naujo kliento įvedimas, (2)- nuskaitome visų klientų sąrašą į List,");
            System.out.println("(3)- ieškoti kliento pagal vardą ir pavardę, grąžina List<Klientas> atitinkantį kriterijus,");
            System.out.println("(4)- darbuotojo įvedimas, (5)- paslaugos įvedimas, (6)- mokėjimo įvedimas,");
            System.out.println("(7)- registruoti vizitą, (8)- gauti artmiausią vizitą, (9)- gauti vizitą pagal vizito ID,");
            System.out.println("(10)- gauti artimiausią vizitą pagal klientą, (11)- registruoti mokėjimą pagal vizitą ir darbuotoją,");
            System.out.println("(12)- atšaukti vizitą, (13)- eksportuoti klientus į CSV, (14)- importuoti klientus iš CSV, (0)- pabaiga");
            System.out.println("(15)- visų mokėjimų suma, mažiausias ir didžiausias mokėjimas, (0)- pabaiga");
            choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    //naujo kliento įvedimas
                    dbManager.naujasKlientas(sukurtiKlienta());
                    break;
                case "2":
                    //nuskaitome visų klientų sąrašą į List
                    List<Klientas> visuKlientuSarasas = dbManager.klientuSarasas();
                    spausdintiList(visuKlientuSarasas);
                    System.out.println();
                    break;
                case "3":
                    //ieškoti kliento pagal vardą ir pavardę, grąžina List<Klientas> atitinkantį kriterijus
                    String vardas = "Jonas"; String pavarde = "Jurgelis";  //Ieškomesime pagal šiuos kriterijus, be įvedimo iš konsolės
                    System.out.println("Vardas- " + vardas + " arba Pavardė- " + pavarde + " atitinka:");
                    List<Klientas> rastuKlientuSarasas = dbManager.paieskaKlientuSarase(vardas, pavarde);
                    spausdintiList(rastuKlientuSarasas);
                    System.out.println();
                    break;
                case "4":
                    //darbuotojo įvedimas
                    dbManager.naujasDarbuotojas(sukurtiDarbuotoja());
                    break;
                case "5":
                    //paslaugos įvedimas
                    dbManager.naujaPaslauga(sukurtiPaslauga());
                    break;
                case "6":
                    //mokėjimo įvedimas
                    dbManager.spausdintiDarbuotojus();
                    System.out.println("Įveskite darbuotojo ID: ");
                    int pasirinktasDarb = Integer.parseInt(scanner.nextLine());
                    dbManager.spausdintiKlientus();
                    System.out.println("Įveskite kliento ID: ");
                    int pasirinktasKl = Integer.parseInt(scanner.nextLine());
                    dbManager.spausdintiPaslaugas();
                    System.out.println("Įveskite paslaugos ID: ");
                    int pasirinktaPasl = Integer.parseInt(scanner.nextLine());
                    System.out.println("Įveskite mokėtiną sumą: ");
                    double suma = Double.parseDouble(scanner.nextLine());
                    dbManager.ivestiMokejima(dbManager.gautiKlientaPagalID(pasirinktasKl), dbManager.gautiDarbuotojaPagalID(pasirinktasDarb),
                            dbManager.gautiPaslaugaPagalID(pasirinktaPasl), suma);
                    dbManager.spausdintiMokejimus();
                    break;
                case "7":
                    //registruoti vizitą
                    dbManager.spausdintiKlientus();
                    System.out.println("Įveskite kliento ID: ");
                    int pasKl = Integer.parseInt(scanner.nextLine());
                    dbManager.spausdintiPaslaugas();
                    System.out.println("Įveskite paslaugos ID: ");
                    int pasPasl = Integer.parseInt(scanner.nextLine());
                    System.out.println("Įveskite pageidaujamą vizito datą ir laiką ( mmmm-MM-dd VV:mm ):");
                    String ivestaData = scanner.nextLine();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                    LocalDateTime vizitoData = LocalDateTime.parse(ivestaData, formatter);
                    dbManager.registruotiVizita(dbManager.gautiKlientaPagalID(pasKl),
                            dbManager.gautiPaslaugaPagalID(pasPasl), vizitoData);
                    break;
                case "8":
                    //gauti artmiausią vizitą
                    dbManager.gautiArtimiausiaVizita();
                    break;
                case "9":
                    //gauti vizitą pagal vizito ID
                    System.out.println("Įveskite vizito ID: ");
                    int vizID = Integer.parseInt(scanner.nextLine());
                    System.out.println(dbManager.gautiVizitaPagalID(vizID));
                    break;
                case "10":
                    //gauti artimiausią vizitą pagal klientą
                    dbManager.spausdintiKlientus();
                    System.out.println("Įveskite kliento ID: ");
                    int parKl = Integer.parseInt(scanner.nextLine());
                    System.out.println("Vizito ID: " + dbManager.gautiVizitaPagaKlient(dbManager.gautiKlientaPagalID(parKl)).vizitoID +
                            ", \nKlientas: " + dbManager.gautiKlientaPagalID(parKl) + ", \nPaslauga : " +
                            dbManager.gautiPaslaugaPagalID(dbManager.gautiVizitaPagaKlient(dbManager.gautiKlientaPagalID(parKl)).paslaugosID) +
                            " \nRezervuotas laikas: " +
                            dbManager.gautiVizitaPagaKlient(dbManager.gautiKlientaPagalID(parKl)).registracijosLaikas.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
                    System.out.println();
                    break;
                case "11":
                    //registruoti mokėjimą pagal vizitą ir darbuotoją
                    dbManager.spausdintiDarbuotojus();
                    System.out.println("Įveskite darbuotojo ID: ");
                    int pasirinkDarb = Integer.parseInt(scanner.nextLine());
                    dbManager.spausdintiVizitus();
                    System.out.println("Įveskite vizito ID: ");
                    int pasirinktasVizitas = Integer.parseInt(scanner.nextLine());
                    dbManager.ivestiMokejimaPagalDarbIrVizita(dbManager.gautiDarbuotojaPagalID(pasirinkDarb),
                            dbManager.gautiVizitaPagalID(pasirinktasVizitas));
                    break;
                case "12":
                    //atšaukti vizitą, pašalinti iš DB
                    dbManager.spausdintiVizitus();
                    System.out.println("Įveskite vizito ID: ");
                    int parinkVizitas = Integer.parseInt(scanner.nextLine());
                    dbManager.pasalintiVizita(dbManager.gautiVizitaPagalID(parinkVizitas));
                    break;
                case "13":
                    //eksportuoti klientus į CSV
                    dbManager.irasytiKlientusCSV(filePathForExportCSV);
                    break;
                case "14":
                    //importuoti klientus iš CSV
                    dbManager.nuskaitytiKlientusCSV(filePathForImportCSV);
                    break;
                case "15":
                    //visų mokėjimų suma, mažiausias ir didžiausias mokėjimas
                    dbManager.operacijosSuMokejimais();

                    break;
            }
        } while (!choice.equals("0"));

    }

    public static Klientas sukurtiKlienta(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Įveskite vardą:");
        String vardas = scanner.nextLine();
        System.out.println("Įveskite pavardę:");
        String pavarde = scanner.nextLine();
        System.out.println("Įveskite gimimo datą (yyyy-mm-dd):");
        String gimimoData = scanner.nextLine();
        //System.out.println("Įveskite registracijos datą (yyyy-mm-dd):");
        //String registracijosData = scanner.nextLine();
        System.out.println("Ar klientas yra VIP? (true/false):");
        boolean vip = scanner.nextBoolean();
        Klientas klientas = new Klientas(vardas, pavarde, gimimoData, null, vip);
        return klientas;
    }

    public static void spausdintiList(List<Klientas> list){
        for(Klientas item: list){
            System.out.println(item);
        }
    }

    public static Paslauga sukurtiPaslauga(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Įveskite paslaugą:");
        String paslPav = scanner.nextLine();
        Paslauga paslauga = new Paslauga(paslPav);
        return paslauga;
    }

    public static Darbuotojas sukurtiDarbuotoja(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Įveskite darbuotoją (vardas pavardė):");
        String vardPav = scanner.nextLine();
        Darbuotojas darbuotojas =new Darbuotojas(vardPav);
        return darbuotojas;
    }
}

