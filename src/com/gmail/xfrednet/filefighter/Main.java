package com.gmail.xfrednet.filefighter;

import com.gmail.xfrednet.filefighter.entity.Entity;
import com.gmail.xfrednet.filefighter.entity.Player;
import com.gmail.xfrednet.filefighter.graphics.Camera;
import com.gmail.xfrednet.filefighter.graphics.Screen;
import com.gmail.xfrednet.filefighter.graphics.Sprite;
import com.gmail.xfrednet.filefighter.graphics.cameras.ControllableCamera;
import com.gmail.xfrednet.filefighter.level.Level;
import com.gmail.xfrednet.filefighter.util.Input;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Main extends Canvas implements Runnable {
    
    public static final String NAME = "File Fighter";
    public static final int WIDTH = 480;
    public static final int HEIGHT = WIDTH * 9 / 16;
    public static final int scale = 3;
    public static final int UPS = 30;
    
    Thread t;
    JFrame jframe;
    Input input;
    Screen screen;
    Camera camera;
    
    Level level;
    Player player;
    
    //Image
    BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
    
    public static boolean running = false;
    
    /*
	* Constructor
	* */
    public Main() {
        setSize(new Dimension(WIDTH * scale, HEIGHT * scale));
        
        //Screen
        screen = new Screen(WIDTH, HEIGHT);
        
        //input
        setFocusable(true);
        requestFocus();
        input = new Input();
        addKeyListener(input);
    
        player = new Player(60, 60, input);
        level = new Level(25, 25, player);
        camera = new ControllableCamera(level, screen, input);
    }
    
    /*
	* Main method
	* */
    public static void main(String[] args) {
        Main main = new Main();
        
        main.jframe = new JFrame(NAME);
        main.jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main.jframe.setResizable(false);
        main.jframe.setFocusable(false);
        main.jframe.add(main);
        main.jframe.pack();
    
        main.jframe.setLocationRelativeTo(null);
        main.jframe.show();
        
        main.start();
    }
    
    /*
	* Util
	* */
    public void start() {
        t = new Thread(this);
        t.start();
    }
    
    public synchronized void stop() {
        try {
            t.join();
        } catch (InterruptedException e) {
            System.exit(0);
        }
        System.exit(0);
    }
    
    //Game loop
    @Override
    public void run() {
        running = true;
        
        long now;
        long timer = System.currentTimeMillis();
        long lastTime = System.nanoTime();
        double ns = 1000000000.0 / UPS;
        double delta = 0;
        int frames = 0;
        int updates = 0;
        
        while (running) {
            now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            
            if (delta >= 1) {
                update();
                delta--;
                updates++;
            }
            
            render();
            frames++;
            
            if ((System.currentTimeMillis() - timer) >= 1000) {
                
                System.out.println("[INFO] UPS: " + updates + ", FPS: " + frames);
                
                timer += 1000;
                frames = 0;
                updates = 0;
            }
            
        }
        
        stop();
        
    }
    
    /*
	* loop util
	* */
    public void update() {
        camera.update();
        player.update(level);
        level.update();
    }
    
    public void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }
        
        screen.clear();
        level.render(camera.getXOffset(), camera.getYOffset(), screen);
        player.render(screen);
        for (int i = 0; i < screen.pixels.length; i++) {
            pixels[i] = screen.pixels[i];
        }
        
        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.RED);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        
        g.dispose();
        bs.show();
        
    }
    
}
