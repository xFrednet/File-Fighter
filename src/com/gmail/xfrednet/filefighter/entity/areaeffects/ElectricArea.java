package com.gmail.xfrednet.filefighter.entity.areaeffects;

import com.gmail.xfrednet.filefighter.Main;
import com.gmail.xfrednet.filefighter.entity.AreaEffect;
import com.gmail.xfrednet.filefighter.entity.Entity;
import com.gmail.xfrednet.filefighter.entity.LivingEntity;
import com.gmail.xfrednet.filefighter.graphics.Line;
import com.gmail.xfrednet.filefighter.graphics.Screen;
import com.gmail.xfrednet.filefighter.graphics.Sprite;
import com.gmail.xfrednet.filefighter.item.item.Damage;
import com.gmail.xfrednet.filefighter.level.Level;
import com.gmail.xfrednet.filefighter.level.path.Node;
import com.sun.istack.internal.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by xFrednet on 30.03.2016.
 */
public class ElectricArea extends AreaEffect {
	
	public static final double DEFAULT_DAMAGE = 3;
	
	private List<Line> lines = new ArrayList<>();
	private Damage damage;
	
	public ElectricArea(Level level, double x, double y, double range) {
		this(level, x, y, range, null);
	}
	public ElectricArea(Level level, double x, double y, double range, @Nullable Entity spawningEntity) {
		this(level, x, y, range, spawningEntity, new Damage(Damage.MENTAL_DAMAGE, DEFAULT_DAMAGE));
	}
	public ElectricArea(Level level, double x, double y, double range, @Nullable Entity spawningEntity, Damage damage) {
		super(level, x, y, range, Sprite.Particles.electric_particles);
		this.damage = damage;
		
		if (spawningEntity != null) {
			setTeam(spawningEntity.getTeam());
		} else {
			setTeam(NO_TEAM);
		}
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
	public void render(Screen screen) {
		super.render(screen);
		
		for (int i = 0; i < lines.size(); i++) {
			screen.drawLine(lines.get(i), 0xffffd800, false);
		}
	}
}
