package com.gmail.xfrednet.filefighter.graphics.gui.groups;

import com.gmail.xfrednet.filefighter.Main;

import com.gmail.xfrednet.filefighter.entity.LivingEntity;
import com.gmail.xfrednet.filefighter.graphics.Sprite;
import com.gmail.xfrednet.filefighter.graphics.gui.GUIComponent;
import com.gmail.xfrednet.filefighter.graphics.gui.GUIComponentGroup;
import com.gmail.xfrednet.filefighter.graphics.gui.components.GUIBackground;
import com.gmail.xfrednet.filefighter.graphics.gui.components.GUIEntitySprite;
import com.gmail.xfrednet.filefighter.graphics.gui.components.GUIItemFrame;
import com.gmail.xfrednet.filefighter.graphics.gui.components.GUIText;

import java.awt.*;

import static com.gmail.xfrednet.filefighter.entity.LivingEntity.*;

/**
 * Created by xFrednet on 20.02.2016.
 */
public class GUILivingEntityEquipment extends GUIComponentGroup {
	
	public static final int BACKGROUND_COLOR = 0xffacacac;
	public static final int PADDING = 2 * Main.scale;
	public static final int ENTITY_SCALE = 2;
	public static final int GUI_WIDTH = (Sprite.ENTITY_SPRITE_SIZE * ENTITY_SCALE * Main.scale + PADDING * 2) + (ArmorBanner.SCALE1_WIDTH * 2);
	public static final int GUI_HEIGHT = (Sprite.ENTITY_SPRITE_SIZE * ENTITY_SCALE) * Main.scale + (PADDING * 2);
	public static final Font HEADLINE_FONT = new Font(Main.gameFont.getName(), Font.BOLD, 32);
	public static final String FRAME_NAME = "EQUIPMENT";
	
	LivingEntity livingEntity;
	GUIBackground background;
	GUIComponent guiEntitySprite;                         
	GUIText headline;
	
	//armor
	ArmorBanner physicalArmor;
	ArmorBanner mentalArmor;
	
	public GUILivingEntityEquipment(GUIComponent parent, LivingEntity livingEntity) {
		this(parent, GRAVITY_CENTER, GRAVITY_CENTER, livingEntity);
	}
	
	public GUILivingEntityEquipment(GUIComponent parent, int x, int y, LivingEntity livingEntity) {
		super(parent, x, y, GUI_WIDTH, GUI_HEIGHT);
		this.livingEntity = livingEntity;
		
		//background
		background = new GUIBackground(this, 0, 0, GUI_WIDTH, GUI_HEIGHT, BACKGROUND_COLOR);
		addComponent(background);
		
		//headline
		addHeadline();
		
		//armors
		physicalArmor = new ArmorBanner(this, 0, 0, 1, ArmorBanner.PHYSICAL_ARMOR, PADDING);
		addComponent(physicalArmor);
		mentalArmor = new ArmorBanner(this, GUI_WIDTH - ArmorBanner.SCALE1_WIDTH, 0, 1, ArmorBanner.MENTAL_ARMOR, PADDING);
		addComponent(mentalArmor);
		
		//Entity
		int entityY = ((ArmorBanner.SCALE1_HEIGHT) - (Sprite.ENTITY_SPRITE_SIZE * ENTITY_SCALE)) / 2;
		guiEntitySprite = new GUIEntitySprite(this, ArmorBanner.SCALE1_WIDTH + PADDING, entityY / Main.scale, livingEntity, ENTITY_SCALE);
		components.add(guiEntitySprite);
		
	}
	
	private void addHeadline() {
		headline = new GUIText(this, 0, 0, FRAME_NAME, false, HEADLINE_FONT);
		addComponent(headline);
	}
	
	private class ArmorBanner extends GUIComponentGroup {
		
		public static final int SCALE1_WIDTH = PADDING * 2 + GUIItemFrame.SCALE1_SIZE;
		public static final int SCALE1_HEIGHT = PADDING * 2 + GUIItemFrame.SCALE1_SIZE * 4;
		public static final int PHYSICAL_ARMOR = 0;
		public static final int MENTAL_ARMOR = 1;
		
		private final static int HELMET_SLOT = 0;
		private final static int CHESTPLATE_SLOT = 1;
		private final static int PENTS_SLOT = 2;
		private final static int SHOES_SLOT = 3;
		
		int scale;
		int armorType;
		int[] armorSlots;
		GUIItemFrame[] itemFrames;
		
		public ArmorBanner(GUIComponent parent, int x, int y, int scale, int armorType, int padding) {
			super(parent, x, y, SCALE1_WIDTH * scale, SCALE1_HEIGHT * scale, padding);
			this.scale = scale;
			this.armorType = armorType;
			armorSlots = getArmorSlots(armorType);
			init();
		}
		
		private int[] getArmorSlots(int armorType) {
			if (armorType == PHYSICAL_ARMOR) {
				return new int[] {EQUIPMENT_PHYSICAL_ARMOR_HELMET, EQUIPMENT_PHYSICAL_ARMOR_CHESTPLATE, EQUIPMENT_PHYSICAL_ARMOR_PENTS, EQUIPMENT_PHYSICAL_ARMOR_SHOES};
			} else {
				return new int[] {EQUIPMENT_MENTAL_ARMOR_HELMET, EQUIPMENT_MENTAL_ARMOR_CHESTPLATE, EQUIPMENT_MENTAL_ARMOR_PENTS, EQUIPMENT_MENTAL_ARMOR_SHOES};
			}
		}
		
		private void init() {
			itemFrames = new GUIItemFrame[4];
			int y = 0;
			int x = 0;
			
			itemFrames[HELMET_SLOT] = (GUIItemFrame) new GUIItemFrame(this, x, y, livingEntity.getEquipment(armorSlots[HELMET_SLOT]));
			y += GUIItemFrame.SCALE1_SIZE * scale;
			//chestplate
			itemFrames[CHESTPLATE_SLOT] = (GUIItemFrame) new GUIItemFrame(this, x, y, livingEntity.getEquipment(armorSlots[CHESTPLATE_SLOT]));
			y += GUIItemFrame.SCALE1_SIZE * scale;
			//pents
			itemFrames[PENTS_SLOT] = (GUIItemFrame) new GUIItemFrame(this, x, y, livingEntity.getEquipment(armorSlots[PENTS_SLOT]));
			y += GUIItemFrame.SCALE1_SIZE * scale;
			//shoes
			itemFrames[SHOES_SLOT] = (GUIItemFrame) new GUIItemFrame(this, x, y, livingEntity.getEquipment(armorSlots[SHOES_SLOT]));
			
			for (int i = 0; i < itemFrames.length; i++) {
				addComponent(itemFrames[i]);
			}
			
		}
		
	}
	
}
