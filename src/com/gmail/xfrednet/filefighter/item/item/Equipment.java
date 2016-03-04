package com.gmail.xfrednet.filefighter.item.item;

import com.gmail.xfrednet.filefighter.item.Item;

/**
 * Created by xFrednet on 20.02.2016.
 */
public abstract class Equipment extends Item {
	protected Equipment() {
		super();
	}
	
	protected int getMaxStackSize() {
		return 1;
	}
	
}
