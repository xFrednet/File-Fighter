package com.gmail.xfrednet.filefighter.graphics.gui.components;

import com.gmail.xfrednet.filefighter.entity.LivingEntity;
import com.gmail.xfrednet.filefighter.graphics.gui.GUIComponent;

import java.awt.*;

/**
 * Created by xFrednet on 01.03.2016.
 */
public class GUIStaminaBar extends GUIComponent {
	
	public static final Color PROGRESS_COLOR = new Color(0xff00aaff, true);
	public static final Color BACKGROUND_COLOR = new Color(0xff757575);
	
	LivingEntity entity;
	GUIProgressBar staminaBar;
	
	public GUIStaminaBar(GUIComponent parent, int x, int y, int width, int height, LivingEntity entity) {
		this(parent, x, y, width, height, entity, 3);
	}
	
	public GUIStaminaBar(GUIComponent parent, int x, int y, int width, int height, LivingEntity entity, int padding) {
		super(parent, x, y, width, height);
		
		this.entity = entity;
		
		staminaBar = new GUIProgressBar(this, 0, 0, MATCH_PARENT, MATCH_PARENT);
		staminaBar.setProgressColor(PROGRESS_COLOR);
		staminaBar.setBackground(BACKGROUND_COLOR, padding);
		staminaBar.setInfo("Stamina");
		staminaBar.showNumbers(true);
		
	}
	
	@Override
	public void update() {
		super.update();
		
		staminaBar.setProgress(entity.getStamina());
		if (staminaBar.getMaxProgress() != entity.getAttribute(LivingEntity.ATTRIBUTE_MAX_STAMINA)) {
			staminaBar.setMaxProgress(entity.getAttribute(LivingEntity.ATTRIBUTE_MAX_STAMINA));
		}
		
	}
	
	@Override
	public void render(Graphics g) {
		super.render(g);
		
		staminaBar.render(g);
	}
}
