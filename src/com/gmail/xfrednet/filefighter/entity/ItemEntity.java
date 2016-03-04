package com.gmail.xfrednet.filefighter.entity;

import com.gmail.xfrednet.filefighter.graphics.Screen;
import com.gmail.xfrednet.filefighter.graphics.Sprite;
import com.gmail.xfrednet.filefighter.item.Item;
import com.gmail.xfrednet.filefighter.level.Level;

/**
 * Created by xFrednet on 14.02.2016.
 */
public class ItemEntity extends Entity {
	
	Item item;
	
	public ItemEntity(double x, double y, Level level, Item item) {
		super(level, item.getName());
		this.item = item;
		currentSprite = item.getItemSprite();
		setInfo(x, y, Sprite.ITEM_ENTITY_SPRITE_SIZE, Sprite.ITEM_ENTITY_SPRITE_SIZE, 0, 0);
	}
	
	@Override
	public void update(Level level) {
		item.updateFormEntity(level);
	}
	
	@Override
	public void render(Screen screen) {
		screen.drawItemEntity((int)info.x, (int)info.y, currentSprite);
	}
}
