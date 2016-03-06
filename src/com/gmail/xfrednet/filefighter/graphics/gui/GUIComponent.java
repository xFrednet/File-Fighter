package com.gmail.xfrednet.filefighter.graphics.gui;

import com.gmail.xfrednet.filefighter.Main;
import com.gmail.xfrednet.filefighter.graphics.Screen;

import java.awt.*;

/**
 * Created by xFrednet on 09.02.2016.
 */
public class GUIComponent {
	
	public static final int NO_BOUNDS_GIVEN = 0;
	public static final int MATCH_PARENT = -1;
	public static final int GRAVITY_CENTER = -1000000;
	
	//font
	public static final Font HEADLINE_FONT = new Font(Main.gameFont.getName(), Font.BOLD, 32);
	public static final Font SMALL_HEADLINE_FONT = new Font(Main.gameFont.getName(), Font.BOLD, 24);
	public static final Font FONT = new Font(Main.gameFont.getName(), Font.BOLD, Main.gameFont.getSize());
	public static final Font INFO_FONT = new Font(Main.gameFont.getName(), Font.PLAIN, (int) (Main.gameFont.getSize() * 0.9));
	
	//colors
	public static final Color BACKGROUND_COLOR = new Color(0xffacacac, true);
	public static final Color TEXT_COLOR = new Color(0xff606060, true);
	public static final Color SKILL_POINT_CATEGORY_SEPARATOR_COLOR = new Color(0xff757575, true);
	
	//size
	public static final int PADDING = 6;
	
	private static int current_ID = Integer.MIN_VALUE;
	
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	protected int preferredWidth;
	protected int preferredHeight;
	protected int screenX;
	protected int screenY;
	protected int ID;
	protected int padding;
	protected boolean show;
	protected GUIComponent parent;
	
	public GUIComponent(GUIComponent parent, int x, int y) {
		this(parent, x, y, NO_BOUNDS_GIVEN, NO_BOUNDS_GIVEN);
	}
	public GUIComponent(GUIComponent parent, int x, int y, int width, int height) {
		this(parent, x, y, width, height, 0);
	}
	public GUIComponent(GUIComponent parent, int x, int y, int width, int height, int padding) {
		if (parent == null) {
			System.out.println("[ERROR] GUIComponent: Parent == null (constructor)");
		} 
		
		this.padding = padding;
		ID = current_ID++;
		
		this.parent = parent;
		show = true;
		
		setBounds(x, y, width, height);
		
	}
	public GUIComponent(int width, int height) {
		setBounds(0, 0, width, height);
		
		System.out.println("[INFO] GUIComponent: created GUIManager");
		
		show = true;
	}
	
	public GUIComponent setPadding(int padding) {
		this.padding = padding;
		updateBounds();
		return this;
	}
	
	/*
	* Util
	* */
	public void updateBounds() {
		if (parent == null) {
			System.out.println("[ERROR] GUIComponent: Parent == null (updateBounds())");
			return;
		}
		
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
		
		if (x > -1000000) {
			screenX = x + parent.getScreenX() + padding;
		} else {
			if (x == GRAVITY_CENTER) {
				screenX = parent.getWidth() / 2 - width / 2;
			}
		}
		if (y > -1000000) {
			screenY = y + parent.getScreenY() + padding;
		} else {
			if (y == GRAVITY_CENTER) {
				screenY = parent.getHeight() / 2 - height / 2;
			}
		}
	}
	
	/*
	* Game Loop Util
	* */
	public void render(Graphics g) {}
	public void render(Screen screen) {}
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
	public void below(int componentID) {
		GUIComponent component = parent.getComponentByID(componentID);
		if (component != null) {
			setBounds(x, component.getMaxY(), width, height);
		}
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
	public int getID() {
		return ID;
	}
	
	public GUIComponent getComponentByID(int ID) {
		return null;
	}
	
	public int getMaxX() {
		return x + width + padding * 2;
	}
	public int getMaxY() {
		return y + height + padding * 2;
	}
	
	public boolean getVisibility() {
		return show;
	}
	
	public int getWidthWithPadding() {
		return width + padding * 2;
	}
	public int getHeightWithPadding() {
		return height + padding * 2;
	}
	
}
