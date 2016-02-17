package com.gmail.xfrednet.filefighter.entity.livingentitys.enemy;

import com.gmail.xfrednet.filefighter.entity.EnemyEntity;
import com.gmail.xfrednet.filefighter.entity.Entity;
import com.gmail.xfrednet.filefighter.entity.entitytask.behavior.MoveToTarget;
import com.gmail.xfrednet.filefighter.graphics.Sprite;
import com.gmail.xfrednet.filefighter.level.Level;

/**
 * Created by xFrednet on 12.02.2016.
 */
public class JPGFileEntity extends EnemyEntity {
	
	public static final double MAX_HEALTH = 10;
	public static final int ANIMATION_SPRITES = 16;
	
	public JPGFileEntity(int x, int y, Level level, Entity target, String name) {
		super(level, name, new MoveToTarget(target.getID(), 1, 30)/*behavior*/);
		super.setInfo(x, y, 24/*width*/, 18/*height*/, 4/*spriteXOffset*/, 7/*spriteXOffset*/);
		setAttributes(MAX_HEALTH);
	}
	
	@Override
	protected void updateCurrentSprite() {
		if (isStanding) {
			currentSprite = Sprite.jpgFile_entity_sprites[STILL_STANDING_SPRITE_INDEX];
		} else {
			currentSprite = Sprite.jpgFile_entity_sprites[(direction * ANIMATION_SPRITES) + ((int)(animation / ANIMATION_SPEED) % ANIMATION_SPRITES)];
		}
	}
}
