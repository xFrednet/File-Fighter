package com.gmail.xfrednet.filefighter.item.itemstorage;

import com.gmail.xfrednet.filefighter.entity.LivingEntity;
import com.gmail.xfrednet.filefighter.entity.Player;
import com.gmail.xfrednet.filefighter.graphics.gui.GUIComponent;
import com.gmail.xfrednet.filefighter.graphics.gui.components.GUIItemFrame;
import com.gmail.xfrednet.filefighter.item.Item;
import com.gmail.xfrednet.filefighter.item.ItemStorage;
import com.gmail.xfrednet.filefighter.level.Level;
import com.gmail.xfrednet.filefighter.util.Input;
import com.sun.glass.events.KeyEvent;

/**
 * Created by xFrednet on 26.03.2016 at 19:14.
 */
public class ToolBar extends ItemStorage {
	
	public static final int WIDTH = 4;
	public static final int HEIGHT = 1;
	
	public static final int GUI_WIDTH = GUIItemFrame.SIZE * WIDTH + GUIItemStorage.PADDING * 2;
	public static final int GUI_HEIGHT = GUIItemFrame.SIZE * HEIGHT + GUIItemStorage.PADDING * 3;
	private static final int SELECT_SLOT_0_KEY = KeyEvent.VK_1;
	private static final int SELECT_SLOT_1_KEY = KeyEvent.VK_2;
	private static final int SELECT_SLOT_2_KEY = KeyEvent.VK_3;
	private static final int SELECT_SLOT_3_KEY = KeyEvent.VK_4;
	
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
	
	@Override
	public void update(Level level) {
		super.update(level);
		Item item;
		for (int i = 0; i < items.length; i++) {
			if ((item = getItem(i)) != null) {
				item.update(level);
			}
		}
		
		if (input.getScrollDirection() == Input.SCROLL_DIRECTION_UP) {
			selectionUp();
		} else if (input.getScrollDirection() == Input.SCROLL_DIRECTION_DOWN) {
			selectionDown();
		}
		
		if (input.isKeyDown(SELECT_SLOT_0_KEY)) {
			select(0);
		} else if (input.isKeyDown(SELECT_SLOT_1_KEY)) {
			select(1);
		} else if (input.isKeyDown(SELECT_SLOT_2_KEY)) {
			select(2);
		} else if (input.isKeyDown(SELECT_SLOT_3_KEY)) {
			select(3);
		}
		
	}
	
	public void selectionUp() {
		int slot = selectedSlot + 1;
		if (slot >= items.length) {
			slot = 0;
		}
		select(slot);
	}
	public void selectionDown() {
		int slot = selectedSlot - 1;
		if (slot < 0) {
			slot = items.length - 1;
		}
		select(slot);
	}
	public void select(int slot) {
		selectedSlot = slot;
		if (gui != null) {
			gui.selectedItemFrame(selectedSlot);
		}
	}
	
	@Override
	public GUIItemStorage getGUI(GUIComponent parent, int x, int y, Player player) {
		GUIItemStorage gui = super.getGUI(parent, x, y, player);
		gui.selectedItemFrame(selectedSlot);
		return gui;
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
