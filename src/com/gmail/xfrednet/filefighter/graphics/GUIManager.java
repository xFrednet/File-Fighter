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
	
	protected List<GUIComponent> components;
	
	public GUIManager(int canvasWidth, int canvasHeight) {
		super(canvasWidth, canvasHeight);
		
		if (components == null) {
			components = new ArrayList<>();
		}
		
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
	
	/*
	* Util
	* */
	public void setBounds(int width, int height) {
		setBounds(0, 0, width, height);
	}

}
