package com.gmail.xfrednet.filefighter.item.item.equipment.armor;

import com.gmail.xfrednet.filefighter.graphics.Sprite;
import com.gmail.xfrednet.filefighter.item.item.equipment.Armor;

/**
 * Created by xFrednet on 03.03.2016.
 */
public class LeatherPents extends Armor {
	@Override
	public Sprite getAnimatedSprite(int animation) {
		return Sprite.Equipment.leather_pents_sprite[animation];
	}
	
	@Override
	public String getName() {
		return "Leather Pents";
	}
	
	@Override
	public Sprite getItemSprite() {
		return Sprite.Item.leather_pents;
	}
}
