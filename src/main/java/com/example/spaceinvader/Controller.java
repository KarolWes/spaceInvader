package com.example.spaceinvader;

import com.example.spaceinvader.crafts.*;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private int bonusDuration = 50;
    private int bonusDropRate = 300;

    private Player ship;
    private List<Alien> allAliens;
    private List<Bonus> allBonuses;
    private List<Explosion> allExplosions;
    private List<Integer> bonusTimeout;
   // private EndDialog endDialogController;

    public AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long l) {
            frames++;
            playerMove();
            pointsText.setText(String.valueOf(points));
            if (frames == 5) {
                autoMove();
                generateAliens();
                for(int i = 0; i < 3; i++){
                    int j = bonusTimeout.get(i);
                    System.out.println(j + " " + i);
                    if(j > 0){
                        bonusTimeout.set(i,j-1);
                    }
                    else {
                        timeout(bonusTimeout.indexOf(j));
                        bonusTimeout.set(i, -1);
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
        allExplosions = new ArrayList<>();
        bonusTimeout = new ArrayList<>();
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
            int quantity = (int)(Math.random()*9)+3;
            int spacing = scene_x/(quantity+1);
            for(int i = 0; i < quantity; i++){
                double type = Math.random()*100;
                if(type < 50){
                    allAliens.add(new Invader((i+1)*spacing, 100));
                }
                else if(type < 75){
                    allAliens.add(new Rider((i+1)*spacing, 100));
                }
                else if(type < 85){
                    allAliens.add(new Destroyer((i+1)*spacing, 100));
                }
                else{
                    allAliens.add(new Bomb((i+1)*spacing, 100));
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

    public EventHandler<KeyEvent> handleKeyReleased = new EventHandler<>() {
        @Override
        public void handle(KeyEvent keyEvent) {
            switch (keyEvent.getCode()) {
                case A, LEFT -> left = false;
                case D, RIGHT -> right = false;
                case W, UP, SPACE -> shoot = false;
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
    public void generateBullet(List<Bullet> bullets) {
        for(Bullet bullet: bullets){
            if(!bullet.isGenerated()){
                Rectangle r = bullet.getSkin();
                mainWindow.getChildren().add(r);
                bullet.setGenerated(true);
            }
        }
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
            ship.attack();
            if(ship.isDoubleshot()){
                ship.attack();
            }
            generateBullet(ship.getFired());
            shoot = false;
        }
    }


    private void bonusMove(){
        Set<Bonus> bonusToRemove = new HashSet<>();
        for (Bonus bonus: allBonuses){
            bonus.move();
            if (bonus.hit(ship.getSkinLoc(),bonus.getSkin())){
                addBonus(bonus.getValue());
                bonus.getSkin().setVisible(false);
                bonusToRemove.add(bonus);
            }
            if(bonus.getY() > scene_y){
                bonus.getSkin().setVisible(false);
                bonusToRemove.add(bonus);
            }
        }
        allBonuses.removeAll(bonusToRemove);
    }

    private void explode(Alien alien){
        Explosion ex = alien.explode();
        if(ex != null){//if alien was a bomb
            allExplosions.add(ex);
            mainWindow.getChildren().add(ex.getSkin());
        }
    }

    private boolean alienMove(){
        Set<Alien> toRemove = new HashSet<>();
        for (Alien a : allAliens) {
            if(a.getY() >= scene_y){ // alien reaches bottom of the window
                a.die();
                toRemove.add(a);
//                System.out.println("GAME OVER");
//                endGameText.setText("Game over");
//                endGame();
//                return;
            }
            if(a.hit(ship.getSkinLoc(), a.getSkinLoc())){ // alien hits the player
                if(ship.hurt(30)){
                    endGameText.setText("Game over");
                    endGame();
                    return true;
                }
                explode(a);
                a.die();
                toRemove.add(a);
            }
            Set<Explosion> toAdd = new HashSet<>();
            for(Explosion ex: allExplosions){ // alien hit by explosion
                if(a.hit(ex.getSkin(), a.getSkinLoc())){
                    if(a.hurt(ex.getDmg())){
                        Explosion tmp = a.explode();
                        if(tmp != null){//if alien was a bomb
                            toAdd.add(tmp);
                            mainWindow.getChildren().add(tmp.getSkin());
                        }
                        a.die();
                        toRemove.add(a);
                    }
                }
            }
            allExplosions.addAll(toAdd);
            a.move(instance % a.getOrderCount());
            a.getSkinLoc().setX(a.getX());
            a.getSkinLoc().setY(a.getY());

            if (alienMoveBullets(a)) return true;
        }
        allAliens.removeAll(toRemove);
        return false;
    }

    private boolean alienMoveBullets(Alien a) {
        Set<Bullet> toRemove = new HashSet<>();
        generateBullet(a.getFired());
        for (Bullet b : a.getFired()) {
            if (b.getY() > scene_y) {
                toRemove.add(b);
                b.getSkin().setVisible(false);
            } else {
                if (b.hit(ship.getSkinLoc(), b.getSkin())) { //bullet hits player
                    if (ship.hurt(b.getDamage())) {
                        System.out.println("GAME OVER");
                        endGameText.setText("Game over");
                        ship.die();
                        endGame();
                        return true;
                    }
                    b.getSkin().setVisible(false);
                    toRemove.add(b);
                }
                for(Explosion ex: allExplosions){
                    if (b.hit(ex.getSkin(), b.getSkin())){
                        b.getSkin().setVisible(false);
                        toRemove.add(b);
                    }
                }
                Set<Bullet> playerToRemove = new HashSet<>();
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
            b.move();
        }
        a.getFired().removeAll(toRemove);
        return false;
    }

    private void playerBulletMove() {
        List<Bullet> toRemove = new ArrayList<>();
        for (Bullet b : ship.getFired()) {
            b.move();
            if (b.getY() < 0) {
                toRemove.add(b);
                b.getSkin().setVisible(false);
            } else {
                Spacecraft spacecraft = b.hit(new ArrayList<>(allAliens));
                if (spacecraft != null) {
                    points+=1;
                    if (spacecraft.hurt(b.getDamage())) { // alien dies if hit
                        double bonus = Math.random()*1000;
                        if(bonus <= bonusDropRate){
                            allBonuses.add(new Bonus(spacecraft.getX(), spacecraft.getY(), 25));
                        }
                        explode((Alien) spacecraft);
                        spacecraft.die();
                        allAliens.remove(spacecraft);
                        points+=10;
                    }
                    toRemove.add(b);
                    b.getSkin().setVisible(false);
                }
                for(Explosion ex: allExplosions){
                    if (b.hit(ex.getSkin(), b.getSkin())){
                        b.getSkin().setVisible(false);
                        toRemove.add(b);
                    }
                }
            }
        }
        ship.getFired().removeAll(toRemove);
    }

    private boolean explosionMove() {
        for(Explosion ex: allExplosions){
            if(ex.hit(ship.getSkinLoc(), ex.getSkin())){
                if(ship.hurt(ex.getDmg())){
                    System.out.println("GAME OVER");
                    endGameText.setText("Game over");
                    ship.die();
                    endGame();
                    return true;
                }
            }
        }
        List <Explosion> exToRemove = new ArrayList<>();
        for(Explosion ex: allExplosions){
            ex.resize();
            if(ex.isExpired()){
                exToRemove.add(ex);
            }
        }
        allExplosions.removeAll(exToRemove);
        return false;
    }


    public void autoMove() {
        bonusMove();
        if(alienMove()){return;}
        playerBulletMove();
        if (explosionMove()) return;
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
        endGameWindow();
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
                bonusTimeout.add(0, bonusTimeout.get(0) + bonusDuration+1);
                ship.setShielded(true);
                break;
            case 1:
                bonusTimeout.add(1, bonusTimeout.get(1) + bonusDuration+1);
                ship.setDoubleshot(true);
                break;
            case 2:
                bonusTimeout.add(2, bonusTimeout.get(2) + bonusDuration+1);
                for(Alien a: allAliens){
                    a.multipleSpeed(0.25);
                }
                break;
            case 3:
                ship.regenerate();
                break;
            case 4:
                points += 25;
                break;
// this option does not exist
            default:
                break;
        }
    }

    @FXML
    public void endGameWindow(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Main.class.getResource("endDialog.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 300, 100);
            EndDialog endDialogController = fxmlLoader.getController();
            endDialogController.initData(points);
            Stage stage = new Stage();
            stage.setTitle("End of the game");
            stage.setScene(scene);
            stage.show();

        }catch (IOException e)
        {
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
            return;
        }
    }

}//end of class