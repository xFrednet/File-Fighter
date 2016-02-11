package com.gmail.xfrednet.filefighter.entity;

import com.gmail.xfrednet.filefighter.Main;
import com.gmail.xfrednet.filefighter.entity.entitytask.behavior.UserInputBehavior;
import com.gmail.xfrednet.filefighter.graphics.Camera;
import com.gmail.xfrednet.filefighter.graphics.Sprite;
import com.gmail.xfrednet.filefighter.level.Level;
import com.gmail.xfrednet.filefighter.util.Input;

/**
 * Created by xFrednet on 07.02.2016.
 */
public class Player extends LivingEntity {
	
	public static final int ANIMATION_SPRITES = 16;
	public static final int ANIMATION_SPEED = (int) (Main.UPS * 0.1);
	
	Input input;
	double speed = 1.5;
	
	public Player(int x, int y, Input input, Camera camera, String name, Level level) {
		super(level, name);
		super.setInfo(x, y, 28, 26, 2, 5);
		behavior = new UserInputBehavior(input, speed, camera);
	}
	public Player(int x, int y, Input input, String name, Level level) {
		this(x, y, input, null, name, level);
	}
	
	
	@Override
	protected void updateCurrentSprite() {
		if (isStanding) {
			currentSprite = Sprite.player_entity_sprite[STILL_STANDING_SPRITE_INDEX];
		} else {
			currentSprite = Sprite.player_entity_sprite[(direction * ANIMATION_SPRITES) + ((int)(animation / ANIMATION_SPEED) % ANIMATION_SPRITES)];
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
	
}
