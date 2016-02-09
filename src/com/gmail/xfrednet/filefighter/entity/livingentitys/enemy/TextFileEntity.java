package com.gmail.xfrednet.filefighter.entity.livingentitys.enemy;

import com.gmail.xfrednet.filefighter.entity.EnemyEntity;
import com.gmail.xfrednet.filefighter.entity.Entity;
import com.gmail.xfrednet.filefighter.entity.entitytask.Behavior;
import com.gmail.xfrednet.filefighter.entity.entitytask.behavior.MoveToTarget;
import com.gmail.xfrednet.filefighter.graphics.Sprite;

/**
 * Created by xFrednet on 08.02.2016.
 */
public class TextFileEntity extends EnemyEntity {
	
	public static final int ANIMATION_SPRITES = 15;
	
	public TextFileEntity(int x, int y, Entity target, String name) {
		super(x, y, 26/*width*/, 27/*height*/, 3/*spriteXOffset*/, 4/*spriteXOffset*/, new MoveToTarget(target.getID(), 1, 30)/*behavior*/, name);
	}
	
	@Override
	protected void updateCurrentSprite() {
		if (isStanding) {
			currentSprite = Sprite.textFile_entity_sprite[STILL_STANDING_SPRITE_INDEX];
		} else {
			currentSprite = Sprite.textFile_entity_sprite[(direction * ANIMATION_SPRITES) + ((int)(animation / ANIMATION_SPEED) % ANIMATION_SPRITES)];
		}
	}
	
}
