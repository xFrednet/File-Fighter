package com.gmail.xfrednet.filefighter.entity.projectiles;

import com.gmail.xfrednet.filefighter.entity.Entity;
import com.gmail.xfrednet.filefighter.entity.LivingEntity;
import com.gmail.xfrednet.filefighter.entity.Projectile;
import com.gmail.xfrednet.filefighter.entity.areaeffects.MusicAreaEffect;
import com.gmail.xfrednet.filefighter.graphics.Screen;
import com.gmail.xfrednet.filefighter.graphics.Sprite;
import com.gmail.xfrednet.filefighter.item.item.Damage;
import com.gmail.xfrednet.filefighter.level.Level;

/**
 * Created by xFrednet on 31.03.2016.
 */
public class CDProjectile extends Projectile {
	
	MusicAreaEffect effect;
	
	public CDProjectile(Level level, double direction, double speed, double maxRange, Damage damage, Entity entity) {
		super(level, "CD", direction, speed, maxRange, damage, entity);
		setInfo(entity, 9, 9, 3, 3);
		setTeam(entity.getTeam());
		effect = new MusicAreaEffect(level, 2 * 32, this, damage);
		
	}
	
	@Override
	public void update(Level level) {
		super.update(level);
		
		effect.setPosition(level, this);
		effect.update(level);
	}
	
	@Override
	public void render(Screen screen) {
		effect.render(screen);
		super.render(screen);
	}
	
	@Override
	protected boolean hasEntityCollision() {
		return false;
	}
	
	@Override
	protected Sprite[] getParticleSprites() {
		return Sprite.Particles.cd_projectile_particles;
	}
	
	@Override
	public Sprite getSprite() {
		return Sprite.Projectiles.cd_projectile;
	}
}
