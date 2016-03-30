package com.gmail.xfrednet.filefighter.graphics;

import com.gmail.xfrednet.filefighter.graphics.gui.GUIComponent;
import com.gmail.xfrednet.filefighter.graphics.gui.GUIComponentGroup;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xFrednet on 09.02.2016.
 */
public class GUIManager extends GUIComponentGroup {
	
	private int canvasWidth;
	private int canvasHeight;
	
	private GUIComponent playerGUI = null;
	
	public GUIManager(int canvasWidth, int canvasHeight) {
		super(canvasWidth, canvasHeight);
		
		if (components == null) {
			components = new ArrayList<>();
		}
		
	}
	
	public void addPlayerGUI(GUIComponent playerGUI) {
		this.playerGUI = playerGUI;
	}
	
	@Override
	public void render(Graphics g) {
		super.render(g);
		
		playerGUI.render(g);
	}
	
	@Override
	public void render(Screen screen) {
		super.render(screen);
		
		playerGUI.render(screen);
	}
	
	@Override 
	public void update() {
		super.update();
		
		playerGUI.update();
	}
	
	/*
	* Overridden
	* */
	public void updateBounds() {
		screenX = 0;
		screenY = 0;
		
		//Width
		width = preferredWidth;
		
		//Height
		height = preferredHeight;
		
		if (components == null) {
			components = new ArrayList<>();
		}
		
		for (int i = 0; i < components.size(); i++) {
			if (!components.get(i).isShown()) continue;
			
			components.get(i).updateBounds();
			
		}
		
	}

}
