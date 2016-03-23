package com.gmail.xfrednet.filefighter.item.item.equipment.armor.boots;

import com.gmail.xfrednet.filefighter.graphics.Sprite;
import com.gmail.xfrednet.filefighter.item.item.EquipmentAttributeModifiers;
import com.gmail.xfrednet.filefighter.item.item.equipment.armor.Shoes;

/**
 * Created by xFrednet on 03.03.2016.
 */
public class LeatherBoots extends Shoes {
	public LeatherBoots() {
		super(new ArmorStats(1.0, 0.2));
	}
	
	@Override
	public Sprite getAnimatedSprite(int animation) {
		return Sprite.Equipment.leather_boots_sprite[animation];
	}
	
	@Override
	public String getName() {
		return "Leather Shoes";
	}
	
	@Override
	public Sprite getItemSprite() {
		return Sprite.Item.leather_boots;
	}
}
