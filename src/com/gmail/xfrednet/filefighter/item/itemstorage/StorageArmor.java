package com.gmail.xfrednet.filefighter.item.itemstorage;

import com.gmail.xfrednet.filefighter.entity.Entity;
import com.gmail.xfrednet.filefighter.entity.LivingEntity;
import com.gmail.xfrednet.filefighter.item.Item;
import com.gmail.xfrednet.filefighter.item.ItemContainer;
import com.gmail.xfrednet.filefighter.item.ItemStorage;
import com.gmail.xfrednet.filefighter.item.item.Equipment;
import static com.gmail.xfrednet.filefighter.entity.LivingEntity.*;

/**
 * Created by xFrednet on 11.03.2016.
 */
public class StorageArmor extends ItemStorage {
	public StorageArmor() {
		super(1, 4, NO_NAME, false);
		
		items[0] = new ItemContainer(ItemContainer.ARMOR_HELMET);
		items[1] = new ItemContainer(ItemContainer.ARMOR_CHESTPLATE);
		items[2] = new ItemContainer(ItemContainer.ARMOR_PENTS);
		items[3] = new ItemContainer(ItemContainer.ARMOR_SHOES);
	}
	
	public Equipment get(int item) {
		switch (item) {
			case EQUIPMENT_HELMET:
				return (Equipment) getItem(0);
			case EQUIPMENT_CHESTPLATE:
				return (Equipment) getItem(1);
			case EQUIPMENT_PENTS:
				return (Equipment) getItem(2);
			case EQUIPMENT_SHOES:
				return (Equipment) getItem(3);
			default:
				return null;
		}
	}
	
	@Override
	public Item switchItem(Entity entity, Item item, int slot) {
		 Item rItem = super.switchItem(entity, item, slot);
		
		if (entity instanceof LivingEntity) {
			((LivingEntity) entity).updateAttributes();
		}
		
		return rItem;
	}
}
