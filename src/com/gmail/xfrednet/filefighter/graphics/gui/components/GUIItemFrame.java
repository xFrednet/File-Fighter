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
	
	public static final int SIZE = (int) (18 * Main.scale * 1.5);
	public static final int SPRITE_SIZE = 18;
	
	private static final int COUNT_X = SIZE - 20;
	private static final int COUNT_Y = SIZE - 3;
	
	private static final Color COUNT_COLOR = new Color(0xdedede);
	
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
	
	private Item item;
	private Sprite itemSprite;
	private Sprite delaySprite;
	
	private int type;
	
	private boolean containsMouse = false;
	//itemStorage
	private ItemStorage storage;
	private int slot;
	private boolean hasStorage = false;
	private boolean locked = false;
	private boolean selected = false;
	
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
			g.drawImage(Sprite.GUI.itemFrame[TYPE_ITEM].getImage(), screenX, screenY, screenX + width, screenY + height, Sprite.GUI.itemFrame[TYPE_ITEM].getImageX(), Sprite.GUI.itemFrame[TYPE_ITEM].getImageY(), Sprite.GUI.itemFrame[TYPE_ITEM].getImageMaxX(), Sprite.GUI.itemFrame[TYPE_ITEM].getImageMaxY(), null);
			g.drawImage(itemSprite.getImage(), screenX + Main.scale, screenY + Main.scale, screenX + width - Main.scale, screenY + height - Main.scale, itemSprite.getImageX(), itemSprite.getImageY(), itemSprite.getImageMaxX(), itemSprite.getImageMaxY(), null);
			if (item.getCount() > 1) {
				g.setColor(COUNT_COLOR);
				g.setFont(INFO_FONT);
				g.drawString(item.getCount() + " ", screenX + COUNT_X, screenY + COUNT_Y);
			}
			if (delaySprite != null) {
				g.drawImage(delaySprite.getImage(), screenX, screenY, screenX + width, screenY + height, delaySprite.getImageX(), delaySprite.getImageY(), delaySprite.getImageMaxX(), delaySprite.getImageMaxY(), null);
			}
		} else {
			g.drawImage(Sprite.GUI.itemFrame[type].getImage(), screenX, screenY, screenX + width, screenY + height, Sprite.GUI.itemFrame[type].getImageX(), Sprite.GUI.itemFrame[type].getImageY(), Sprite.GUI.itemFrame[type].getImageMaxX(), Sprite.GUI.itemFrame[type].getImageMaxY(), null);
		}
		
		if (selected) {
			g.drawImage(Sprite.GUI.selected_item_frame.getImage(), screenX, screenY, screenX + width, screenY + height, Sprite.GUI.selected_item_frame.getImageX(), Sprite.GUI.selected_item_frame.getImageY(), Sprite.GUI.selected_item_frame.getImageMaxX(), Sprite.GUI.selected_item_frame.getImageMaxY(), null);
		}
		
	}
	@Override
	public void update() {
		if (item != null) {
			
			if (item.getUseTimer() > 0) {
				int delaySpriteIndex = (int) ((item.getUseTimer() /(double) item.getUseDelay()) * 10);
				delaySprite = Sprite.GUI.itemDelay[delaySpriteIndex];
			} else if (delaySprite != null) {
				delaySprite = null;
			}
			
			if (item.getCount() <= 0) {
				setItem(null);
			}
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
		
		delaySprite = null;
		
	}
	
	public void setSelected(boolean selected) {
		this.selected = selected;
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
		if (parent.getVisibility() && hasStorage && !locked) {
			storage.mouseInteraction(slot, this);
		}
	}
	
	@Override
	public void mouseReleased(int x, int y, int button) {}
	
	@Override
	public void mouseWaits(int x, int y, int time) {}
	
	@Override
	public void mouseMoved(int x, int y) {}
	
	public void setLocked(boolean locked) {
		this.locked = locked;
	}
}

