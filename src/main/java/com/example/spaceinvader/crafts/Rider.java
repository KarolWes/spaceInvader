package com.example.spaceinvader.crafts;

import javafx.scene.image.Image;

import java.io.File;

public class Rider extends Alien{
    public Rider(int x, int y) {
        super(1, 20, 5, x, y, new Image(new File("src/main/resources/com/example/spaceinvader/invader72.png").toURI().toString()));
        generateMoves();
    }

    @Override
    protected void generateMoves() {
        super.getMoves().add(270);
        super.getMoves().add(270);
        super.getMoves().add(0);
        super.getMoves().add(270);
        super.getMoves().add(270);
        super.getMoves().add(0);
        super.getMoves().add(270);
        super.getMoves().add(270);
        super.getMoves().add(0);
        super.getMoves().add(90);
        super.getMoves().add(90);
        super.getMoves().add(0);
        super.getMoves().add(90);
        super.getMoves().add(90);
        super.getMoves().add(0);
        super.getMoves().add(90);
        super.getMoves().add(90);
        super.getMoves().add(0);


    }
}
