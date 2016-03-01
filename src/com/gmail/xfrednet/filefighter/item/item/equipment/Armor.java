package com.gmail.xfrednet.filefighter.item.item.equipment;

import com.gmail.xfrednet.filefighter.item.Item;
import com.gmail.xfrednet.filefighter.item.item.Equipment;

/**
 * Created by xFrednet on 20.02.2016.
 */
public abstract class Armor extends Equipment {
	
	protected Armor(String name) {
		this(name, 1);
	}
	protected Armor(String name, int count) {
		super(name, count);
	}
	
}
