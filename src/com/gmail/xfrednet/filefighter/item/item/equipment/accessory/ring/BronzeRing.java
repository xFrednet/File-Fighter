package com.gmail.xfrednet.filefighter.item.item.equipment.accessory.ring;

import com.gmail.xfrednet.filefighter.graphics.Sprite;
import com.gmail.xfrednet.filefighter.item.Item;
import com.gmail.xfrednet.filefighter.item.item.EquipmentAttributeModifiers;
import com.gmail.xfrednet.filefighter.item.item.equipment.accessory.Ring;

/**
 * Created by xFrednet on 13.03.2016.
 */
public class BronzeRing extends Ring {
	public BronzeRing() {
		super(null);
	}
	
	@Override
	public Sprite getItemSprite() {
		return Sprite.Item.bronze_ring;
	}
	
	@Override
	public String getName() {
		return "Bronze Ring";
	}
	
	@Override
	public Item clone() {
		return new BronzeRing().setCount(count);
	}
}
