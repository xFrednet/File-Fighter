package com.gmail.xfrednet.filefighter.graphics.gui.groups;

import com.gmail.xfrednet.filefighter.Main;

import com.gmail.xfrednet.filefighter.entity.LivingEntity;
import com.gmail.xfrednet.filefighter.graphics.Sprite;
import com.gmail.xfrednet.filefighter.graphics.gui.GUIComponent;
import com.gmail.xfrednet.filefighter.graphics.gui.GUIComponentGroup;
import com.gmail.xfrednet.filefighter.graphics.gui.components.*;
import com.gmail.xfrednet.filefighter.util.MouseInteraction;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static com.gmail.xfrednet.filefighter.entity.LivingEntity.*;

/**
 * Created by xFrednet on 20.02.2016.
 */
public class GUILivingEntityEquipment extends GUIComponentGroup {
	
	//colors
	public static final int BACKGROUND_COLOR = 0xffacacac;
	public static final Color TEXT_COLOR = new Color(0xff606060, true);		
	public static final Color SKILL_POINT_CATEGORY_SEPARATOR_COLOR = new Color(0xff757575, true);
	//sizes
	public static final int PADDING = 2 * Main.scale;
	public static final int ENTITY_SCALE = 2;
	public static final int GUI_WIDTH = 336;
	public static final int GUI_HEIGHT = 471;
	public static final int GUI_X = 552;
	public static final int GUI_Y = 171;
	//string
	public static final String FRAME_NAME = "EQUIPMENT";
	
	LivingEntity livingEntity;
	GUIBackground background;
	GUIComponent guiEntitySprite;                         
	GUIText headline;
	
	//armor
	ArmorBanner physicalArmor;
	ArmorBanner mentalArmor;
	
	public GUILivingEntityEquipment(GUIComponent parent, LivingEntity livingEntity) {
		this(parent, GUI_X, GUI_Y, livingEntity);
	}
	
	public GUILivingEntityEquipment(GUIComponent parent, int x, int y, LivingEntity livingEntity) {
		super(parent, x, y, GUI_WIDTH, GUI_HEIGHT);
		this.livingEntity = livingEntity;
		
		//background
		background = new GUIBackground(this, 0, 0, GUI_WIDTH, GUI_HEIGHT, BACKGROUND_COLOR);
		addComponent(background);
		
		//headline
		addHeadline();
		int contentY = HEADLINE_FONT.getSize();
		
		//armors
		physicalArmor = new ArmorBanner(this, 0, contentY, 1, ArmorBanner.PHYSICAL_ARMOR, PADDING);
		addComponent(physicalArmor);
		mentalArmor = new ArmorBanner(this, GUI_WIDTH - ArmorBanner.SCALE1_WIDTH, contentY, 1, ArmorBanner.MENTAL_ARMOR, PADDING);
		addComponent(mentalArmor);
		
		//Entity
		int entityY = ((ArmorBanner.SCALE1_HEIGHT) - (Sprite.ENTITY_SPRITE_SIZE * ENTITY_SCALE)) / 2 + contentY;
		guiEntitySprite = new GUIEntitySprite(this, ArmorBanner.SCALE1_WIDTH + PADDING, entityY / Main.scale, livingEntity, ENTITY_SCALE);
		components.add(guiEntitySprite);
		contentY += ArmorBanner.SCALE1_HEIGHT;
		
		SkillPointBanner skills = new SkillPointBanner(this, -PADDING, contentY, PADDING);
		addComponent(skills);
	}
	
	private void addHeadline() {
		headline = new GUIText(this, 0, 0, FRAME_NAME, false, HEADLINE_FONT);
		headline.setColor(TEXT_COLOR, Color.BLACK);
		addComponent(headline);
	}
	
	//gui components
	private class ArmorBanner extends GUIComponentGroup {
		
		public static final int SCALE1_WIDTH = PADDING * 2 + GUIItemFrame.SIZE;
		public static final int SCALE1_HEIGHT = PADDING * 2 + GUIItemFrame.SIZE * 4;
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
			
			itemFrames[HELMET_SLOT] = new GUIItemFrame(this, x, y, livingEntity.getEquipment(armorSlots[HELMET_SLOT]), GUIItemFrame.TYPE_HELMET);
			y += GUIItemFrame.SIZE * scale;
			//chestplate
			itemFrames[CHESTPLATE_SLOT] = new GUIItemFrame(this, x, y, livingEntity.getEquipment(armorSlots[CHESTPLATE_SLOT]), GUIItemFrame.TYPE_CHESTPLATE);
			y += GUIItemFrame.SIZE * scale;
			//pents
			itemFrames[PENTS_SLOT] = new GUIItemFrame(this, x, y, livingEntity.getEquipment(armorSlots[PENTS_SLOT]), GUIItemFrame.TYPE_PENTS);
			y += GUIItemFrame.SIZE * scale;
			//shoes
			itemFrames[SHOES_SLOT] = new GUIItemFrame(this, x, y, livingEntity.getEquipment(armorSlots[SHOES_SLOT]), GUIItemFrame.TYPE_SHOES);
			
