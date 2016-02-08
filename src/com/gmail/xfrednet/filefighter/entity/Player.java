package com.gmail.xfrednet.filefighter.entity;

import com.gmail.xfrednet.filefighter.entity.entitytask.behavior.UserInputBehavior;
import com.gmail.xfrednet.filefighter.graphics.Sprite;
import com.gmail.xfrednet.filefighter.util.Input;

/**
 * Created by xFrednet on 07.02.2016.
 */
public class Player extends LivingEntity {
	
	public static final int ANIMATION_SPRITES = 4;
	
	Input input;
	int speed = 1;
	
	public Player(int x, int y, Input input) {
		super(x, y, 16, 26, 8, 5, null);
		behavior = new UserInputBehavior(input, speed);
	}
	
	@Override
	protected void updateCurrentSprite() {
		if (isStanding) {
			currentSprite = Sprite.player_entity_sprite[STILL_STANDING_SPRITE_INDEX];
		} else {
			currentSprite = Sprite.player_entity_sprite[(direction * ANIMATION_SPRITES) + ((int)(animation / ANIMATION_SPEED) % ANIMATION_SPRITES)];
		}
	}
}
