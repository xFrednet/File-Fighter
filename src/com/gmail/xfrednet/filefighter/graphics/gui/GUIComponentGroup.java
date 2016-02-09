package com.gmail.xfrednet.filefighter.graphics.gui;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xFrednet on 09.02.2016.
 */
public class GUIComponentGroup extends GUIComponent {
	
	protected List<GUIComponent> components = new ArrayList<GUIComponent>();
	
	public GUIComponentGroup(GUIComponent parent, int x, int y) {
		this(parent, x, y, NO_BOUNDS_GIVEN, NO_BOUNDS_GIVEN);
	}
	public GUIComponentGroup(GUIComponent parent, int x, int y, int width, int height) {
		super(parent, x, y, width, height);
		
		if (components == null) {
			components = new ArrayList<>();
		}
	}
	
	
	/*
	* Overridden
	* */
	@Override
	public void render(Graphics g) {
		for (int i = 0; i < components.size(); i++) {
			if (!components.get(i).isShown()) continue;
			
			components.get(i).render(g);
			
		}
	}
	
	public void update() {
		for (int i = 0; i < components.size(); i++) {
			if (!components.get(i).isShown()) continue;
			
			components.get(i).update();
			
		}
	}
	
	@Override
	public void updateBounds() {
		if (components == null) {
			components = new ArrayList<>();
		} 
		
		super.updateBounds();
		
		for (int i = 0; i < components.size(); i++) {
			if (components.get(i).isShown()) continue;
			
			components.get(i).updateBounds();
			
		}
	}
	
	/*
	* Util
	* */
	public void addComponent(GUIComponent component) {
		components.add(component);
	}
	
	public void removeComponent(GUIComponent component) {
		components.remove(component);
	}
	
}