			for (int i = 0; i < itemFrames.length; i++) {
				addComponent(itemFrames[i]);
			}
			
		}
		
	}
	
	public static final int SKILL_POINT_ROW_HEIGHT = FONT.getSize() + PADDING * 2;
	public static final int SKILL_POINT_ROW_WIDTH = GUI_WIDTH - PADDING * 4;
	public static final int SKILL_BANNER_HEIGHT = SKILL_POINT_ROW_HEIGHT * SKILL_POINT_CATEGORY_COUNT;
	private class SkillPointBanner extends GUIComponentGroup {
		
		SkillPointRow[] rows = new SkillPointRow[SKILL_POINT_CATEGORY_COUNT];
		String[] displayedInfo = new String[0];
		BufferedImage skillButtons;
		int skillInfoY;
		
		int buttonAnimation = 0;
		int currentAnimation = 0;
		final int ANIMATION_SPEED = 15;
		
		public SkillPointBanner(GUIComponent parent, int x, int y, int padding) {
			super(parent, x, y, GUI_WIDTH, SKILL_BANNER_HEIGHT, padding);
			
			int rowY = 0;
			for (int i = 0; i < SKILL_POINT_CATEGORY_COUNT; i++) {
				rows[i] = new SkillPointRow(this, 0, rowY, i);
				rowY += SKILL_POINT_ROW_HEIGHT;
				addComponent(rows[i]);
			}
			
			skillInfoY = rowY;
			
			try {
				skillButtons = ImageIO.read(GUILivingEntityEquipment.class.getResource("/textures/gui/SkillButtons.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		private void setSkillInfo(String skillName, String[] skillInfo) {
			displayedInfo = new String[skillInfo.length + 1];
			displayedInfo[0] = skillName;
			
			System.arraycopy(skillInfo, 0, displayedInfo, 1, skillInfo.length);
		}
		
		@Override
		public void render(Graphics g) {
			super.render(g);
			
			g.setColor(TEXT_COLOR);
			
			for (int i = 0; i < displayedInfo.length; i++) {
				g.setFont(INFO_FONT);
				g.drawString(displayedInfo[i], screenX + padding, screenY + skillInfoY + PADDING + INFO_FONT.getSize() * (i + 1));
			}
			
			g.setFont(FONT);
			g.drawString(livingEntity.getUnspentSkillPoints() + " Skill Points" , screenX + 75, screenY - 28 + FONT.getSize());
			
		}
		
		@Override
		public void update() {
			super.update();
			
			//animation
			if (livingEntity.hasUnspentSkillPoints()) {
				if (buttonAnimation++ > 100000) {
					buttonAnimation = 0;
				}
				
				if ((buttonAnimation / ANIMATION_SPEED) % 2 == 0) {
					currentAnimation = 0;
				} else {
					currentAnimation = 1;
				}
			} else {
				if (currentAnimation != 0) {
					currentAnimation = 0;
				}
			}
			
			
		}
		
		private class SkillPointRow extends GUIComponent implements MouseInteraction {
			
			final int BUTTON_SIZE = FONT.getSize();
			final int buttonX = SKILL_POINT_ROW_WIDTH - BUTTON_SIZE;
			final int SPENT_SKILL_POINT_COUNT_X = SKILL_POINT_ROW_WIDTH - 50;

			
			int skillPointCategory;
			String skillName;
			int buttonY;
			
			public SkillPointRow(GUIComponent parent, int x, int y, int skillPoint) {
				super(parent, x, y, SKILL_POINT_ROW_WIDTH, SKILL_POINT_ROW_HEIGHT, PADDING);
				this.skillPointCategory = skillPoint;
				skillName = livingEntity.getSkillPointName(skillPoint);
				
				buttonY = screenY + padding;
				
				Main.input.addMouseInteraction(this, screenX, screenY, width, height);
			}
			
			@Override
			public void render(Graphics g) {
				super.render(g);
				g.setFont(FONT);
				g.setColor(SEPARATOR_COLOR);
				g.drawRect(screenX, screenY, getWidthWithPadding(), height);
				
				//button rect
				g.setColor(Color.PINK);
				
				if (currentAnimation == 0) {
					g.drawImage(skillButtons, screenX + buttonX, buttonY, screenX + buttonX + BUTTON_SIZE, buttonY + BUTTON_SIZE, 0, 0, BUTTON_SIZE, BUTTON_SIZE, null);
				} else if (currentAnimation == 1) {
					g.drawImage(skillButtons, screenX + buttonX, buttonY, screenX + buttonX + BUTTON_SIZE, buttonY + BUTTON_SIZE, 0, BUTTON_SIZE, BUTTON_SIZE, BUTTON_SIZE * 2, null);
				}
				
				g.setColor(TEXT_COLOR);
				g.drawString(skillName, screenX + padding, screenY + FONT.getSize() + padding);
				g.drawString(livingEntity.getSpentSkillPoint(skillPointCategory) + " ", screenX + SPENT_SKILL_POINT_COUNT_X, screenY + FONT.getSize() + padding);
				
			}
			
			private void buttonPressed() {
				if (livingEntity.hasUnspentSkillPoints()) {
					livingEntity.applySkillPoints(skillPointCategory);
				}
			}
			
			@Override
			public void mouseEntered(int x, int y) {
				setSkillInfo(skillName, livingEntity.getSkillPointInfo(skillPointCategory));
			}
			
			@Override
			public void mouseExited(int x, int y) {}
			
			@Override
			public void mousePressed(int x, int y, int button) {
				if (x >= (this.x + buttonX) && x < (this.x + buttonX + BUTTON_SIZE) && y < (BUTTON_SIZE)) buttonPressed();
			}
			
			@Override
			public void mouseReleased(int x, int y, int button) {}
			
			@Override
			public void mouseWaits(int x, int y, int time) {}
			
			@Override
			public void mouseMoved(int x, int y) {}
		}
		
	}
	
}
