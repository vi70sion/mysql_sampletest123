package org.example;

import java.util.Scanner;

public class Paslauga extends DataBaseTableObject{
    public String paslaugosPavadinimas;

    public Paslauga(String paslaugosPavadinimas) {
        super(0);
        this.paslaugosPavadinimas = paslaugosPavadinimas;
    }

    public Paslauga(int id, String paslPav) {
        super(id);
        this.paslaugosPavadinimas = paslPav;
    }

    @Override
    public String toString(){
        return super.toString() + " " + paslaugosPavadinimas;
    }
}
