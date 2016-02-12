package com.gmail.xfrednet.filefighter.util;

import com.gmail.xfrednet.filefighter.Main;
import com.gmail.xfrednet.filefighter.level.Level;


import java.awt.event.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * Created by xFrednet on 06.02.2016.
 */
public class Input implements KeyListener, MouseMotionListener, MouseListener {
	
	public static int LEFT_MOUSE_BUTTON = 0;
	
	private static boolean[] keyDown = new boolean[256];
	private static boolean[] mouseButtonsDown = new boolean[5];
	private static int mouseX = 0;
	private static int mouseY = 0;
	
	/*
	* util
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
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON1) 
				mouseButtonsDown[LEFT_MOUSE_BUTTON] = false;
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
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}
}

