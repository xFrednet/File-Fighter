package com.gmail.xfrednet.filefighter.graphics.gui.components;

import com.gmail.xfrednet.filefighter.Main;
import com.gmail.xfrednet.filefighter.entity.Entity;
import com.gmail.xfrednet.filefighter.graphics.Screen;
import com.gmail.xfrednet.filefighter.graphics.Sprite;
import com.gmail.xfrednet.filefighter.graphics.gui.GUIComponent;
import com.gmail.xfrednet.filefighter.item.Item;
import com.gmail.xfrednet.filefighter.util.Input;
import com.gmail.xfrednet.filefighter.util.MouseInteraction;

import java.awt.*;

/**
 * Created by xFrednet on 20.02.2016.
 */
public class GUIItemFrame extends GUIComponent implements MouseInteraction {
	
	public static final int SCALE1_SIZE = 18 * Main.scale;
	private static final int HOVER_TEXT_TIMER = 10;
	
	int scale;
	Item item;
	
	//hover text
	boolean hasHoverText = false;
	boolean showHoverText = false;
	int mouseX;
	int mouseY;
	String hoverText;
	
	/*
	* Constructor
	* */
	public GUIItemFrame(GUIComponent parent, int x, int y, Item item) {
		this(parent, x, y, item, 1);
	}
	public GUIItemFrame(GUIComponent parent, int x, int y, Item item, int scale) {
		super(parent, x, y, SCALE1_SIZE * scale, SCALE1_SIZE * scale);
		this.scale = scale;
		this.item = item;
	}
	
	/*
	* Util
	* */
	@Override
	public void render(Screen screen) {
		screen.drawSprite(screenX / Main.scale, screenY / Main.scale, Sprite.itemFrame, true, scale);
		if (item != null) {
			screen.drawSprite(screenX / Main.scale + 1, screenY / Main.scale + 1, item.getItemSprite(), true, scale);
		}
	}
	
	@Override
	public void render(Graphics g) {
		super.render(g);
		if (showHoverText) {
			g.setFont(Main.gameFont);
			g.drawString(hoverText, getScreenX() + mouseX, getScreenY() + mouseY);
		}
	}
	
	@Override
	public void mouseWaits(int x, int y, int time) {
		if (hasHoverText) {
			if (time >= HOVER_TEXT_TIMER) {
				showHoverText = true;
			}
		}
	}
	
	public void addHoverText(String hoverText, Input input) {
		hasHoverText = true;
		this.hoverText = hoverText;
		
		input.addMouseInteraction(this, screenX, screenY, width, height);
	}
	
	/*
	* MouseInteraction
	* */
	@Override
	public void mouseEntered(int x, int y) {}
	
	@Override
	public void mouseExited(int x, int y) {}
	
	@Override
	public void mousePressed(int x, int y, int button) {}
	
	@Override
	public void mouseReleased(int x, int y, int button) {}
	
	@Override
	public void mouseMoved(int x, int y) {
		if (hasHoverText) {
			showHoverText = false;
		}
	}
}
