package com.example.proyecto_pdm_g10.cz13016;

public class Filtro {
    private String idItem;
    private String Item;

    public Filtro() {
    }

    public Filtro(String idItem, String item) {
        this.idItem = idItem;
        Item = item;
    }

    public String getIdItem() {
        return idItem;
    }

    public void setIdItem(String idItem) {
        this.idItem = idItem;
    }

    public String getItem() {
        return Item;
    }

    public void setItem(String item) {
        Item = item;
    }
}
