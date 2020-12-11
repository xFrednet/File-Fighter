package com.gmail.xfrednet.filefighter.entity.livingentitys.enemy;

import com.gmail.xfrednet.filefighter.entity.EnemyEntity;
import com.gmail.xfrednet.filefighter.level.Level;

/**
 * Created by xFrednet on 15.03.2016.
 */
public class FileEntity extends EnemyEntity {
	public FileEntity(double x, double y, Level level, String name) {
		super(level, name, 0);
		super.setInfo(x, y);
	}
}
