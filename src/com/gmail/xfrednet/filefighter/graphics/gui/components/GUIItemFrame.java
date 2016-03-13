package com.gmail.xfrednet.filefighter.graphics.gui.components;

import com.gmail.xfrednet.filefighter.Main;
import com.gmail.xfrednet.filefighter.graphics.Sprite;
import com.gmail.xfrednet.filefighter.graphics.gui.GUIComponent;
import com.gmail.xfrednet.filefighter.item.Item;
import com.gmail.xfrednet.filefighter.item.ItemStorage;
import com.gmail.xfrednet.filefighter.util.Input;
import com.gmail.xfrednet.filefighter.util.MouseInteraction;

import java.awt.*;

/**
 * Created by xFrednet on 20.02.2016.
 */
public class GUIItemFrame extends GUIComponent implements MouseInteraction {
	
	public static final int SIZE = 18 * Main.scale;
	public static final int SPRITE_SIZE = 18;
	
	//types
	public static final int TYPE_COUNT = 8;
	public static final int TYPE_ITEM = 0;
	//armor
	public static final int TYPE_HELMET = 1;
	public static final int TYPE_CHESTPLATE = 2;
	public static final int TYPE_PENTS = 3;
	public static final int TYPE_SHOES = 4;
	//accessories
	public static final int TYPE_NECKLACE = 5;
	public static final int TYPE_RING = 6;
	public static final int TYPE_BRACELET = 7;
	
	Item item;
	Sprite itemSprite;

	int type;
	
	boolean containsMouse = false;
	//itemStorage
	ItemStorage storage;
	int slot;
	boolean hasStorage = false;
	
	/*
	* Constructor
	* */
	public GUIItemFrame(GUIComponent parent, int x, int y, Item item) {
		this(parent, x, y, item, TYPE_ITEM);
	}
	public GUIItemFrame(GUIComponent parent, int x, int y, Item item, int type) {
		super(parent, x, y, SIZE, SIZE);
		setItem(item);
		this.type = type;
	}
	public void addItemStorageFunction(ItemStorage storage, int slot, Input input) {
		this.storage = storage;
		this.slot = slot;
		input.addMouseInteraction(this, screenX, screenY, width - 1, height - 1);
		hasStorage = true;
	}
	
	/*
	* Util
	* */
	@Override
	public void render(Graphics g) {
		super.render(g);
		
		if (itemSprite != null) {
			g.drawImage(Sprite.itemFrame[TYPE_ITEM].getImage(), screenX, screenY, screenX + width, screenY + height, Sprite.itemFrame[TYPE_ITEM].getImageX(), Sprite.itemFrame[TYPE_ITEM].getImageY(), Sprite.itemFrame[TYPE_ITEM].getImageMaxX(), Sprite.itemFrame[TYPE_ITEM].getImageMaxY(), null);
			g.drawImage(itemSprite.getImage(), screenX + Main.scale, screenY + Main.scale, screenX + width - Main.scale, screenY + height - Main.scale, itemSprite.getImageX(), itemSprite.getImageY(), itemSprite.getImageMaxX(), itemSprite.getImageMaxY(), null);
		} else {
			g.drawImage(Sprite.itemFrame[type].getImage(), screenX, screenY, screenX + width, screenY + height, Sprite.itemFrame[type].getImageX(), Sprite.itemFrame[type].getImageY(), Sprite.itemFrame[type].getImageMaxX(), Sprite.itemFrame[type].getImageMaxY(), null);
		}
		
	}
	
	public void setItem(Item item) {
		this.item = item;
		
		if (item != null) {
			itemSprite = item.getItemSprite();
		} else {
			itemSprite = null;
		}
		
		if (containsMouse) {
			if (parent instanceof ItemStorage.GUIItemStorage) {
				((ItemStorage.GUIItemStorage) parent).setItemInfo(item);
			}
		}
		
	}
	
	/*
	* MouseInteraction
	* */
	@Override
	public void mouseEntered(int x, int y) {
		containsMouse = true;
		if (parent instanceof ItemStorage.GUIItemStorage) {
			((ItemStorage.GUIItemStorage) parent).setItemInfo(item);
		}
	}
	
	@Override
	public void mouseExited(int x, int y) {
		containsMouse = false;
	}
	
	@Override
	public void mousePressed(int x, int y, int button) {
		if (parent.getVisibility() && hasStorage) {
			storage.mouseInteraction(slot, this);
		}
	}
	
	@Override
	public void mouseReleased(int x, int y, int button) {}
	
	@Override
	public void mouseWaits(int x, int y, int time) {}
	
	@Override
	public void mouseMoved(int x, int y) {}
}

