package com.gmail.xfrednet.filefighter.entity;

import com.gmail.xfrednet.filefighter.Main;
import com.gmail.xfrednet.filefighter.entity.entitytask.behavior.UserInputBehavior;
import com.gmail.xfrednet.filefighter.graphics.Camera;
import com.gmail.xfrednet.filefighter.graphics.Sprite;
import com.gmail.xfrednet.filefighter.item.item.weapon.gun.PaperGun;
import com.gmail.xfrednet.filefighter.level.Level;
import com.gmail.xfrednet.filefighter.util.Input;

/**
 * Created by xFrednet on 07.02.2016.
 */
public class Player extends LivingEntity {
	
	public static final int ANIMATION_SPRITES = 8;
	public static final int ANIMATION_SPEED = ((int) (Main.UPS * 0.1) == 0) ? 1 : (int) (Main.UPS * 0.1);
	
	double speed = 1.5;
	
	public Player(int x, int y, Input input, Camera camera, String name, Level level) {
		super(level, name, 200);
		setInfo(x, y, 18, 32, 7, 0);
		behavior = new UserInputBehavior(input, speed, camera);
		weapon = new PaperGun();
		team = PLAYER_TEAM;
	}
	public Player(int x, int y, Input input, String name, Level level) {
		this(x, y, input, null, name, level);
	}
	
	@Override
	protected void updateCurrentSprite() {
		if (isStanding) {
			currentSprite = Sprite.player_entity_sprites[STILL_STANDING_SPRITE_INDEX];
		} else {
			currentSprite = Sprite.player_entity_sprites[(direction * ANIMATION_SPRITES) + ((int)(animation / ANIMATION_SPEED) % ANIMATION_SPRITES)];
		}
	}
	
	@Override
	protected double getBaseAttribute(int attribute) {
		switch (attribute) {
			case ATTRIBUTE_MAX_HEALTH: return 100;
			case ATTRIBUTE_MAX_STAMINA: return 100;
			case ATTRIBUTE_PHYSICAL_DEFENCE: return 1;
			case ATTRIBUTE_MENTAL_DEFENCE: return 1;
			case ATTRIBUTE_STRENGTH: return 1;
			case ATTRIBUTE_INTELLIGENCE: return 1;
			case ATTRIBUTE_LUCK: return 1;
			case ATTRIBUTE_HEALTH_REGENERATION: return 0.01;
			case ATTRIBUTE_STAMINA_REGENERATION: return 0.2;
			default: return 0;
		}
	}
	
	public Camera getCamera() {
		if (behavior.getClass().getName() == UserInputBehavior.class.getName()) {
			return ((UserInputBehavior) behavior).getCamera();
		} else {
			System.out.println("[ERROR] Player: Player has a different behavior getCamera returns null, Current behavior: " + behavior.getClass().getName());
			return null;
		}
	}
	
	public void setCamera(Camera camera) {
		if (behavior.getClass().getName() == UserInputBehavior.class.getName()) {
			((UserInputBehavior) behavior).setCamera(camera);
		} else {
			System.out.println("[ERROR] Player: Player has a different behavior can not set Camera, Current behavior: " + behavior.getClass().getName());
			return;
		}
	}
	
	protected boolean autoSpendSkillPoints() {
		return false;
	}
	
}
