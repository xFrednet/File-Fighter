package com.gmail.xfrednet.filefighter.item;

import com.gmail.xfrednet.filefighter.graphics.gui.components.GUIItemFrame;
import com.gmail.xfrednet.filefighter.item.item.equipment.Armor;
import com.gmail.xfrednet.filefighter.item.item.equipment.accessory.Bracelet;
import com.gmail.xfrednet.filefighter.item.item.equipment.accessory.Necklace;
import com.gmail.xfrednet.filefighter.item.item.equipment.accessory.Ring;
import com.gmail.xfrednet.filefighter.item.item.equipment.armor.Shoes;
import com.gmail.xfrednet.filefighter.item.item.equipment.armor.Chestplate;
import com.gmail.xfrednet.filefighter.item.item.equipment.armor.Helmet;
import com.gmail.xfrednet.filefighter.item.item.equipment.armor.Pents;
import com.gmail.xfrednet.filefighter.level.Level;
import com.sun.istack.internal.Nullable;

/**
 * Created by xFrednet on 10.03.2016.
 */
public class ItemContainer {
	
	public static final int EVERY_ITEM = 0;
	//armor
	public static final int ARMOR_HELMET = 1;
	public static final int ARMOR_CHESTPLATE = 2;
	public static final int ARMOR_PENTS = 3;
	public static final int ARMOR_SHOES = 4;
	public static final int ARMOR = 5;
	//accessories
	public static final int ACCESSORY_NECKLACE = 7;
	public static final int ACCESSORY_RING = 6;
	public static final int ACCESSORY_BRACELET = 8;
	
	Item item;
	int itemType;
	
	/*
	* constructor
	* */
	public ItemContainer() {
		this(EVERY_ITEM);
	}
	
	public ItemContainer(int itemType) {
		this(null, itemType);
	}
	
	public ItemContainer(Item item, int itemType) {
		this.item = item;
		this.itemType = itemType;
	}
	
	/*
	* util
	* */
	public Item switchItem(Item item) {
		if (!isSwitchPossible(item)) return item;
		
		Item returnItem;
		
		if (isAddPossible(item)) {
			returnItem = addItem(item);
		} else {
			returnItem = this.item;
			this.item = item;
		}
		return returnItem;
	}
	
	private Item addItem(@Nullable Item item) {
		if ((this.item.getCount() + item.getCount()) <= item.getMaxStackSize()) {
			this.item.addToCount(item.getCount());
			return null;
		} else {
			int leftOver = (this.item.getCount() + item.getCount()) - item.getMaxStackSize();
			this.item.addToCount(item.getCount() - leftOver);
			item.setCount(leftOver);
			return item;
		}
	}
	
	public void update(Level level) {
		if (item != null) {
			
			item.update(level);
			
			if (item.getCount() <= 0) {
				item = null;
			}
		}
	}
	
	/*
	* getters
	* */
	public boolean isSwitchPossible(Item item) {
		if (item == null || itemType == EVERY_ITEM) return true;
		
		switch (itemType) {
			case ARMOR: 
				return item instanceof Armor;
			case ARMOR_HELMET: 
				return item instanceof Helmet;
			case ARMOR_CHESTPLATE:
				return item instanceof Chestplate;
			case ARMOR_PENTS:
				return item instanceof Pents;
			case ARMOR_SHOES:
				return item instanceof Shoes;
			case ACCESSORY_NECKLACE:
				return item instanceof Necklace;
			case ACCESSORY_RING:
				return item instanceof Ring;
			case ACCESSORY_BRACELET:
				return item instanceof Bracelet;
			default: return false;
		}
	}
	public boolean isAddPossible(Item item) {
		if (this.item != null) {
			return (this.item.equals(item) && this.item.getCount() < this.item.getMaxStackSize());
		} else {
			return false;
		}
	}
	public boolean isEmpty() {
		return item == null;
	}
	
	public Item getItem() {
		return item;
	}
	
	public int getItemFrameType() {
		switch (itemType) {
			case ARMOR_HELMET:
				return GUIItemFrame.TYPE_HELMET;
			case ARMOR_CHESTPLATE:
				return GUIItemFrame.TYPE_CHESTPLATE;
			case ARMOR_PENTS:
				return GUIItemFrame.TYPE_PENTS;
			case ARMOR_SHOES:
				return GUIItemFrame.TYPE_SHOES;
			case ACCESSORY_NECKLACE:
				return GUIItemFrame.TYPE_NECKLACE;
			case ACCESSORY_RING:
				return GUIItemFrame.TYPE_RING;
			case ACCESSORY_BRACELET:
				return GUIItemFrame.TYPE_BRACELET;
			default:
				return GUIItemFrame.TYPE_ITEM;
		}
	}
	
	public int getItemCount(Class itemClass) {
		if (hasItemClass(itemClass)) {
			return item.getCount();
		}
		return 0;
	}
	public boolean hasItemClass(Class itemClass) {
		return (item != null) && (item.equals(itemClass));
	}
	
}
