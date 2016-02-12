package com.gmail.xfrednet.filefighter;

import com.gmail.xfrednet.filefighter.entity.Player;
import com.gmail.xfrednet.filefighter.graphics.Camera;
import com.gmail.xfrednet.filefighter.graphics.GUIManager;
import com.gmail.xfrednet.filefighter.graphics.Screen;
import com.gmail.xfrednet.filefighter.graphics.gui.GUIComponent;
import com.gmail.xfrednet.filefighter.graphics.gui.GUIComponentGroup;
import com.gmail.xfrednet.filefighter.graphics.gui.components.*;
import com.gmail.xfrednet.filefighter.level.FileLevel;
import com.gmail.xfrednet.filefighter.level.Level;
import com.gmail.xfrednet.filefighter.util.Input;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;

public class Main extends Canvas implements Runnable {
    
    public static final String NAME = "File Fighter";
    public static final String PLAYER_NAME = "xFrednet";
    public static final int WIDTH = 480;
    public static final int HEIGHT = WIDTH * 9 / 16;
    public static final int scale = 3;
    public static final int UPS = 30;
	
	public static final Font gameFont = new Font("Lucida Console", Font.PLAIN, 16);
    
    Thread thread;
    JFrame jframe;
    Input input;
    
    //Graphics
    Screen screen;
    Camera camera;
    GUIManager guiManager;
	GameHud hud;
    
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
        addMouseListener(input);
        addMouseMotionListener(input);
    

        player = new Player(60, 60, input, PLAYER_NAME, null);
        
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
        guiManager = new GUIManager(getWidth(), getHeight());
	    hud = new GameHud(guiManager);
	    guiManager.addComponent(hud);
    
        level = new FileLevel(player, input, screen, new File(System.clearProperty("user.home") + "\\Desktop"), guiManager);
        camera = level.getCamera();
        
        thread = new Thread(this);
        thread.setName("Game Loop");
        thread.start();
    
    }
    
    public synchronized void stop() {
        try {
            thread.join();
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
                
                hud.FPSInfo.setText("UPS: " + updates + ", FPS: " + frames);
                
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
        guiManager.update();
    }
    
    public void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }
	
	    screen.clear();
	    level.render(screen);
        
        screen.drawPixel(input.getMouseLevelX(level), input.getMouseLevelY(level), 0xff00ff, false);
	    for (int i = 0; i < screen.pixels.length; i++) {
		    pixels[i] = screen.pixels[i];
	    }
	
	    Graphics g = bs.getDrawGraphics();
	    g.setColor(Color.RED);
	    g.setFont(gameFont);
	    g.fillRect(0, 0, getWidth(), getHeight());
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        g.fillRect(input.getMouseX() - 1, input.getMouseY() - 1, scale, scale);
        guiManager.render(g);
        
        g.dispose();
        bs.show();
        
    }
    
	public static class GameHud extends GUIComponentGroup {
		
		public GUIText FPSInfo;
		
		public GameHud(GUIComponent parent) {
			super(parent, 0, 0);
			
			FPSInfo = new GUIText(this, 0, 0, "FPS: %, UPS: %", true, new Font(gameFont.getName(), Font.PLAIN, 8));
			FPSInfo.setColor(new Color(0xff00ff21, true), new Color(0xaa000000, true));
			addComponent(FPSInfo);
		}
	}
	
}
