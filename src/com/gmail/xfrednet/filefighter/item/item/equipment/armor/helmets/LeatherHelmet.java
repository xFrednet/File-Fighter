package com.gmail.xfrednet.filefighter.item.item.equipment.armor.helmets;

import com.gmail.xfrednet.filefighter.graphics.Sprite;
import com.gmail.xfrednet.filefighter.item.item.equipment.Armor;
import com.gmail.xfrednet.filefighter.item.item.equipment.armor.Helmet;

/**
 * Created by xFrednet on 01.03.2016.
 */
public class LeatherHelmet extends Helmet {
	
	public LeatherHelmet() {
		super(new ArmorStats(1.0, 0.2));
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
