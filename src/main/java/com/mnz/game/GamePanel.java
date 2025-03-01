package com.mnz.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

import com.mnz.game.entity.planet.Planet;
import com.mnz.game.entity.player.Player;
import com.mnz.game.entity.star.Star;
import com.mnz.game.sound.Music;
import com.mnz.game.util.KeyHandler;

public class GamePanel extends JPanel implements Runnable{
    // Screen settings
    final int originalTileSize = 32;
    final int scale = 1;
    public final int tileSize = originalTileSize * scale;
    final int maxScreenCol = 60;
    final int maxScreenRow = 40;
    public final int screenWidth = tileSize * maxScreenCol;  // 1024 pixels
    public final int screenHeight = tileSize * maxScreenRow;  // 768 pixels

    int FPS = 60; // Frames per second

    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    Player player = new Player(this, keyH);
    Star star = new Star(this);
    Planet planet = new Planet(this);
    // StarManager starmgr = new StarManager(this, 5);

    // Create music and menu instance
    public static Music menuMusic = new Music();
    public static Menu mainMenu = new Menu();

    // Declare Variables
    static boolean menuIsOpen = false;
    static boolean pauseButtonClicked = false;
    static boolean gameStarted = false;

    public enum GameState {
        PLAYING, MENU;
    }

    private GameState gameState;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);

        gameState = GameState.MENU; // Set the initial state to MENU
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        while (true){
            if (gameState == GameState.PLAYING) {
                if (!gameStarted){
                    // Display the menu only once
                    mainMenu.openMainMenu(this, Main.window);
                    menuIsOpen = true; // Set the menuIsOpen flag to true
                    gameStarted = true;
                }
                // Game logic (physics, AI, etc.)
                double drawInterval = 1000000000 / FPS; // 0.0166666 seconds
                double nextDrawTime = System.nanoTime() + drawInterval;
                // Update game objects
                // Handle player input
                // Render the game
                while(gameThread != null) {
                    // step 1 - UPDATE player position
                    update();
                    // step 2 - DRAW screen with updated info
                    repaint();
                    
                    try {
                        double remainingTime = nextDrawTime - System.nanoTime();
                        remainingTime = remainingTime / 1000000;

                        if(remainingTime < 0) {
                            remainingTime = 0;
                        }
                        Thread.sleep((long) remainingTime);
                        nextDrawTime += drawInterval;

                    } catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    if(pauseButtonClicked){
                        gameStarted = false;
                        gameState = GameState.MENU;
                    }
                }
            }
        }
    }

    public void startMenu(){
        if (gameState == GameState.MENU) {
            if (!menuIsOpen) {
                // Display the menu only once
                mainMenu.openMainMenu(this, Main.window);
                menuIsOpen = true; // Set the menuIsOpen flag to true
            }
            // Handle menu input...
            
            // When you're done with the menu (e.g., the player clicks a button to start the game), switch back to PLAYING
            if (mainMenu.playButtonClicked()){    
                menuIsOpen = false; // Reset the menuIsOpen flag
                gameStarted = false;
                gameState = GameState.PLAYING;
            }
        }
    }
    public void update() {
        player.update();
        //starmgr.update();
        star.update();
        planet.update();
    }
    
    // draw game components
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        player.draw(g2);
        //starmgr.draw(g2);

        //***************************************WORK ON THIS PART TO GET STAR AND PLANET IMPLEMENTED. */
        star.draw(g2);
        planet.draw(g2);
        //g2.dispose();
    }

    public void setUpGame() {
        player.setName("Mike");
        star.setName("Sun");
        star.setMagnitude(10);
        star.setX(this.screenHeight/2);
        star.setX(this.screenWidth/2);
        System.out.println(star.toString());  // output to debug console
        planet.setX(700);
        planet.setY(400);
    }

    public void switchToMenu() {
        gameState = GameState.MENU;
    }

    public void switchToPlaying() {
        gameState = GameState.PLAYING;
    }

    public Object getConfig() {
        return null;
    }

    public boolean isFullScreenOn() {
        return false;
    }
}
