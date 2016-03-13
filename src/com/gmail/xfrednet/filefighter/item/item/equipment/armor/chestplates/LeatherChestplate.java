package com.gmail.xfrednet.filefighter.item.item.equipment.armor.chestplates;

import com.gmail.xfrednet.filefighter.graphics.Sprite;
import com.gmail.xfrednet.filefighter.item.item.equipment.Armor;
import com.gmail.xfrednet.filefighter.item.item.equipment.armor.Chestplate;

/**
 * Created by xFrednet on 03.03.2016.
 */
public class LeatherChestplate extends Chestplate {
	public LeatherChestplate() {
		super(new ArmorStats(1.0, 0.2));
	}
	
	@Override
	public Sprite getAnimatedSprite(int animation) {
		return Sprite.Equipment.leather_chestplate_sprite[animation];
	}
	
	@Override
	public String getName() {
		return "Leather Chestplate";
	}
	
	@Override
	public Sprite getItemSprite() {
		return Sprite.Item.leather_chestplate;
	}
}
