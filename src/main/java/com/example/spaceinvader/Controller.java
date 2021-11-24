package com.example.spaceinvader;

import com.example.spaceinvader.crafts.*;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class Controller {

    public static int scene_x = 1200;
    public static int scene_y = 800;
    private int instance;
    private int frames = 0;
    private boolean game = true;
    private boolean left = false;
    private boolean right = false;
    private boolean shoot = false;
    private int points = 0;

    private Player ship;
    private List<Alien> allAliens;
    private List<Bonus> allBonuses;
    private List<Integer> bonusTimeout;

    public AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long l) {
            frames++;
            playerMove();
            pointsText.setText(String.valueOf(points));
            if (frames == 5) {
                autoMove();
                generateAliens();
                for(int i = 0; i < 4; i++){
                    int j = bonusTimeout.get(i);
                    if(j > 0){
                        j--;
                    }
                    else if (j == 0){
                         timeout(bonusTimeout.indexOf(j));
                         j=-1;
                    }
                }
                frames = 0;
            }
            ship.decreaseSCd();
        }
    };


    @FXML
    private Pane mainWindow;
    @FXML
    private Text endGameText;
    @FXML
    private Text pointsText;

    @FXML
    public void initialize() {
        mainWindow.setPrefHeight(scene_y);
        mainWindow.setPrefWidth(scene_x);
        ship = new Player(50, scene_x / 2, scene_y - 100);
        mainWindow.getChildren().add(ship.getSkinLoc());
        mainWindow.getChildren().add(ship.getHp().getSkin());
        mainWindow.getChildren().add(ship.getShield());
        endGameText = new Text();
        pointsText = new Text();
        pointsText.setFill(Color.LIME);
        pointsText.setFont(Font.font("Arial", 24));
        pointsText.setY(30);
        pointsText.setX(20);
        mainWindow.getChildren().add(pointsText);
        allAliens = new ArrayList<>();
        allBonuses = new ArrayList<>();
        bonusTimeout = new ArrayList<>();
        bonusTimeout.add(-1);
        bonusTimeout.add(-1);
        bonusTimeout.add(-1);
        bonusTimeout.add(-1);
        firstLine();

        instance = 0;
        timer.start();
    }

    public void firstLine(){
        allAliens.add(new Invader(100, 100));
        allAliens.add(new Invader(200, 100));
        allAliens.add(new Invader(300, 100));
        allAliens.add(new Invader(400, 100));
        allAliens.add(new Invader(500, 100));
        allAliens.add(new Invader(600, 100));
        allAliens.add(new Invader(700, 100));
        allAliens.add(new Invader(800, 100));
        allAliens.add(new Invader(900, 100));
        allAliens.add(new Invader(1000, 100));
        allAliens.add(new Invader(1100, 100));
        drawAliens();
    }

    public void generateAliens(){
        boolean areUnder = true;
        for(Alien a: allAliens){
            areUnder = areUnder && (a.getY() >= 250);
        }
        if(allAliens.isEmpty() || areUnder){
            int quantity = (int)(Math.random()*11)+1;
            int spacing = scene_x/(quantity+1);
            for(int i = 0; i < quantity; i++){
                double type = Math.random()*100;
                if(type < 60){
                    allAliens.add(new Invader((i+1)*spacing, 100));
                }
                else if(type < 90){
                    allAliens.add(new Rider((i+1)*spacing, 100));
                }
                else{
                    //third kind of alien
                }
            }
        }
        drawAliens();
    }

    @FXML
    public void drawAliens() {
        for (Alien a : allAliens) {
            if(!a.isGenerated()){
                mainWindow.getChildren().add(a.getSkinLoc());
                mainWindow.getChildren().add(a.getHp().getSkin());
                a.setGenerated(true);
            }
        }
    }

    @FXML
    public void drawBonuses() {
        for (Bonus bonus : allBonuses) {
            if(!bonus.isGenerated()){
                mainWindow.getChildren().add(bonus.getSkin());
                bonus.setGenerated(true);
            }
        }
    }

    @FXML
    public void handleTest(MouseEvent mouseEvent) {
        if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
            ship.move(270, 10);
        } else if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
            ship.move(90, 10);
        }

    }

    public EventHandler<KeyEvent> handleKeyRelesed = new EventHandler<>() {
        @Override
        public void handle(KeyEvent keyEvent) {
            switch (keyEvent.getCode()) {
                case A, LEFT:
                    left = false;
                    break;
                case D, RIGHT:
                    right = false;
                    break;
                case W, UP, SPACE:
                    shoot = false;
            }
        }
    };


    public EventHandler<KeyEvent> handleKeyPressed = new EventHandler<>() {
        @Override
        public void handle(KeyEvent keyEvent) {
            if (game) {
                switch (keyEvent.getCode()) {
                    case A, LEFT:
                        left = true;
                        break;
                    case D, RIGHT:
                        right = true;
                        break;
                    case ESCAPE:
                        System.out.println("ESC");
                        break;
                    case W, UP, SPACE:
                        if(ship.getSmallCooldown() <= 0){
                            shoot = true;
                        }
                        break;
                    default:
                        break;
                }
            }

        }
    };

    @FXML
    public void generateBullet(Bullet bullet) {
        Rectangle r = bullet.getSkin();
        mainWindow.getChildren().add(r);
        bullet.setGenerated(true);
    }

    public void playerMove() {
        if (left) {
            if (ship.getX() > 0) {
                ship.move(270, ship.getSpeed());
            }
        }
        if (right) {
            if (ship.getX() + ship.getxSize() < scene_x) {
                ship.move(90, ship.getSpeed());
            }
        }
        if (shoot) {
            generateBullet(ship.attack());
            if(ship.isDoubleshot()){
                generateBullet(ship.attack());
            }
            shoot = false;
        }
    }


    public void autoMove() {
        List<Bonus> bonusToRemove = new ArrayList<>();
        for (Bonus bonus: allBonuses){
            bonus.move();
            if (bonus.hit(ship.getSkinLoc(),bonus.getSkin())){
                addBonus(bonus.getValue());
                bonus.getSkin().setVisible(false);
                bonusToRemove.add(bonus);
            }
        }
        allBonuses.removeAll(bonusToRemove);

        for (Alien a : allAliens) {
            a.move(instance % a.getOrderCount());
            if(a.getY() >= scene_y){ // alien reaches bottom of the window
                System.out.println("GAME OVER");
                a.getHp().getSkin().setVisible(false);
                endGameText.setText("Game over");
                endGame();
                return;
            }
            if(a.hit(ship.getSkinLoc(), a.getSkinLoc())){ // alien hits the player
                if(ship.hurt(30)){
                    endGameText.setText("Game over");
                    endGame();
                    return;
                }
                a.die();
                allAliens.remove(a);
                if (allAliens.size() == 0) {
                    System.out.println("WIN");
                    endGameText.setText("Win");
                    endGame();
                    return;
                }
                return;
            }
            a.getSkinLoc().setX(a.getX());
            a.getSkinLoc().setY(a.getY());
            List<Bullet> toRemove = new ArrayList<>();
            for (Bullet b : a.getFired()) { // alien shoots
                if(!b.isGenerated()){
                    generateBullet(b);
                }
                b.move();
                if (b.getY() > scene_y) {
                    toRemove.add(b);
                    b.getSkin().setVisible(false);
                } else {
                    if (b.hit(ship.getSkinLoc(), b.getSkin())) {
                        if (ship.hurt(b.getDamage())) {
                            System.out.println("GAME OVER");
                            endGameText.setText("Game over");
                            ship.die();
                            endGame();
                            return;
                        }
                        b.getSkin().setVisible(false);
                        toRemove.add(b);
                    }
                    List<Bullet> playerToRemove = new ArrayList<>();
                    for(Bullet playerB: ship.getFired()){ // if two bullets meet
                        if(b.hit(playerB.getSkin(), b.getSkin())){
                            b.getSkin().setVisible(false);
                            toRemove.add(b);
                            playerB.getSkin().setVisible(false);
                            playerToRemove.add(playerB);
                        }
                    }
                    ship.getFired().removeAll(playerToRemove);
                }
            }
            a.getFired().removeAll(toRemove);
        }
        List<Bullet> toRemove = new ArrayList<>();
        for (Bullet b : ship.getFired()) {
            b.move();
            if (b.getY() < 0) {
                toRemove.add(b);
                b.getSkin().setVisible(false);
            } else {
                Spacecraft sHit = b.hit(new ArrayList<>(allAliens));
                if (sHit != null) {
                    points+=1;
                    if (sHit.hurt(b.getDamage())) { // alien dies if hit
                        double bonus = Math.random()*1000;
                        if(bonus <= 300){
                            allBonuses.add(new Bonus(sHit.getX(), sHit.getY(), 25));
                        }
                        sHit.die();
                        allAliens.remove(sHit);
                        points+=10;
                    }
                    toRemove.add(b);
                    b.getSkin().setVisible(false);
                    if (allAliens.size() == 0) {
                        System.out.println("WIN");
                        endGameText.setText("Win");
                        endGame();
                        return;
                    }
                }
            }
        }
        ship.getFired().removeAll(toRemove);
        drawBonuses();
        instance++;
    }

    private void endGame() {
        pointsText.setText(String.valueOf(points));
        timer.stop();
        for (Alien a : allAliens) {
            for (Bullet b : a.getFired()) {
                b.getSkin().setVisible(false);
            }
            a.getFired().clear();
        }


        for (Bullet b : ship.getFired()) {
            b.getSkin().setVisible(false);
        }
        ship.getFired().clear();
        endGameText.setFont(Font.font("Arial", 24));
        endGameText.setX(scene_x / 2);
        endGameText.setY(scene_y / 2);
        endGameText.setFill(Color.GOLD);
        mainWindow.getChildren().add(endGameText);
        game = false;
    }

    public void timeout(int bonus){
        switch(bonus){
            case 0:
                ship.setShielded(false);
                break;
            case 1:
                ship.setDoubleshot(false);
                break;
            case 2:
                for(Alien a: allAliens){
                    a.multipleSpeed(1);
                }
                break;
            default:
                //nothing here
        }
    }


    public void addBonus(int bonus){
        System.out.println("Bonus value: " + bonus);
        switch (bonus) {
            case 0:
                bonusTimeout.add(0, bonusTimeout.get(0) + 21);
                ship.setShielded(true);
                break;
            case 1:
                bonusTimeout.add(1, bonusTimeout.get(1) + 21);
                ship.setDoubleshot(true);
                break;
            case 2:
                bonusTimeout.add(2, bonusTimeout.get(2) + 21);
                for(Alien a: allAliens){
                    a.multipleSpeed(0.25);
                }
                break;
            case 3:
                
                break;
            case 4:
                points += 25;
                break;
// this option does not exist
            default:
                break;
        }
    }


}//end of class