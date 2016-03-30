package com.gmail.xfrednet.filefighter.entity.areaeffects;

import com.gmail.xfrednet.filefighter.entity.AreaEffect;
import com.gmail.xfrednet.filefighter.entity.LivingEntity;
import com.gmail.xfrednet.filefighter.graphics.Line;
import com.gmail.xfrednet.filefighter.graphics.Screen;
import com.gmail.xfrednet.filefighter.graphics.Sprite;
import com.gmail.xfrednet.filefighter.level.Level;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xFrednet on 30.03.2016.
 */
public class ElectricArea extends AreaEffect {
	
	List<Line> lines = new ArrayList<>();
	
	public ElectricArea(Level level, double x, double y, double range) {
		super(level, x, y, range, Sprite.Particles.electric_particles);
		configureParticles(null, 10, 100);
	}
	
	@Override
	public void update(Level level) {
		super.spawnParticles(level);
		
		List<LivingEntity> targets = level.getEnemies(this, getRange());
		
		for (int i = 0; i < targets.size(); i++) {
			lines.add(new Line(this, targets.get(i)));
		}
		
		//for (int i = 0; i < targets.size(); i++) {
		//	targets.get(i).damage(level, executingEntity, getDamage());
		//}
	}
	
	@Override
	public void render(Screen screen) {
		super.render(screen);
		
		for (int i = 0; i < lines.size(); i++) {
			screen.drawLine(lines.get(i), 0xffffd800, false);
		}
	}
}
