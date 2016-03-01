package com.gmail.xfrednet.filefighter.util;

import com.gmail.xfrednet.filefighter.Main;
import com.gmail.xfrednet.filefighter.level.Level;


import javax.imageio.stream.MemoryCacheImageOutputStream;
import java.awt.event.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xFrednet on 06.02.2016.
 */
public class Input implements KeyListener, MouseMotionListener, MouseListener {
	
	public static int LEFT_MOUSE_BUTTON = 0;
	
	private boolean[] keyDown = new boolean[256];
	private boolean[] mouseButtonsDown = new boolean[5];
	private int mouseX = 0;
	private int mouseY = 0;
	
	//wait function
	private int mouseLastUpdateX = 0;
	private int mouseLastUpdateY = 0;
	private int waitTime = 0;
	
	private List<MouseInteractionWrapper> mouseInteractions = new ArrayList<>();
	
	/*
	* util
	* */
	
	public void update() {
		if (mouseX == mouseLastUpdateX && mouseY == mouseLastUpdateY) {
			waitTime++;
			
			for (int i = 0; i < mouseInteractions.size(); i++) {
				mouseInteractions.get(i).mouseWaits(waitTime);
			}
			
		} else {
			waitTime = 0;
			mouseLastUpdateX = mouseX;
			mouseLastUpdateY = mouseY;
		}
	}
	  
	public void addMouseInteraction(MouseInteraction mouseInteraction, int x, int y, int width, int height) {
		MouseInteractionWrapper interactionWrapper = new MouseInteractionWrapper(x, y, width, height, mouseInteraction);
		mouseInteractions.add(interactionWrapper);
		
	}
	
	/*
	* Util Class
	* */
	private class MouseInteractionWrapper {
		
		int x;
		int y;
		int width;
		int height;
		
		boolean containsMouse = false;
		
		MouseInteraction interaction;
		
		public MouseInteractionWrapper(int x, int y, int width, int height, MouseInteraction mouseInteraction) {
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
			
			interaction = mouseInteraction;
		}
		
		public void mousePressed(int button) {
			if (containsMouse) interaction.mousePressed(getActionX(), getActionY(), button);
		}
		public void mouseReleased(int button) {
			if (containsMouse) interaction.mouseReleased(getActionX(), getActionY(), button);
		}
		
		public void mouseWaits(int time) {
			if (containsMouse) interaction.mouseWaits(getActionX(), getMouseY(), time);
		}
		
		public void mouseMoved() {
			if (contains()) {
				//enters
				if (!containsMouse) {
					containsMouse = true;
					interaction.mouseEntered(getActionX(), getActionY());
				}
				
				//mouse Moved
				interaction.mouseMoved(getActionX(), getActionY());
				
			} else {
				//exit
				if (containsMouse) {
					containsMouse = false;
					interaction.mouseExited(getActionX(), getActionY());
				}
			}
		}
		
		//util
		private boolean contains() {
			return mouseX > x && mouseX < x + width &&
					mouseY > y && mouseY < y + height;
		}
		
		private int getActionX() {
			return mouseX - x;
		}
		private int getActionY() {
			return mouseY - y;
		}
		
	}
	
	/*
	* getters
	* */
	public boolean isKeyDown(int keyID) {
		if (keyID >= keyDown.length) return false;
		return keyDown[keyID];
	}
	public boolean isMouseButtonDown(int buttonID) {
		return mouseButtonsDown[buttonID];
	}
	public int getMouseX() {
		return mouseX;
	}
	public int getMouseY() {
		return mouseY;
	}
	public int getMouseLevelX(Level level) {
		return level.getCamera().getXOffset() + (mouseX / Main.scale);
	}
	public int getMouseLevelY(Level level) {
		return level.getCamera().getYOffset() + mouseY / Main.scale;
	}
	
	
	
	/*
	* KeyListener Methods
	* */
	@Override
	public void keyTyped(KeyEvent e) {}
	
	@Override
	public void keyPressed(KeyEvent e) {
		//System.out.println("[INFO] Input: KeyEvent.getID:" + e.getKeyCode());
		if (e.getKeyCode() >= keyDown.length) return;
		keyDown[e.getKeyCode()] = true;
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() >= keyDown.length) return;
		keyDown[e.getKeyCode()] = false;
	}
	
	/*
	* MouseListener
	* */
	@Override
	public void mouseClicked(MouseEvent e) {}
	
	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) 
			mouseButtonsDown[LEFT_MOUSE_BUTTON] = true;
		
		for (int i = 0 ; i < mouseInteractions.size(); i++) {
			mouseInteractions.get(i).mousePressed(e.getButton());
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) 
			mouseButtonsDown[LEFT_MOUSE_BUTTON] = false;
		
		for (int i = 0 ; i < mouseInteractions.size(); i++) {
			mouseInteractions.get(i).mouseReleased(e.getButton());
		}
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {}
	
	@Override
	public void mouseExited(MouseEvent e) {}
	
	/*
	* MouseMotionListener 
	* */
	@Override
	public void mouseDragged(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		for (int i = 0 ; i < mouseInteractions.size(); i++) {
			mouseInteractions.get(i).mouseMoved();
		}
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		for (int i = 0 ; i < mouseInteractions.size(); i++) {
			mouseInteractions.get(i).mouseMoved();
		}
	}
	
	
	
}

