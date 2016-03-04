package com.gmail.xfrednet.filefighter.item.item.equipment;

import com.gmail.xfrednet.filefighter.graphics.Sprite;
import com.gmail.xfrednet.filefighter.item.Item;
import com.gmail.xfrednet.filefighter.item.item.Equipment;

/**
 * Created by xFrednet on 20.02.2016.
 */
public abstract class Armor extends Equipment {
	
	protected Armor() {
		super();
	}
	
	abstract public Sprite getAnimatedSprite(int animation);
	
}
