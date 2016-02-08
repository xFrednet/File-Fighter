package com.gmail.xfrednet.filefighter.entity;

import com.gmail.xfrednet.filefighter.Main;
import com.gmail.xfrednet.filefighter.entity.entitytask.behavior.UserInputBehavior;
import com.gmail.xfrednet.filefighter.graphics.Camera;
import com.gmail.xfrednet.filefighter.graphics.Sprite;
import com.gmail.xfrednet.filefighter.util.Input;

/**
 * Created by xFrednet on 07.02.2016.
 */
public class Player extends LivingEntity {
	
	public static final int ANIMATION_SPRITES = 16;
	public static final int ANIMATION_SPEED = (int) (Main.UPS * 0.1);
	
	Input input;
	int speed = 1;
	
	public Player(int x, int y, Input input, Camera camera, String name) {
		super(x, y, 28, 26, 2, 5, null, name);
		behavior = new UserInputBehavior(input, speed, camera);
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
		if (behavior.getClass().getName() == UserInputBehavior.class.getClass().getName()) {
			return ((UserInputBehavior) behavior).getCamera();
		} else {
			System.out.println("[ERROR] Player: Player has a different behavior getCamera returns null");
			return null;
		}
	}
}
