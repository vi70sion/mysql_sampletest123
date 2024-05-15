package org.example;
public class Klientas extends DataBaseTableObject{
    public String vardas;
    public String pavarde;
    public String gimimoData;
    public String registracijosData;
    public boolean vip;

    public Klientas(String vardas, String pavarde, String gimimoData, String registracijosData, boolean vip) {
        super(0);
        this.vardas = vardas;
        this.pavarde = pavarde;
        this.gimimoData = gimimoData;
        this.registracijosData = registracijosData;
        this.vip = vip;
    }

    public Klientas(int id, String vardas, String pavarde, String gimimoData, String registracijosData, boolean vip) {
        super(id);
        this.vardas = vardas;
        this.pavarde = pavarde;
        this.gimimoData = gimimoData;
        this.registracijosData = registracijosData;
        this.vip = vip;

    }

    public String toString(){
        return super.toString() + " " + vardas + " " + pavarde + " " + gimimoData + " " + registracijosData + " " + vip;
    }
}
