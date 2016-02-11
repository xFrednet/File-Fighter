package com.gmail.xfrednet.filefighter.entity.projectiles;

import com.gmail.xfrednet.filefighter.entity.Entity;
import com.gmail.xfrednet.filefighter.entity.Projectile;
import com.gmail.xfrednet.filefighter.graphics.Sprite;
import com.gmail.xfrednet.filefighter.level.Level;

/**
 * Created by xFrednet on 11.02.2016.
 */
public class PaperProjectile extends Projectile {
	
	public PaperProjectile(double x, double y, Level level, String name, double direction, Entity shootingEntity) {
		super(level, name, direction, 1, 1, shootingEntity, Sprite.paper_projectile_sprite);
		super.setInfo(x, y, 14, 14, 1, 1);
	}
	
}
