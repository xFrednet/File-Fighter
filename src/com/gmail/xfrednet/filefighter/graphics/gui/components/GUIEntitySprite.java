package com.gmail.xfrednet.filefighter.graphics.gui.components;

import com.gmail.xfrednet.filefighter.Main;
import com.gmail.xfrednet.filefighter.entity.Entity;
import com.gmail.xfrednet.filefighter.graphics.Screen;
import com.gmail.xfrednet.filefighter.graphics.gui.GUIComponent;

/**
 * Created by xFrednet on 20.02.2016.
 */
public class GUIEntitySprite extends GUIComponent {
	
	int scale;
	Entity entity;
	
	public GUIEntitySprite(GUIComponent parent, int x, int y, Entity entity) {
		this(parent, x, y, entity, 1);
	}
	public GUIEntitySprite(GUIComponent parent, int x, int y, Entity entity, int scale) {
		super(parent, x, y, entity.getSprite().WIDTH * scale, entity.getSprite().HEIGHT * scale);
		this.scale = scale;
		this.entity = entity;
	}
	
	@Override
	public void render(Screen screen) {
		entity.render(screen, screenX / Main.scale, screenY / Main.scale, true, scale);
	}
}
