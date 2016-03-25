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
	
	public static final int ANIMATION_SPRITES = 16;
	
	public JPGFileEntity(int x, int y, Level level, Entity target, String name) {
		super(level, name, 0, new MoveToTarget(target.getID(), 1, 30)/*behavior*/);
		super.setInfo(x, y, 24/*width*/, 18/*height*/, 4/*spriteXOffset*/, 7/*spriteXOffset*/);
	}
	
	@Override
	protected double getBaseAttribute(int attribute) {
		switch (attribute) {
			case ATTRIBUTE_MAX_HEALTH: return 10;
			case ATTRIBUTE_MAX_STAMINA: return 20;
			case ATTRIBUTE_PHYSICAL_DEFENCE: return 1;
			case ATTRIBUTE_MENTAL_DEFENCE: return 1;
			case ATTRIBUTE_PHYSICAL_DAMAGE: return 1;
			case ATTRIBUTE_MENTAL_DAMAGE: return 1;
			case ATTRIBUTE_LUCK: return 1;
			case ATTRIBUTE_HEALTH_REGENERATION: return 0;
			case ATTRIBUTE_STAMINA_REGENERATION: return 0.2;
			default: return 0;
		}
	}
}
