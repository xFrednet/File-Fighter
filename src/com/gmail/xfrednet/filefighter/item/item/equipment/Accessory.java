package com.gmail.xfrednet.filefighter.item.item.equipment;

import com.gmail.xfrednet.filefighter.item.item.Equipment;
import com.gmail.xfrednet.filefighter.item.item.EquipmentAttributeModifiers;

/**
 * Created by xFrednet on 13.03.2016.
 */
public abstract class Accessory extends Equipment {
	
	EquipmentAttributeModifiers modifiers;
	
	protected Accessory(EquipmentAttributeModifiers modifiers) {
		this.modifiers = modifiers;
	}
	
}
