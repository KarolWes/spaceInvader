package com.example.spaceinvader;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.WindowEvent;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class LeaderBoard {
    private String filename = "leader.txt";
    private ObservableList<Person> people;
    private Person player;
    @FXML
    GridPane grid;

    public void initialize(){}

    public void createLeaderBoard(){
        Collections.sort(people, Collections.reverseOrder(new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                Integer i1 = o1.getPoints();
                Integer i2 = o2.getPoints();
                return i1.compareTo(i2);
            }}));

        int playerID = people.indexOf(player);
        if( playerID > 9){
            for(int i = 1; i<=8 && i < people.size(); i++){
                List <Label> record = people.get(i-1).getPerson();
                grid.addRow(i, new Label(String.valueOf(i)));
                grid.addRow(i, record.get(0));
                grid.addRow(i, record.get(1));
            }
            grid.addRow(9, new Label("..."));
            List <Label> record = people.get(playerID).getPerson();
            record.get(0).setId("player");
            grid.addRow(10, new Label(String.valueOf(playerID+1)));
            grid.addRow(10, record.get(0));
            grid.addRow(10, record.get(1));

        }
        else{
            for(int i = 1; i<=10 && i < people.size(); i++){
                List <Label> record = people.get(i-1).getPerson();
                if(i -1 == playerID){
                    record.get(0).setId("player");
                }

                grid.addRow(i, new Label(String.valueOf(i)));
                grid.addRow(i, record.get(0));
                grid.addRow(i, record.get(1));
            }
        }

    }


    public void loadPeople() throws IOException{
        people = FXCollections.observableArrayList();
        Path path = Paths.get(filename);
        BufferedReader br= Files.newBufferedReader(path);
        String input;
        try{
            while((input = br.readLine()) != null)
            {
                String[] itemPieces = input.split(";");
                String name = itemPieces[0];
                String points = itemPieces[1];
                Person person = new Person(name, Integer.parseInt(points));
                people.add(person);
            }
        }finally {
            if (br!=null)
            {
                br.close();
            }
        }
    }

    public void save()throws IOException {
        Path path = Paths.get(filename);
        BufferedWriter bw = Files.newBufferedWriter(path);
        try {
            Iterator<Person> iter = people.iterator();
            while(iter.hasNext())
            {
                Person item = iter.next();
                bw.write(String.format("%s;%s",
                        item.getName(),
                        item.getPoints()));
                bw.newLine();
            }
        }finally {
            if(bw != null)
            {
                bw.close();
            }
        }
    }
    public void initData(int points, String name) throws IOException {
        loadPeople();
        player = new Person(name, points);
        people.add(player);
        createLeaderBoard();
        save();
    }
}
