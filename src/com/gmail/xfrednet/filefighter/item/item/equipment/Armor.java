package com.gmail.xfrednet.filefighter.item.item.equipment;

import com.gmail.xfrednet.filefighter.graphics.Sprite;
import com.gmail.xfrednet.filefighter.graphics.gui.GUIComponentGroup;
import com.gmail.xfrednet.filefighter.graphics.gui.groups.GUIItemInfoFrame;
import com.gmail.xfrednet.filefighter.item.item.Equipment;
import static com.gmail.xfrednet.filefighter.entity.LivingEntity.*;

/**
 * Created by xFrednet on 20.02.2016.
 */
public abstract class Armor extends Equipment {
	
	protected ArmorStats stats;
	
	protected Armor(ArmorStats armorStats) {
		super();
		stats = armorStats;
	}
	
	abstract public Sprite getAnimatedSprite(int animation);
	
	public double getAttributeModifier(int attribute) {
		switch (attribute) {
			case ATTRIBUTE_PHYSICAL_DEFENCE: return stats.getPhysicalDefence() + super.getAttributeModifier(attribute);
			case ATTRIBUTE_MENTAL_DEFENCE: return stats.getMentalDefence() + super.getAttributeModifier(attribute);
			default: return super.getAttributeModifier(attribute);
		}
	}
	
	//gui
	@Override
	public GUIItemInfoFrame getGUIItemInfoFrame(GUIComponentGroup parent, int x, int y) {
		GUIItemInfoFrame info = super.getGUIItemInfoFrame(parent, x, y);
		
		info.addItemInfo(new GUIItemInfoFrame.GUItemNumberInfo(info, "Physical Defence", stats.getPhysicalDefence()));
		info.addItemInfo(new GUIItemInfoFrame.GUItemNumberInfo(info, "Mental Defence", stats.getMentalDefence()));
		
		return info;
	}
	
	/*
	* armor stats
	* */
	public static class ArmorStats {
		
		final double PHYSICAL_DEFENCE;
		final double MENTAL_DEFENCE;
		
		public ArmorStats(double physical_defence, double mental_defence) {
			PHYSICAL_DEFENCE = physical_defence;
			MENTAL_DEFENCE = mental_defence;
		}
		
		public double getPhysicalDefence() {
			return PHYSICAL_DEFENCE;
		}
		
		public double getMentalDefence() {
			return MENTAL_DEFENCE;
		}
	}
	
}
