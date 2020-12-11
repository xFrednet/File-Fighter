package com.gmail.xfrednet.filefighter.entity.areaeffects;

import com.gmail.xfrednet.filefighter.entity.AreaEffect;
import com.gmail.xfrednet.filefighter.entity.Entity;
import com.gmail.xfrednet.filefighter.entity.LivingEntity;
import com.gmail.xfrednet.filefighter.graphics.Line;
import com.gmail.xfrednet.filefighter.graphics.Screen;
import com.gmail.xfrednet.filefighter.graphics.Sprite;
import com.gmail.xfrednet.filefighter.item.item.Damage;
import com.gmail.xfrednet.filefighter.level.Level;
import com.sun.istack.internal.Nullable;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by xFrednet on 31.03.2016.
 */
public class MusicAreaEffect extends AreaEffect {
	
	private List<Line> lines = new ArrayList<>();
	private Damage damage;
	
	public MusicAreaEffect(Level level, double range, Entity spawningEntity, Damage damage) {
		super(level, spawningEntity.getInfo().getCenterX(), spawningEntity.getInfo().getCenterY(), range, Sprite.Particles.music_particles);
		this.damage = damage;
		
		setTeam(spawningEntity.getTeam());
	}
	
	@Override
	public void update(Level level) {
		super.spawnParticles(level);
		
		lines.clear();
		
		List<LivingEntity> targets = level.getEnemies(this, getRange());
		
		for (int i = 0; i < targets.size(); i++) {
			if (isInArea(targets.get(i))) {
				targets.get(i).damage(level, this, damage);
				
				lines.add(new Line(this, targets.get(i)));
			}
		}
	}
	
	@Override
	protected void sizeChanged() {
		int count = tiles.size() / 10;
		if (count > 0) {
			configureParticles(Sprite.Particles.music_particles, count, 50);
		} else {
			configureParticles(Sprite.Particles.music_particles, 1, 50);
		}
	}
	
	@Override
	public void render(Screen screen) {
		super.render(screen);
		
		for (int i = 0; i < lines.size(); i++) {
			screen.drawLine(lines.get(i), 0xffffd800, false);
		}
	}
}
