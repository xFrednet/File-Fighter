package com.gmail.xfrednet.filefighter.graphics.gui.groups;

import com.gmail.xfrednet.filefighter.Main;
import com.gmail.xfrednet.filefighter.entity.Player;
import com.gmail.xfrednet.filefighter.graphics.Sprite;
import com.gmail.xfrednet.filefighter.graphics.gui.GUIComponent;
import com.gmail.xfrednet.filefighter.graphics.gui.components.GUIBackground;
import com.gmail.xfrednet.filefighter.item.item.potion.HealthPotion;
import com.gmail.xfrednet.filefighter.item.item.potion.StaminaPotion;

import java.awt.*;

/**
 * Created by xFrednet on 29.03.2016 at 13:59.
 */
public class GUIPotionInfo extends GUIComponent {
	
	private static final Color FONT_COLOR = new Color(0xffffffff, true);
	private static final Font COUNT_FONT = new Font(Main.gameFont.getName(), Font.PLAIN, 12);
	
	private static final int PADDING = 5;
	private static final int SPRITE_SIZE = 32;
	
	public static final int GUI_WIDTH = SPRITE_SIZE * 2 - 4;
	public static final int GUI_HEIGHT = SPRITE_SIZE + PADDING + COUNT_FONT.getSize();
	
	private static final int HEALTH_POTION_OFFSET_X = 0;
	private static final int HEALTH_POTION_OFFSET_Y = 2;
	
	private static final int STAMINA_POTION_OFFSET_X = SPRITE_SIZE;
	private static final int STAMINA_POTION_OFFSET_Y = 2;
	
	private static final Color BACKGROUND_COLOR = new Color(0xcc757575, true);
	private GUIBackground background;
	
	private Sprite healthPotionSprite = Sprite.Item.health_potion;
	private Sprite spritePotionSprite = Sprite.Item.stamina_potion;
	
	private Player player;
	
	private String healthPotionCount = " " + 10;
	private String staminaPotionCount = " " + 1;
	
	private int healthPotionCountOffset = 0;
	private int staminaPotionCountOffset = 3;
	
	private Sprite healthPotionDelaySprite = null;
	private Sprite staminaPotionDelaySprite = null;
	
	/*
	* constructor
	* */
	public GUIPotionInfo(GUIComponent parent, int x, int y, Player player) {
		super(parent, x, y, GUI_WIDTH, GUI_HEIGHT);
		
		this.player = player;
		
		background = new GUIBackground(this, 0, 0, width, height, BACKGROUND_COLOR);
	}
	
	@Override
	public void render(Graphics g) {
		background.render(g);
		
		g.drawImage(healthPotionSprite.getImage(), getScreenX() + HEALTH_POTION_OFFSET_X, getScreenY() + HEALTH_POTION_OFFSET_Y,
				getScreenX() + HEALTH_POTION_OFFSET_X + SPRITE_SIZE, getScreenY() + HEALTH_POTION_OFFSET_Y + SPRITE_SIZE,
				healthPotionSprite.getImageX(), healthPotionSprite.getImageY(), healthPotionSprite.getImageMaxX(), healthPotionSprite.getImageMaxY(), null);
		g.drawImage(spritePotionSprite.getImage(), getScreenX() + STAMINA_POTION_OFFSET_X, getScreenY() + STAMINA_POTION_OFFSET_Y,
				getScreenX() + STAMINA_POTION_OFFSET_X + SPRITE_SIZE, getScreenY() + STAMINA_POTION_OFFSET_Y + SPRITE_SIZE,
				spritePotionSprite.getImageX(), spritePotionSprite.getImageY(), spritePotionSprite.getImageMaxX(), spritePotionSprite.getImageMaxY(), null);
		
		//delay
		if (healthPotionDelaySprite != null) {
			g.drawImage(healthPotionDelaySprite.getImage(), getScreenX() + HEALTH_POTION_OFFSET_X, getScreenY() + HEALTH_POTION_OFFSET_Y,
					getScreenX() + HEALTH_POTION_OFFSET_X + SPRITE_SIZE, getScreenY() + HEALTH_POTION_OFFSET_Y + SPRITE_SIZE,
					healthPotionDelaySprite.getImageX(), healthPotionDelaySprite.getImageY(), healthPotionDelaySprite.getImageMaxX(), healthPotionDelaySprite.getImageMaxY(), null);
		}
		if (staminaPotionDelaySprite != null) {
			g.drawImage(staminaPotionDelaySprite.getImage(), getScreenX() + STAMINA_POTION_OFFSET_X, getScreenY() + STAMINA_POTION_OFFSET_Y,
					getScreenX() + STAMINA_POTION_OFFSET_X + SPRITE_SIZE, getScreenY() + STAMINA_POTION_OFFSET_Y + SPRITE_SIZE,
					staminaPotionDelaySprite.getImageX(), staminaPotionDelaySprite.getImageY(), staminaPotionDelaySprite.getImageMaxX(), staminaPotionDelaySprite.getImageMaxY(), null);
		}
		
		g.setColor(FONT_COLOR);
		g.setFont(COUNT_FONT);
		
		g.drawString(healthPotionCount, getScreenX() + HEALTH_POTION_OFFSET_X + healthPotionCountOffset, getScreenY() + HEALTH_POTION_OFFSET_Y + SPRITE_SIZE + COUNT_FONT.getSize() - 3);
		g.drawString(staminaPotionCount, getScreenX() + STAMINA_POTION_OFFSET_X + staminaPotionCountOffset, getScreenY() + STAMINA_POTION_OFFSET_Y + SPRITE_SIZE + COUNT_FONT.getSize() - 3);
	}
	
	@Override
	public void update() {
		setHealthPotionCount(player.getItemCount(HealthPotion.class));
		setStaminaPotionCount(player.getItemCount(StaminaPotion.class));
		
		if (player.getHealthPotionDelayTimer() > 0) {
			int index = (int) ((player.getHealthPotionDelayTimer() / (double) HealthPotion.GetUseDelay()) * 10);
			healthPotionDelaySprite = Sprite.GUI.potion_delay[index];
		} else {
			if (healthPotionDelaySprite != null) {
				healthPotionDelaySprite = null;
			}
		}
		
		if (player.getStaminaPotionDelayTimer() > 0) {
			int index = (int) ((player.getStaminaPotionDelayTimer() / (double) StaminaPotion.GetUseDelay()) * 10);
			staminaPotionDelaySprite = Sprite.GUI.potion_delay[index];
		} else {
			if (staminaPotionDelaySprite != null) {
				staminaPotionDelaySprite = null;
			}
		}
	}
	
	public void setHealthPotionCount(int healthPotionCount) {
		if (healthPotionCount < 100) {
			this.healthPotionCount = " " + healthPotionCount;
			
			if (healthPotionCount >= 10) {
				healthPotionCountOffset = 0;
			} else {
				healthPotionCountOffset = 3;
			}
			
		} else {
			healthPotionCountOffset = 3;
			this.healthPotionCount = healthPotionCount + "";
		}
	}
	public void setStaminaPotionCount(int staminaPotionCount) {
		if (staminaPotionCount < 100) {
			this.staminaPotionCount = " " + staminaPotionCount;
			
			if (staminaPotionCount >= 10) {
				staminaPotionCountOffset = 0;
			} else {
				staminaPotionCountOffset = 3;
			}
			
		} else {
			staminaPotionCountOffset = 3;
			this.staminaPotionCount = staminaPotionCount + "";
		}
	}
}
