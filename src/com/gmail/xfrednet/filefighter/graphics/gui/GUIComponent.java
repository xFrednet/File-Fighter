package com.gmail.xfrednet.filefighter.graphics.gui;

import java.awt.*;

/**
 * Created by xFrednet on 09.02.2016.
 */
public abstract class GUIComponent {
	
	public static final int NO_BOUNDS_GIVEN = 0;
	public static final int MATCH_PARENT = -1;
	
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	protected int preferredWidth;
	protected int preferredHeight;
	protected int screenX;
	protected int screenY;
	protected boolean show;
	protected GUIComponent parent;
	
	public GUIComponent(GUIComponent parent, int x, int y) {
		this(parent, x, y, NO_BOUNDS_GIVEN, NO_BOUNDS_GIVEN);
	}
	
	public GUIComponent(GUIComponent parent, int x, int y, int width, int height) {
		if (parent == null) {
			System.out.println("[ERROR] GUIComponent: Parent == null (constructor)");
		} 
		
		this.parent = parent;
		show = true;
		
		setBounds(x, y, width, height);
		
	}
	
	public GUIComponent(int width, int height) {
		setBounds(0, 0, width, height);
		
		System.out.println("[INFO] GUIComponent: created GUIManager");
		
		show = true;
	}
	
	/*
	* Util
	* */
	public void updateBounds() {
		if (parent == null) {
			System.out.println("[ERROR] GUIComponent: Parent == null (updateBounds())");
			return;
		}
		
		screenX = x + parent.getScreenX();
		screenY = y + parent.getScreenY();
		
		//Width
		if (preferredWidth > 0) {
			width = preferredWidth;
		} else {
			if (preferredWidth == NO_BOUNDS_GIVEN) {
				width = 0;
			} else if (preferredWidth == MATCH_PARENT) {
				width = parent.getWidth();
			}
		}
		
		//Height
		if (preferredHeight > 0) {
			height = preferredHeight;
		} else {
			if (preferredWidth == NO_BOUNDS_GIVEN) {
				height = 0;
			} else if (preferredWidth == MATCH_PARENT) {
				height = parent.getHeight();
			}
		}
	}
	
	/*
	* Game Loop Util
	* */
	abstract public void render(Graphics g);
	public void update() {}
	
	/*
	* Setters
	* */
	public void setBounds(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		
		this.preferredWidth = width;
		this.preferredHeight = height;
		
		updateBounds();
	}
	
	/*
	* getters
	* */
	public int getScreenX() {
		return screenX;
	}
	public int getScreenY() {
		return screenY;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	
	public boolean isShown() {
		return show;
	}
	public void setVisible(boolean show) {
		this.show = show;
	}
}
