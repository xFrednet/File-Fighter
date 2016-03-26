package com.gmail.xfrednet.filefighter.entity.livingentitys.enemy;

import com.gmail.xfrednet.filefighter.entity.EnemyEntity;
import com.gmail.xfrednet.filefighter.entity.Entity;
import com.gmail.xfrednet.filefighter.graphics.Sprite;
import com.gmail.xfrednet.filefighter.level.Level;

/**
 * Created by xFrednet on 12.02.2016.
 */
public class JPGFileEntity extends EnemyEntity {
	
	public static final int ANIMATION_SPRITES = 16;
	
	public JPGFileEntity(int x, int y, Level level, Entity target, String name) {
		super(level, name, 0);
		super.setInfo(x, y, 24/*width*/, 18/*height*/, 4/*spriteXOffset*/, 7/*spriteXOffset*/);
	}
}
