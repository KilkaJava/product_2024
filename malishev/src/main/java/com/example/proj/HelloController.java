package com.example.proj;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class HelloController {
    @FXML
    TableView<Data> table;
    @FXML
    TableColumn<Data, String> ti;
    @FXML
    TableColumn<Data, String> dur;
    @FXML
    TableColumn<Data, String> app;
    @FXML
    TableColumn<Data, String> title;

    @FXML
    TableView<Data1> table1;
    @FXML
    TableColumn<Data1, String> naz;
    @FXML
    TableColumn<Data1, String> dlit;
    @FXML
    TableColumn<Data1, String> zp;


    @FXML
    TableView<Data2> table2;
    @FXML
    TableColumn<Data2, String> vk;
    @FXML
    TableColumn<Data2, String> dlit1;
    @FXML
    TableColumn<Data2, String> zp1;

    @FXML
    BarChart BarC;

    @FXML
    LineChart lineC;

    FileChooser op;
    File res;
    @FXML
    TextArea tA;
    
    List<String> listChast = new ArrayList<>();
    
    ObservableList<Data> listDB;
    ObservableList<Data1> listDB1;
    ObservableList<Data2> listDB2;
    HashSet<String> appSet;
    HashSet<String> browserSet;
    List<String > appSetList;
    List<String> dateList;
    List<String> dateSetList;
    HashSet<String> dateSet;
    List<String > browsetSetList;
    List <Double> appDuration;
    List <Double> dateDuration;
    List <Double> browserDuration;
    String str[]=new String[4];

    //Статистика:
    int bdSize;
    int appUnic;
    double YouTube = 0;

   @FXML
    MenuItem open;
   public void initialize(){
        listDB = FXCollections.observableArrayList();
       listDB1 = FXCollections.observableArrayList();
       listDB2 = FXCollections.observableArrayList();
        ti.setCellValueFactory(cellData -> cellData.getValue().timestamp);
       dur.setCellValueFactory(cellData -> cellData.getValue().duration);
       app.setCellValueFactory(cellData -> cellData.getValue().app);
       title.setCellValueFactory(cellData -> cellData.getValue().title);
       naz.setCellValueFactory(cellData1 -> cellData1.getValue().nazvanie);
       dlit.setCellValueFactory(cellData1 -> cellData1.getValue().dlitelnost);
       zp.setCellValueFactory(cellData1 -> cellData1.getValue().zapolni);
       vk.setCellValueFactory(cellData2 -> cellData2.getValue().nazvanie);
       dlit1.setCellValueFactory(cellData2 -> cellData2.getValue().dlitelnost);
       zp1.setCellValueFactory(cellData2 -> cellData2.getValue().zapolni);
   }
   public void readBD() throws Exception {
       BufferedReader inputfile = new BufferedReader(new FileReader(res));
       int numberTerms = 0;

           do {
               numberTerms++;
               //массив
               str = inputfile.readLine().split(",");
               try {
                   if (str[3] == null) {
                       str[3] = "--";
                   }
                   Data s = new Data(str[0], str[1], str[2], str[3]);
                   listDB.add(s);
                   table.setItems(listDB);
               }
               catch (Exception e){
                   Data s = new Data(str[0], str[1], str[2],"0");
                   listDB.add(s);
                   table.setItems(listDB);
               }
           } while (inputfile.ready());
           inputfile.close();
           bdSize = listDB.size();

   }


   public void open (ActionEvent actionEvent) throws Exception {
       op = new FileChooser();
       op.setTitle("Выберите БД");
       op.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("csv файлы",".csv"));
       res = op.showOpenDialog(null);
       if (res!=null) {
           readBD();
           int i = 0;
           if(i<1000000){
               listChast.add(i, String.valueOf(app.getCellObservableValue(i)));
           }

       }
       else
       System.out.println( "ОТМЕНА");
   }

    public <T> Map<T, Long> countByClassicalLoop(List<T> inputList) {
        Map<T, Long> resultMap = new HashMap<>();
        for (T element : inputList) {
            if (resultMap.containsKey(element)) {
                resultMap.put(element, resultMap.get(element) + 1L);
            } else {
                resultMap.put(element, 1L);
            }
        }
        return resultMap;
    }


    public void showStat(ActionEvent actionEvent) {
        appSet = new HashSet<>();
        for(Data d: listDB){
            appSet.add(d.as);
        }
        // Получим размер HashSet
        appUnic = appSet.size();


        // Общее время работы каждого приложения.
        appDuration = new ArrayList<>();
        appSetList = new ArrayList<>(appSet);
        Double dur = 0.0;
        for(int i = 0;i<appSet.size();i++){
            for (Data d : listDB){
                if(appSetList.get(i).equals(d.as)){
                    dur = dur+d.ds;
                }
            }
            appDuration.add(dur);
            dur =0.0;
        }
        for (int i =0;i<appDuration.size();i++){

        }
        /////////////////////////////////////////В АНАЛИЗ
        for(int i = appSetList.size()-1;i>0;i--){

            Data1 s = new Data1(appSetList.get(i), appDuration.get(i), "nothing");
            listDB1.add(s);
            table1.setItems(listDB1);

        }
        ////////////////////////////////////////////БРОВЗЕР
        browserSet = new HashSet<>();
        for(Data d: listDB){
            if(d.as.equals("chrome.exe")||d.as.equals("browser.exe")||d.as.equals("opera.exe")) {
                browserSet.add(d.ts);
            }
        }
        browsetSetList = new ArrayList<>(browserSet);
        browserDuration = new ArrayList<>();
        for(int i = 0;i<browserSet.size();i++){
            for (Data d : listDB){
                if(browsetSetList.get(i).equals(d.ts)){
                    dur = dur+d.ds;
                }
            }
            browserDuration.add(dur);
            dur =0.0;
        }

        for(int i = browsetSetList.size()-1;i>0;i--) {

            Data2 s = new Data2(browsetSetList.get(i), browserDuration.get(i), "nothing");
            listDB2.add(s);
            table2.setItems(listDB2);
        }
            ////////////////////////////////////////////// ЮТЮБЕ
            System.out.println(browsetSetList.size());
        double dr = 0;

        for(Data d: listDB) {
            if (d.ts.contains("YouTube")) {
                YouTube = YouTube + d.ds;
            }
            if (!d.ts.contains("YouTube")) {
                dr = dr + d.ds;
            }
        }


        CategoryAxis xA = new CategoryAxis();
        NumberAxis yA = new NumberAxis();
            BarChart<String,Number> barChart = new BarChart<String,Number>(xA,yA);
        XYChart.Series<String,Number> data = new XYChart.Series<String,Number>();
        data.getData().add(new XYChart.Data<String,Number>("YouTube",YouTube/3600));
        data.getData().add(new XYChart.Data<String,Number>("Другое",dr/3600));
        BarC.getData().add(data);

        ////////////////////////////////////////////////String to date и тд
        dateList = new ArrayList<>();
        dateSet = new HashSet<>();
        dateDuration = new ArrayList<>();
        for(int i = 0; i<bdSize-1;i++){
            dateList.add(listDB.get(i).tss.substring(0,10));
        }
        for(int i = 0; i<dateList.size()-1;i++){
            dateSet.add(dateList.get(i));

        }
        dateSetList = new ArrayList<>(dateSet);
        Collections.sort(dateSetList);
        for(int i = 0;i<dateSetList.size();i++){
            for (Data d : listDB){
                if(dateSetList.get(i).equals(d.tss.substring(0,10))){
                    dur = dur+d.ds;
                }
            }
            dateDuration.add(dur);
            dur =0.0;
        }

        var xAxis = new NumberAxis();
        xAxis.setLabel("День");

        var yAxis = new NumberAxis();
        yAxis.setLabel("Время");

        var lineChart = new LineChart<>(xAxis, yAxis);
        var dataa = new XYChart.Series<String,Number>();
        for (int i = 0;i<dateSetList.size();i++) {
            dataa.getData().add(new XYChart.Data<String, Number>(dateSetList.get(i),dateDuration.get(i)/3600));
        }
        lineC.getData().add(dataa);

    }

    public void dialog(ActionEvent actionEvent) {

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Описание данных");
            String text = "Количество строк: "+bdSize+"\n"+
                    "Начало наблюдений: "+listDB.get(bdSize-1).tss+"\n"+
                    "Конец наблюдений: "+listDB.get(0).tss;
            dialog.setHeaderText(text);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.show();

    }
}