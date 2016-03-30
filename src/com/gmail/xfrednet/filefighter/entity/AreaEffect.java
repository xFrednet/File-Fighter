package com.gmail.xfrednet.filefighter.entity;

import com.gmail.xfrednet.filefighter.graphics.Particle;
import com.gmail.xfrednet.filefighter.graphics.Sprite;
import com.gmail.xfrednet.filefighter.level.Level;

/**
 * Created by xFrednet on 30.03.2016.
 */
public abstract class AreaEffect extends Entity {
	
	protected int range;
	//particles
	protected Sprite[] particles;
	protected int particlesCount = 3;
	protected int particlesTime = 10;
	
	/*
	* constructor
	* */
	protected AreaEffect(Level level, double x, double y, double range, Sprite[] particles) {
		super(level, "", false);
		setInfo(x, y, 0, 0, 0, 0);
		
		this.particles = particles;
		this.range = (int)range;
	}
	
	public void configureParticles(Sprite[] particles, int particlesCount, int particlesTime) {
		if (particles != null) {
			this.particles = particles;
		}
		this.particlesCount = particlesCount;
		this.particlesTime = particlesTime;
	}
	
	protected void spawnParticles(Level level) {
		if (particles == null) return;
		
		int count = (int)(particlesCount * random.nextDouble());
		
		int x;
		int y;
		int doubleRange = (range * 2);
		for (int i = 0; i < count; i++) {
			x = (int)(getInfo().getCenterX() + (doubleRange * random.nextDouble()) - range);
			y = (int)(getInfo().getCenterY() + (doubleRange * random.nextDouble()) - range);
			
			level.addParticle(new Particle(x, y, level, particlesTime, 0, 0.1, particles));
		}
	}
	
	public int getRange() {
		return range;
	}
}
