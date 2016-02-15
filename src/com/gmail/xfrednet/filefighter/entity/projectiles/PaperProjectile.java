package com.gmail.xfrednet.filefighter.entity.projectiles;

import com.gmail.xfrednet.filefighter.entity.Entity;
import com.gmail.xfrednet.filefighter.entity.Projectile;
import com.gmail.xfrednet.filefighter.graphics.Sprite;
import com.gmail.xfrednet.filefighter.level.Level;

/**
 * Created by xFrednet on 11.02.2016.
 */
public class PaperProjectile extends Projectile {
	
	public static final String NAME = "Paper ball";
	
	public PaperProjectile(Level level, double direction, Entity shootingEntity) {
		super(level, NAME, direction, 10, 1, shootingEntity, Sprite.paper_projectile_sprite);
		super.setInfo(shootingEntity.getInfo().getCenterX(), shootingEntity.getInfo().getCenterY(), 6, 6, 5, 5);
	}
	
	@Override
	protected Sprite[] getParticleSprites() {
		return Sprite.paper_projectile_particles;
	}
	
	@Override
	protected Sprite getSprite() {
		return Sprite.paper_projectile_sprite;
	}
}
