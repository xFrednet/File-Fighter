package com.gmail.xfrednet.filefighter.entity.projectiles;

import com.gmail.xfrednet.filefighter.entity.Entity;
import com.gmail.xfrednet.filefighter.entity.LivingEntity;
import com.gmail.xfrednet.filefighter.entity.Projectile;
import com.gmail.xfrednet.filefighter.graphics.Particle;
import com.gmail.xfrednet.filefighter.graphics.Sprite;
import com.gmail.xfrednet.filefighter.item.item.Damage;
import com.gmail.xfrednet.filefighter.level.Level;

/**
 * Created by xFrednet on 11.02.2016.
 */
public class PaperProjectile extends Projectile {
	
	public static final String NAME = "Paper ball";
	public static final int PARTICLE_TRACE_TIME = 10;
	public static final double PARTICLE_TRACE_SPEED = 0.5;
	public static final int TRACE_PARTICLE_CHANCE = 5; // 1/5
	public static final int DAMAGE_TYPE = Damage.PHYSICAL_DAMAGE;
	
	/*
	* Constructor
	* */
	public PaperProjectile(Level level, double direction, Entity shootingEntity, double speed, double damage) {
		super(level, NAME, direction, speed, new Damage(DAMAGE_TYPE, damage, shootingEntity), shootingEntity, Sprite.paper_projectile_sprite);
		super.setInfo(shootingEntity.getInfo().getCenterX(), shootingEntity.getInfo().getCenterY(), 6, 6, 5, 5);
	}
	
	/*
	* Overridden methods
	* */
	
	@Override
	protected void projectileMoved(Level level) {
		super.projectileMoved(level);
		if (random.nextInt(TRACE_PARTICLE_CHANCE) == 0) {
			level.addParticle(new Particle(info.getCenterX(), info.getCenterY(), level, PARTICLE_TRACE_TIME, direction - Math.PI , PARTICLE_TRACE_SPEED, getParticleSprites()));
		}
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
