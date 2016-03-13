package com.gmail.xfrednet.filefighter.item.item.equipment.accessory.necklace;

import com.gmail.xfrednet.filefighter.graphics.Sprite;
import com.gmail.xfrednet.filefighter.item.item.equipment.accessory.Necklace;

/**
 * Created by xFrednet on 13.03.2016.
 */
public class GoldDiamondNecklace extends Necklace {
	public GoldDiamondNecklace() {
		super(null);
	}
	
	@Override
	public Sprite getItemSprite() {
		return Sprite.Item.gold_diamond_necklace;
	}
	
	@Override
	public String getName() {
		return "Gold Diamond Necklace";
	}
}
