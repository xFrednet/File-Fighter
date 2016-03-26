package com.gmail.xfrednet.filefighter.item.itemstorage;

import com.gmail.xfrednet.filefighter.entity.LivingEntity;
import com.gmail.xfrednet.filefighter.graphics.gui.components.GUIItemFrame;
import com.gmail.xfrednet.filefighter.item.Item;
import com.gmail.xfrednet.filefighter.item.ItemStorage;
import com.gmail.xfrednet.filefighter.level.Level;
import com.gmail.xfrednet.filefighter.util.Input;

/**
 * Created by xFrednet on 26.03.2016 at 19:14.
 */
public class ToolBar extends ItemStorage {
	
	public static final int WIDTH = 4;
	public static final int HEIGHT = 1;
	
	public static final int GUI_WIDTH = GUIItemFrame.SIZE * WIDTH + GUIItemStorage.PADDING * 2;
	public static final int GUI_HEIGHT = GUIItemFrame.SIZE * HEIGHT + GUIItemStorage.PADDING * 3;
	
	int selectedSlot = 0;
	Input input;
	
	public ToolBar(Input input) {
		super(WIDTH, HEIGHT, NO_NAME);
		this.input = input;
	}
	
	/*
	* Util
	* */
	public boolean useItemPrimaryAction(Level level, LivingEntity executingEntity, double angle) {
		Item item;
		if ((item = getSelectedItem()) != null) {
			return item.usePrimaryAction(level, executingEntity, angle);
		}
		return true;
	}
	public boolean useItemSecondaryAction(Level level, LivingEntity executingEntity, double angle) {
		Item item;
		if ((item = getSelectedItem()) != null) {
			return item.useSecondaryAction(level, executingEntity, angle);
		}
		return true;
	}
	
	public void update(Level level) {
		Item item;
		for (int i = 0; i < items.length; i++) {
			if ((item = getItem(i)) != null) {
				item.update(level);
			}
		}
	}
	
	/*
	* Getters
	* */
	public Item getSelectedItem() {
		return getItem(selectedSlot);
	}
	@Override
	protected boolean getDefaultLock() {
		return true;
	}
	
}
