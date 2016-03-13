package com.gmail.xfrednet.filefighter.item.itemstorage;

import com.gmail.xfrednet.filefighter.item.ItemContainer;
import com.gmail.xfrednet.filefighter.item.ItemStorage;
import com.gmail.xfrednet.filefighter.item.item.Equipment;

import static com.gmail.xfrednet.filefighter.entity.LivingEntity.*;

/**
 * Created by xFrednet on 11.03.2016.
 */
public class StorageAccessories extends ItemStorage {
	
	public StorageAccessories() {
		super(1, 4, NO_NAME, false);
		
		items[0] = new ItemContainer(ItemContainer.ACCESSORY_NECKLACE);
		items[1] = new ItemContainer(ItemContainer.ACCESSORY_RING);
		items[2] = new ItemContainer(ItemContainer.ACCESSORY_RING);
		items[3] = new ItemContainer(ItemContainer.ACCESSORY_BRACELET);
	}
	
	public Equipment get(int item) {
		switch (item) {
			case EQUIPMENT_NECKLACE:
				return (Equipment) getItem(0);
			case EQUIPMENT_RING_1:
				return (Equipment) getItem(1);
			case EQUIPMENT_RING_2:
				return (Equipment) getItem(2);
			case EQUIPMENT_BRACELET:
				return (Equipment) getItem(3);
			default:
				return null;
		}
	}
}
