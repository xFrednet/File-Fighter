package com.gmail.xfrednet.filefighter.item.item.equipment.armor;

import com.gmail.xfrednet.filefighter.graphics.Sprite;
import com.gmail.xfrednet.filefighter.item.item.equipment.Armor;

/**
 * Created by xFrednet on 01.03.2016.
 */
public class LeatherHelmet extends Armor {
	public LeatherHelmet() {
		super();
	}
	
	@Override
	public String getName() {
		return "Leather Helmet";
	}
	
	@Override
	public Sprite getAnimatedSprite(int animation) {
		return Sprite.Equipment.leather_helmet_sprite[animation];
	}
	
	@Override
	public Sprite getItemSprite() {
		return Sprite.Item.leather_helmet;
	}
}
