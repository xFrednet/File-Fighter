package com.gmail.xfrednet.filefighter;

import com.gmail.xfrednet.filefighter.entity.Entity;
import com.gmail.xfrednet.filefighter.entity.LivingEntity;
import com.gmail.xfrednet.filefighter.entity.Player;
import com.gmail.xfrednet.filefighter.graphics.Camera;
import com.gmail.xfrednet.filefighter.graphics.GUIManager;
import com.gmail.xfrednet.filefighter.graphics.Screen;
import com.gmail.xfrednet.filefighter.graphics.gui.GUIComponent;
import com.gmail.xfrednet.filefighter.graphics.gui.GUIComponentGroup;
import com.gmail.xfrednet.filefighter.graphics.gui.components.*;
import com.gmail.xfrednet.filefighter.graphics.gui.groups.GUILivingEntityEquipment;
import com.gmail.xfrednet.filefighter.graphics.gui.groups.GUILivingEntityStatusBar;
import com.gmail.xfrednet.filefighter.level.FileLevel;
import com.gmail.xfrednet.filefighter.level.Level;
import com.gmail.xfrednet.filefighter.util.Input;
import com.sun.glass.events.KeyEvent;

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
    public static final String LEVEL_LOCATION = (System.getProperty("user.home").endsWith("xFrednet")) ? "C:\\Users\\xFrednet\\IdeaProjects\\File-Fighter\\level" : System.clearProperty("user.home") + "\\Desktop";
    /*"Alter ich Psycholog(y)ier dich gleich mal"*/
	
	public static final Font gameFont = new Font("Lucida Console", Font.PLAIN, 16);
    
    Thread thread;
    public static JFrame jframe;
    public static Input input;
    
    //Graphics
    Screen screen;
    Camera camera;
    GUIManager guiManager;
	public static GameHud hud;
    
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
    
        jframe = new JFrame(NAME);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setResizable(false);
        jframe.setFocusable(false);
        jframe.add(main);
        jframe.pack();
    
        input.setDefaultCursor();
    
        jframe.setLocationRelativeTo(null);
        jframe.show();
    
        main.start();
    }
    
    /*
	* Game loop 
	* */
    public void start() {
        guiManager = new GUIManager(getWidth(), getHeight());
	    hud = new GameHud(guiManager, player);
	    guiManager.addComponent(hud);
    
        level = new FileLevel(player, input, screen, new File(LEVEL_LOCATION), guiManager);
        camera = level.getCamera();
        
        thread = new Thread(this);
        thread.setName("Game Loop");
        thread.start();
    
    }
    
    public synchronized void stop() {
        System.exit(0);
    }
    
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
        input.update();
        camera.update();
        player.update(level);
        level.update();
        guiManager.update();
        debugUpdate();
        
        if (input.isKeyDown(KeyEvent.VK_ESCAPE)) {
            running = false;
        }
        
    }
    private void debugUpdate() {
        if (input.isKeyDown(KeyEvent.VK_F3)) {
            if (input.isKeyDown(KeyEvent.VK_B)) Entity.showBoundingBoxes = !Entity.showBoundingBoxes;
        }
    }
    
    public void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }
	
	    screen.clear();
	    level.render(screen);
        
        Graphics g = bs.getDrawGraphics();
        guiManager.render(screen);
        
	    for (int i = 0; i < screen.pixels.length; i++) {
		    pixels[i] = screen.pixels[i];
	    }
	
	    
	    g.setColor(Color.RED);
	    g.setFont(gameFont);
	    g.fillRect(0, 0, getWidth(), getHeight());
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        guiManager.render(g);
        
        g.dispose();
        bs.show();
        
    }
    
	public static class GameHud extends GUIComponentGroup {
		
        public LivingEntity entity;
		
        /*
        * GUIComponents
        * */
        public GUIText FPSInfo;
		public GUILivingEntityStatusBar healthBar;
        
        /*
        * Constructor
        * */
		public GameHud(GUIComponent parent, LivingEntity entity) {
			super(parent, 0, 0, MATCH_PARENT, MATCH_PARENT);
			
			FPSInfo = new GUIText(this, 0, 0, "FPS: %, UPS: %", true, new Font(gameFont.getName(), Font.PLAIN, 8));
			FPSInfo.setColor(new Color(0xff00ff21, true), new Color(0xaa000000, true));
			addComponent(FPSInfo);
            
            
            this.entity = entity;
            addComponent(healthBar = new GUILivingEntityStatusBar(this, 20, 20, entity));
		}
	}
	
}
