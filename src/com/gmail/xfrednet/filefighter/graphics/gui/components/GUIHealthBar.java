package com.gmail.xfrednet.filefighter.graphics.gui.components;

import com.gmail.xfrednet.filefighter.Main;
import com.gmail.xfrednet.filefighter.entity.LivingEntity;
import com.gmail.xfrednet.filefighter.graphics.gui.GUIComponent;
import com.gmail.xfrednet.filefighter.graphics.gui.GUIComponentGroup;

import java.awt.*;

/**
 * Created by xFrednet on 29.02.2016.
 */
public class GUIHealthBar extends GUIComponent {
	
	public static final Color PROGRESS_COLOR = new Color(0xffff0000, true);
	public static final Color BACKGROUND_COLOR = new Color(0xff757575);
	
	private LivingEntity livingEntity;
	private GUIProgressBar healthBar;
	
	/*
	* Constructor
	* */
	public GUIHealthBar(GUIComponent parent, int x, int y, int width, int height, LivingEntity entity) {
		this(parent, x, y, width, height, entity, 3);
	}
	
	public GUIHealthBar(GUIComponent parent, int x, int y, int width, int height, LivingEntity entity, int padding) {
		super(parent, x, y, width, height);
		livingEntity = entity;
		
		healthBar = new GUIProgressBar(this, 0, 0, MATCH_PARENT, MATCH_PARENT);
		healthBar.setProgressColor(PROGRESS_COLOR);
		healthBar.setBackground(BACKGROUND_COLOR, padding);
		healthBar.setInfo("Health");
		healthBar.showNumbers(true);
	}
	
	@Override
	public void update() {
		super.update();
		healthBar.setProgress(livingEntity.getHealth());
		
		if (healthBar.getMaxProgress() != livingEntity.getAttribute(LivingEntity.ATTRIBUTE_MAX_HEALTH)) {
			healthBar.setMaxProgress(livingEntity.getAttribute(LivingEntity.ATTRIBUTE_MAX_HEALTH));
		}
	}
	
	@Override
	public void render(Graphics g) {
		super.render(g);
		healthBar.render(g);
	}
}
