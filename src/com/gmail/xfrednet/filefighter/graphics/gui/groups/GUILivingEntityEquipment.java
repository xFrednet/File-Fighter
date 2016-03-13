package com.gmail.xfrednet.filefighter.graphics.gui.groups;

import com.gmail.xfrednet.filefighter.Main;

import com.gmail.xfrednet.filefighter.entity.LivingEntity;
import com.gmail.xfrednet.filefighter.entity.Player;
import com.gmail.xfrednet.filefighter.graphics.Sprite;
import com.gmail.xfrednet.filefighter.graphics.gui.GUIComponent;
import com.gmail.xfrednet.filefighter.graphics.gui.GUIComponentGroup;
import com.gmail.xfrednet.filefighter.graphics.gui.components.*;
import com.gmail.xfrednet.filefighter.item.ItemStorage;
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
	ItemStorage.GUIItemStorage armor;
	ItemStorage.GUIItemStorage accessories;
	SkillPointBanner skills;
	
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
		armor = livingEntity.getArmor().getGUI(this, 0, contentY, (Player) livingEntity);
		addComponent(armor);
		accessories = livingEntity.getAccessories().getGUI(this, width - armor.getWidth() - PADDING * 2, contentY, (Player) livingEntity);
		addComponent(accessories);
		
		//Entity
		guiEntitySprite = new GUIEntitySprite(this, armor.getWidth() + PADDING, contentY + PADDING * 3, livingEntity, ENTITY_SCALE);
		components.add(guiEntitySprite);
		contentY += armor.getHeight();
		
		skills = new SkillPointBanner(this, -PADDING, contentY, PADDING);
		addComponent(skills);
	}
	
	/*
	* util
	* */
	private void addHeadline() {
		headline = new GUIText(this, 0, 0, FRAME_NAME, false, HEADLINE_FONT);
		headline.setColor(TEXT_COLOR, Color.BLACK);
		addComponent(headline);
	}
	public void setVisible(boolean show) {
		super.setVisible(show);
		if (armor != null) {
			armor.setVisible(show);
		}
	}
	
	//gui components
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
