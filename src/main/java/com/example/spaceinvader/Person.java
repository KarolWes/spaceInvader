package com.example.spaceinvader;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;

public class Person {
    private String name;
    private int points;

    public Person(String name, int points) {
        this.name = name;
        this.points = points;
    }
    public List<Label> getPerson(){
        List<Label> person = new ArrayList<>();
        Label name = new Label();
        name.setText(this.name);
        person.add(name);
        Label points = new Label();
        points.setText(String.valueOf(this.points));
        person.add(points);
        return person;
    }

    public String getName() {
        return name;
    }

    public int getPoints() {
        return points;
    }
}
