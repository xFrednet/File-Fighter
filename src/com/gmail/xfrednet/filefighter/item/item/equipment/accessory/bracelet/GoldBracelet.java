package com.gmail.xfrednet.filefighter.item.item.equipment.accessory.bracelet;

import com.gmail.xfrednet.filefighter.graphics.Sprite;
import com.gmail.xfrednet.filefighter.item.Item;
import com.gmail.xfrednet.filefighter.item.item.EquipmentAttributeModifiers;
import com.gmail.xfrednet.filefighter.item.item.equipment.accessory.Bracelet;

/**
 * Created by xFrednet on 13.03.2016.
 */
public class GoldBracelet extends Bracelet {
	public GoldBracelet() {
		super(null);
	}
	
	@Override
	public Sprite getItemSprite() {
		return Sprite.Item.gold_bracelet;
	}
	
	@Override
	public Item clone() {
		return new GoldBracelet().setCount(count);
	}
	
	@Override
	public String getName() {
		return "Gold Bracelet";
	}
	
	
}
