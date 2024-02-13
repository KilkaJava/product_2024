package com.example.proj;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Data2 {
        StringProperty nazvanie;
        StringProperty dlitelnost;
        StringProperty zapolni;
        Double dlitt;

        public Data2(String naz, Double dlit, String zp) {
            nazvanie = new SimpleStringProperty(naz);
            dlitelnost = new SimpleStringProperty(Double.toString(dlit));
            zapolni = new SimpleStringProperty(zp);
            dlitt = dlit;
        }
}
