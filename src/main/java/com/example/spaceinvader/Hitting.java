package com.example.spaceinvader;

import com.example.spaceinvader.crafts.Spacecraft;
import javafx.scene.Node;

public interface Hitting {
    public boolean hit(Node target, Node myself);
}
