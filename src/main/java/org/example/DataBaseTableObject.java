package org.example;

public class DataBaseTableObject {
    public int id;

    public DataBaseTableObject(int id) {
        this.id = id;
    }

    @Override
    public String toString(){
        return Integer.toString(id);
    }

}
