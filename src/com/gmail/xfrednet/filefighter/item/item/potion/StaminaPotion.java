package com.gmail.xfrednet.filefighter.item.item.potion;

import com.gmail.xfrednet.filefighter.Main;
import com.gmail.xfrednet.filefighter.entity.LivingEntity;
import com.gmail.xfrednet.filefighter.entity.Player;
import com.gmail.xfrednet.filefighter.graphics.Sprite;
import com.gmail.xfrednet.filefighter.graphics.gui.GUIComponentGroup;
import com.gmail.xfrednet.filefighter.graphics.gui.groups.GUIItemInfoFrame;
import com.gmail.xfrednet.filefighter.item.Item;
import com.gmail.xfrednet.filefighter.level.Level;

/**
 * Created by xFrednet on 28.03.2016 at 18:41.
 */
public class StaminaPotion extends Item {
	
	public static final int DELAY = 30;
	
	double staminaGainAmount;
	String name;
	Sprite sprite;
	
	private StaminaPotion(int count, double healAmount, String name, Sprite sprite) {
		super(count);
		this.staminaGainAmount = healAmount;
		this.name = name;
		this.sprite = sprite;
	}
	
	@Override
	public Item clone() {
		return new StaminaPotion(count, staminaGainAmount, name, sprite);
	}
	
	/*
	* Types
	* */
	public static StaminaPotion newSmallStaminaPotion(int count) {
		return new StaminaPotion(count, 30, "small stamina potion", Sprite.Item.stamina_potion);
	}
	
	/*
	* util
	* */
	@Override
	public void update(Level level) {
		Player player = level.getPlayer();
		useTimer = player.getStaminaPotionDelayTimer();
	}
	
	/*
	* getters
	* */
	@Override
	public boolean equals(Object object) {
		if (super.equals(object)) {
			StaminaPotion staminaPotion = (StaminaPotion) object;
			
			return staminaPotion.getStaminaGainAmount() == getStaminaGainAmount();
		} else {
			return false;
		}
	}
	
	@Override
	public GUIItemInfoFrame getGUIItemInfoFrame(GUIComponentGroup parent, int x, int y) {
		GUIItemInfoFrame info = super.getGUIItemInfoFrame(parent, x, y);
		info.addItemInfo(new GUIItemInfoFrame.GUItemNumberInfo(info, "Stamina ", "+ " + getStaminaGainAmount()));
		return info;
	}
	
	@Override
	public boolean usePrimaryAction(Level level, LivingEntity executingEntity, double angle) {
		if (useTimer > 0 || !(executingEntity instanceof Player)) return false;
		Player player = (Player) executingEntity;
		player.gainStamina(staminaGainAmount);
		
		count--;
		player.setStaminaPotionDelayTimer(getUseDelay());
		useTimer = player.getStaminaPotionDelayTimer();
		return true;
	}
	
	@Override
	public Sprite getItemSprite() {
		return sprite;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	protected int getMaxStackSize() {
		return 20;
	}
	
	public double getStaminaGainAmount() {
		return staminaGainAmount;
	}
	
	@Override
	public int getUseDelay() {
		return Main.UPS * 10;
	}
	public static int GetUseDelay() {
		return Main.UPS * 10;
	}
}
