package com.gmail.xfrednet.filefighter.item.item;

import com.gmail.xfrednet.filefighter.Main;
import com.gmail.xfrednet.filefighter.item.Item;

/**
 * Created by xFrednet on 20.02.2016.
 */
public abstract class Equipment extends Item {
	
	protected EquipmentAttributeModifiers modifiers;
	
	/*
	* Constructor
	* */
	protected Equipment() {
		this(null);
	}
	protected Equipment(EquipmentAttributeModifiers modifiers) {
		super();
		this.modifiers = modifiers;
	}
	
	/*
	* setters
	* */
	protected void setModifiers(EquipmentAttributeModifiers modifiers) {
		this.modifiers = modifiers;
	}
	
	/*
	* getters
	* */
	protected int getMaxStackSize() {
		return 1;
	}
	public double getAttributeModifier(int attribute) {
		if (modifiers == null) return 0;
		return modifiers.getAttributeModifier(attribute);
	}
	
	@Override
	public int getUseDelay() {
		return 0;
	}
}
