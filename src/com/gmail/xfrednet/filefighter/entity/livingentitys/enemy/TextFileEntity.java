package com.gmail.xfrednet.filefighter.entity.livingentitys.enemy;

import com.gmail.xfrednet.filefighter.entity.EnemyEntity;
import com.gmail.xfrednet.filefighter.entity.Entity;
import com.gmail.xfrednet.filefighter.entity.entitytask.behavior.MoveToTarget;
import com.gmail.xfrednet.filefighter.graphics.Sprite;
import com.gmail.xfrednet.filefighter.level.Level;

/**
 * Created by xFrednet on 08.02.2016.
 */
public class TextFileEntity extends EnemyEntity {
	
	public static final int ANIMATION_SPRITES = 6;
	public static final double MAX_HEALTH = 10;
	
	public TextFileEntity(int x, int y, Level level, Entity target, String name) {
		super(level, name, new MoveToTarget(target.getID(), 1, 30)/*behavior*/);
		super.setInfo(x, y, 26/*width*/, 27/*height*/, 3/*spriteXOffset*/, 4/*spriteXOffset*/);
		setAttributes(MAX_HEALTH);
	}
	
	@Override
	protected void updateCurrentSprite() {
		if (isStanding) {
			currentSprite = Sprite.textFile_entity_sprites[STILL_STANDING_SPRITE_INDEX];
		} else {
			currentSprite = Sprite.textFile_entity_sprites[(direction * ANIMATION_SPRITES) + ((int)(animation / ANIMATION_SPEED) % ANIMATION_SPRITES)];
		}
	}
	
}
