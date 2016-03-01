package com.gmail.xfrednet.filefighter.graphics.gui.groups;

import com.gmail.xfrednet.filefighter.entity.LivingEntity;
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
	
	public static final int DEFAULT_GUI_WIDTH = 250;
	public static final int DEFAULT_GUI_HEIGHT = 52;
	
	private static final int PADDING = 3;

	private static final Color BACKGROUND_COLOR = new Color(0xcc757575, true);
	
	LivingEntity entity;
	
	//GUIComponents
	GUIBackground background;
	GUIHealthBar healthBar;
	GUIStaminaBar staminaBar;
	
	int componentWidth;
	int componentsHeight;
	
	public GUILivingEntityStatusBar(GUIComponent parent, int x, int y, LivingEntity entity) {
		this(parent, x, y, DEFAULT_GUI_WIDTH, DEFAULT_GUI_HEIGHT, entity);
	}
	public GUILivingEntityStatusBar(GUIComponent parent, int x, int y, int width, int height, LivingEntity entity) {
		super(parent, x, y, width, height);
		
		this.entity = entity;
		componentWidth = width - PADDING * 2;
		componentsHeight = (height - PADDING * 3) / 2;
		
		background = new GUIBackground(this, 0, 0, MATCH_PARENT, MATCH_PARENT, BACKGROUND_COLOR);
		addComponent(background);
		
		healthBar = new GUIHealthBar(this, PADDING, PADDING, componentWidth, componentsHeight, entity, 2);
		addComponent(healthBar);
		
		staminaBar = new GUIStaminaBar(this, PADDING, PADDING * 2 + componentsHeight, componentWidth, componentsHeight, entity, 2);
		addComponent(staminaBar);
	}
}
