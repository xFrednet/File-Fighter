package com.gmail.xfrednet.filefighter.item.item.equipment.accessory.ring;

import com.gmail.xfrednet.filefighter.graphics.Sprite;
import com.gmail.xfrednet.filefighter.item.Item;
import com.gmail.xfrednet.filefighter.item.item.EquipmentAttributeModifiers;
import com.gmail.xfrednet.filefighter.item.item.equipment.accessory.Ring;

/**
 * Created by xFrednet on 13.03.2016.
 */
public class SilverDiamondRing extends Ring {
	
	/*
	* Constructors
	* */
	public SilverDiamondRing() {
		super(null);
	}
	private SilverDiamondRing(EquipmentAttributeModifiers modifiers) {
		super(modifiers);
	}
	
	public static SilverDiamondRing newSpeedRing() {
		return new SilverDiamondRing(new EquipmentAttributeModifiers().setSpeed(10));
	}
	
	@Override
	public Item clone() {
		return new SilverDiamondRing().setCount(count);
	}
	
	/*
	* getters
	* */
	@Override
	public Sprite getItemSprite() {
		return Sprite.Item.silver_diamond_ring;
	}
	
	@Override
	public String getName() {
		return "Silver Diamond Ring";
	}
}
