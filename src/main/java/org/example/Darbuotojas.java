package org.example;

public class Darbuotojas {
    public int darbuotojoId;
    public String darbVardasPavarde;
    public Darbuotojas(String darbVardasPavarde) {
        darbuotojoId = 0;
        this.darbVardasPavarde = darbVardasPavarde;
    }
    public Darbuotojas(int darbuotojoId, String darbVardasPavarde) {
        this.darbuotojoId = darbuotojoId;
        this.darbVardasPavarde = darbVardasPavarde;
    }


}
