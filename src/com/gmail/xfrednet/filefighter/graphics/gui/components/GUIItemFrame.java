package com.gmail.xfrednet.filefighter.graphics.gui.components;

import com.gmail.xfrednet.filefighter.Main;
import com.gmail.xfrednet.filefighter.graphics.Sprite;
import com.gmail.xfrednet.filefighter.graphics.gui.GUIComponent;
import com.gmail.xfrednet.filefighter.item.Item;

import java.awt.*;

/**
 * Created by xFrednet on 20.02.2016.
 */
public class GUIItemFrame extends GUIComponent {
	
	public static final int SIZE = 18 * Main.scale;
	public static final int SPRITE_SIZE = 18;
	
	//types
	public static final int TYPE_COUNT = 5;
	public static final int TYPE_ITEM = 0;
	public static final int TYPE_HELMET = 1;
	public static final int TYPE_CHESTPLATE = 2;
	public static final int TYPE_PENTS = 3;
	public static final int TYPE_SHOES = 4;
	
	Item item;
	Sprite s;
	int type;
	
	//hover text
	boolean hasHoverText = false;
	boolean showHoverText = false;
	
	/*
	* Constructor
	* */
	public GUIItemFrame(GUIComponent parent, int x, int y, Item item) {
		this(parent, x, y, item, TYPE_ITEM);
	}
	public GUIItemFrame(GUIComponent parent, int x, int y, Item item, int type) {
		super(parent, x, y, SIZE, SIZE);
		this.item = item;
		this.type = type;
		s = Sprite.itemFrame[type];
	}
	
	/*
	* Util
	* */
	@Override
	public void render(Graphics g) {
		super.render(g);
		g.drawImage(s.getImage(), screenX, screenY, screenX + width, screenY + height, s.getImageX(), s.getImageY(), s.getImageMaxX(), s.getImageMaxY(), null);
	}
	
}
