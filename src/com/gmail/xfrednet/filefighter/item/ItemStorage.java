package com.gmail.xfrednet.filefighter.item;

import com.gmail.xfrednet.filefighter.Main;
import com.gmail.xfrednet.filefighter.entity.Player;
import com.gmail.xfrednet.filefighter.graphics.gui.GUIComponent;
import com.gmail.xfrednet.filefighter.graphics.gui.GUIComponentGroup;
import com.gmail.xfrednet.filefighter.graphics.gui.components.GUIBackground;
import com.gmail.xfrednet.filefighter.graphics.gui.components.GUIItemFrame;
import com.gmail.xfrednet.filefighter.graphics.gui.components.GUITitle;
import com.gmail.xfrednet.filefighter.item.item.weapon.gun.PaperGun;
import com.gmail.xfrednet.filefighter.util.Input;

/**
 * Created by xFrednet on 05.03.2016.
 */
public class ItemStorage {
	
	public static final int STORAGE_FULL = -1;
	
	protected Item[] items;
	protected int width;
	protected int height;
	protected String name;
	
	protected GUIItemStorage gui;
	protected Player player;
	
	/*
	* Constructor
	* */
	public ItemStorage(int width, int height, String name) {
		this.width = width;
		this.height = height;
		
		this.name = name;
		
		items = new Item[width * height];
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
		return switchItem(item, getFirstFreeSlot());
	}
	public Item switchItem(Item item, int slot) {
		if (slot < 0 || slot >= items.length) return item;
		
		Item currentItem = items[slot];
		items[slot] = item;
		
		if (gui != null) {
			gui.updateItemSlot(slot);
		}
		
		return currentItem;
	}
	
	/*
	* getters
	* */
	public Item getItem(int slot) {
		if (slot < 0 || slot >= items.length) return null;
		return items[slot];
	}
	
	public boolean isStorageFull() {
		for (int i = 0; i < items.length; i++) {
			if (items[i] == null) return false;
		}
		return true;
	}
	public int getFirstFreeSlot() {
		for (int i = 0; i < items.length; i++) {
			if (items[i] == null) return i;
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
	public class GUIItemStorage extends GUIComponentGroup {
		
		public static final int PADDING = 6;
		
		GUIItemFrame[] itemFrames;
		
		public GUIItemStorage(GUIComponent parent, int x, int y, Input input) {
			super(parent, x, y, GUIItemFrame.SIZE * ItemStorage.this.width + PADDING * 2, GUIItemFrame.SIZE * ItemStorage.this.height + PADDING + GUITitle.HEIGHT, PADDING);
			
			init(Main.input);
		}
		
		private void init(Input input) {
			addComponent(new GUIBackground(this));
			addComponent(new GUITitle(this, 0, 0, name));
			
			//itemFrames
			itemFrames = new GUIItemFrame[items.length];
			
			int componentY;
			for (int y = 0; y < ItemStorage.this.height; y++) {
				componentY = GUITitle.HEIGHT + GUIItemFrame.SIZE * y;
				for (int x = 0; x < ItemStorage.this.width; x++) {
					itemFrames[x + y * ItemStorage.this.width] = new GUIItemFrame(this, PADDING + GUIItemFrame.SIZE * x, componentY, items[x + y * ItemStorage.this.width]);
					
					if (input != null) {
						itemFrames[x + y * ItemStorage.this.width].addItemStorageFunction(ItemStorage.this, x + y * ItemStorage.this.width, input);
					}
					
					addComponent(itemFrames[x + y * ItemStorage.this.width]);
				}
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
			
			itemFrames[slot].setItem(items[slot]);
		}
		
	}
	
}
