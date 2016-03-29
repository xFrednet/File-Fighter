package com.gmail.xfrednet.filefighter.graphics.gui.groups;

import com.gmail.xfrednet.filefighter.entity.LivingEntity;
import com.gmail.xfrednet.filefighter.entity.Player;
import com.gmail.xfrednet.filefighter.graphics.gui.GUIComponent;
import com.gmail.xfrednet.filefighter.graphics.gui.GUIComponentGroup;
import com.gmail.xfrednet.filefighter.graphics.gui.components.GUIBackground;
import com.gmail.xfrednet.filefighter.graphics.gui.components.GUIHealthBar;
import com.gmail.xfrednet.filefighter.graphics.gui.components.GUIStaminaBar;

import java.awt.*;

/**
 * Created by xFrednet on 01.03.2016.
 */
public class GUILivingEntityStatusBar extends GUIComponentGroup {
	
	private static final int PADDING = 3;
	
	public static final int DEFAULT_GUI_WIDTH = 250;
	public static final int DEFAULT_GUI_HEIGHT = 49;
	
	private static final int POTION_INFO_X_OFFSET = DEFAULT_GUI_WIDTH;
	private static final int POTION_INFO_Y_OFFSET = 0;
	
	private static final Color BACKGROUND_COLOR = new Color(0xcc757575, true);
	
	public GUILivingEntityStatusBar(GUIComponent parent, int x, int y, LivingEntity entity) {
		this(parent, x, y, DEFAULT_GUI_WIDTH, DEFAULT_GUI_HEIGHT, entity);
	}
	public GUILivingEntityStatusBar(GUIComponent parent, int x, int y, int width, int height, LivingEntity entity) {
		super(parent, x, y, width, height);
		
		int componentWidth = width - PADDING * 2;
		int componentsHeight = (height - PADDING * 3) / 2;
		
		addComponent(new GUIBackground(this, 0, 0, DEFAULT_GUI_WIDTH, DEFAULT_GUI_HEIGHT, BACKGROUND_COLOR));

		addComponent(new GUIHealthBar(this, PADDING, PADDING, componentWidth, componentsHeight, entity, 2));

		addComponent(new GUIStaminaBar(this, PADDING, PADDING * 2 + componentsHeight, componentWidth, componentsHeight, entity, 2));
		
		if (entity instanceof Player) {
			addComponent(new GUIPotionInfo(this, POTION_INFO_X_OFFSET, POTION_INFO_Y_OFFSET, (Player) entity));
		}
	}
}
