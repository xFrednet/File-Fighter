package com.gmail.xfrednet.filefighter.entity.projectiles;

import com.gmail.xfrednet.filefighter.entity.Entity;
import com.gmail.xfrednet.filefighter.entity.Projectile;
import com.gmail.xfrednet.filefighter.graphics.Particle;
import com.gmail.xfrednet.filefighter.graphics.Sprite;
import com.gmail.xfrednet.filefighter.item.item.Damage;
import com.gmail.xfrednet.filefighter.level.Level;

/**
 * Created by xFrednet on 26.03.2016 at 10:52.
 */
public class FireProjectile  extends Projectile {
	
	public static final String NAME = "Fire ball";
	public static final int PARTICLE_TRACE_TIME = 10;
	public static final double PARTICLE_TRACE_SPEED = 0.5;
	public static final int TRACE_PARTICLE_CHANCE = 4; // 1/4
	
	/*
	* Constructor
	* */
	public FireProjectile(Level level, double direction, double speed, double range, Damage damage, Entity shootingEntity) {
		super(level, NAME, direction, speed, range, damage, shootingEntity);
		super.setInfo(shootingEntity.getInfo().getCenterX(), shootingEntity.getInfo().getCenterY(), 6, 6, 5, 5);
	}
	
	/*
	* Overridden methods
	* */
	/*@Override
	protected void projectileMoved(Level level) {
		super.projectileMoved(level);
		if (random.nextInt(TRACE_PARTICLE_CHANCE) == 0) {
			level.addParticle(new Particle(info.getCenterX(), info.getCenterY(), level, PARTICLE_TRACE_TIME, direction - Math.PI , PARTICLE_TRACE_SPEED, getParticleSprites()));
		}
	}*/
	
	@Override
	protected Sprite[] getParticleSprites() {
		return Sprite.Particles.fire_ball_particles;
	}
	
	@Override
	public Sprite getSprite() {
		return Sprite.Projectiles.fire_ball;
	}
	
	@Override
	protected int getParticlesOnDestroy() {
		return 2;
	}
}
