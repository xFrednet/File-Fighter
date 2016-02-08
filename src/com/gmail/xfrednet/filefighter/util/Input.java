package com.gmail.xfrednet.filefighter.util;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by xFrednet on 06.02.2016.
 */
public class Input implements KeyListener {
	
	private static boolean[] keyDown = new boolean[256];
	
	/*
	* util
	* */
	public boolean isKeyDown(int keyID) {
		if (keyID >= keyDown.length) return false;
		return keyDown[keyID];
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
}

