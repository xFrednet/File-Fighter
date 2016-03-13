package com.gmail.xfrednet.filefighter.item;

import com.gmail.xfrednet.filefighter.entity.Player;
import com.gmail.xfrednet.filefighter.graphics.gui.GUIComponent;
import com.gmail.xfrednet.filefighter.graphics.gui.GUIComponentGroup;
import com.gmail.xfrednet.filefighter.graphics.gui.components.GUIBackground;
import com.gmail.xfrednet.filefighter.graphics.gui.components.GUIItemFrame;
import com.gmail.xfrednet.filefighter.graphics.gui.components.GUITitle;
import com.gmail.xfrednet.filefighter.graphics.gui.groups.GUIItemInfoFrame;
import com.gmail.xfrednet.filefighter.util.Input;
import com.gmail.xfrednet.filefighter.util.MouseInteraction;

import java.awt.*;
import java.util.Objects;

/**
 * Created by xFrednet on 05.03.2016.
 */
public class ItemStorage {
	
	public static final int STORAGE_FULL = -1;
	public static final String NO_NAME = "NO_NAME";
	
	protected ItemContainer[] items;
	protected int width;
	protected int height;
	protected String name;
	
	protected GUIItemStorage gui;
	protected Player player;
	
	/*
	* Constructor
	* */
	public ItemStorage(int width, int height, String name) {
		this(width, height, name, true);
	}
	public ItemStorage(int width, int height, String name, boolean initItemContainers) {
		this.width = width;
		this.height = height;
		this.name = name;
		
		items = new ItemContainer[width * height];
		
		//initialize ItemContainers
		if (initItemContainers) {
			for (int i = 0; i < items.length; i++) {
				items[i] = new ItemContainer();
			}
		}
	}
	
	/*
	* Util
	* */
	public void mouseInteraction(int slot, GUIItemFrame itemFrame) {
		if (player == null) {
			System.out.println("Player = null");
			return;
		}
		player.setInHandItem(switchItem(player.getInHandItem(), slot));
		
	}
	
	/*
	* setters
	* */
	public Item switchItem(Item item) {
		return switchItem(item, getFirstFreeSlot(item));
	}
	public Item switchItem(Item item, int slot) {
		if (slot < 0 || slot >= items.length) return item;
		
		Item returnItem = items[slot].switchItem(item);
		
		if (gui != null) {
			gui.updateItemSlot(slot);
		}
		
		return returnItem;
	}
	
	/*
	* getters
	* */
	public Item getItem(int slot) {
		if (slot < 0 || slot >= items.length) return null;
		return items[slot].getItem();
	}
	
	private int getItemFrameType(int slot) {
		if (slot < 0 || slot >= items.length) return 0;
		return items[slot].getItemFrameType();
	}
	
	public boolean isStorageFull() {
		for (int i = 0; i < items.length; i++) {
			if (items[i].getItem() == null) return false;
		}
		return true;
	}
	public int getFirstFreeSlot(Item item) {
		for (int i = 0; i < items.length; i++) {
			if (items[i].isEmpty() && items[i].isSwitchPossible(item)) return i;
		} 
		return STORAGE_FULL;
	}
	
	public GUIItemStorage getGUI(GUIComponent parent, int x, int y, Player player) {
		if (gui == null) {
			this.player = player;
			gui = new GUIItemStorage(parent, x, y, player.getInput());
		} else {
			gui.updateItemFrames();
		}
		
		return gui;
	}
	
	/*
	* Util class
	* */
	public class GUIItemStorage extends GUIComponentGroup implements MouseInteraction {
		
		public static final int PADDING = 6;
		
		GUIItemFrame[] itemFrames;
		GUIItemInfoFrame itemInfo;
		
		public GUIItemStorage(GUIComponent parent, int x, int y, Input input) {
			super(parent, x, y, GUIItemFrame.SIZE * ItemStorage.this.width + PADDING * 2, (name != NO_NAME) ? GUIItemFrame.SIZE * ItemStorage.this.height + PADDING + GUITitle.HEIGHT : GUIItemFrame.SIZE * ItemStorage.this.height + PADDING * 2, PADDING);
			
			init(input);
			input.addMouseInteraction(this, screenX, screenY, width, height);
		}
		
		private void init(Input input) {
			int componentY = 0;
			
			if (!Objects.equals(name, NO_NAME)) {
				addComponent(new GUIBackground(this));
				
				addComponent(new GUITitle(this, 0, 0, name));
				componentY += GUITitle.HEIGHT;
			} else {
				addComponent(new GUIBackground(this).setShowSeparator(false));
				componentY += PADDING;
			}
			
			//itemFrames
			itemFrames = new GUIItemFrame[items.length];
			
			for (int y = 0; y < ItemStorage.this.height; y++) {
				for (int x = 0; x < ItemStorage.this.width; x++) {
					itemFrames[x + y * ItemStorage.this.width] = new GUIItemFrame(this, PADDING + GUIItemFrame.SIZE * x, componentY, getItem(x + y * ItemStorage.this.width), getItemFrameType(x + y * ItemStorage.this.width));
					
					if (input != null) {
						itemFrames[x + y * ItemStorage.this.width].addItemStorageFunction(ItemStorage.this, x + y * ItemStorage.this.width, input);
					}
					
					addComponent(itemFrames[x + y * ItemStorage.this.width]);
				}
				componentY += GUIItemFrame.SIZE;
			}
		}
		
		/*
		* Util
		* */
		protected void updateItemFrames() {
			for (int i = 0; i < itemFrames.length; i++) {
				updateItemSlot(i);
			}
		}
		
		protected void updateItemSlot(int slot) {
			if (slot < 0 || slot >= itemFrames.length) return;
			
			itemFrames[slot].setItem(items[slot].getItem());
		}
		
		@Override
		public void render(Graphics g) {
			super.render(g);
			
			if (itemInfo != null) {
				itemInfo.render(g);
			}
		}
		
		/*
		* setters
		* */
		public void setItemInfo(Item item) {
			if (item != null) {
				itemInfo = item.getGUIItemInfoFrame(this, width, 0);
			} else {
				itemInfo = null;
			}
		}
		
		/*
		* Mouse Interaction
		* */
		@Override
		public void mouseEntered(int x, int y) {}
		
		@Override
		public void mouseExited(int x, int y) {
			itemInfo = null;
		}
		
		@Override
		public void mousePressed(int x, int y, int button) {}
		
		@Override
		public void mouseReleased(int x, int y, int button) {}
		
		@Override
		public void mouseWaits(int x, int y, int time) {}
		
		@Override
		public void mouseMoved(int x, int y) {}
	}
	
}
