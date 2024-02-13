package com.example.proj;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Data {
    //timestamp,duration,app,title
    StringProperty timestamp;
    StringProperty duration;
    StringProperty app;
    StringProperty title;
    String tss;
    Double ds;
    String as;
    String ts;
    public Data(String timestam, String duratio, String ap, String titl ){
        title = new SimpleStringProperty(titl);
        app = new SimpleStringProperty(ap);
        duration = new SimpleStringProperty(duratio);
        timestamp = new SimpleStringProperty(timestam);
        tss = timestam;
        ds = Double.parseDouble(duratio);

        as = ap;
        ts = titl;
    }
}
