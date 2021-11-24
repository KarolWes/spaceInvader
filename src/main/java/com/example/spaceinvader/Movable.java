package com.example.spaceinvader;

import java.util.ArrayList;
import java.util.List;

public abstract class Movable {
    public List<Integer> move(int dir, double dist, int pos_x, int pos_y)
    {
        //System.out.println("moved in direction " + dir + " for " + dist + " miles.");
        pos_x+= Math.sin(Math.toRadians(dir))*dist;
        pos_y+= Math.cos(Math.toRadians(dir))*dist;
        //System.out.println("Your new position: (" + pos_x + "; " + pos_y + ")");
        List<Integer> res = new ArrayList<>();
        res.add(pos_x);
        res.add(pos_y);
        return res;
    }

}
