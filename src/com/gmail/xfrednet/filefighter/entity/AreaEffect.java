package com.gmail.xfrednet.filefighter.entity;

import com.gmail.xfrednet.filefighter.graphics.Particle;
import com.gmail.xfrednet.filefighter.graphics.Sprite;
import com.gmail.xfrednet.filefighter.level.Level;
import com.gmail.xfrednet.filefighter.level.path.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xFrednet on 30.03.2016.
 */
public abstract class AreaEffect extends Entity {
	
	List<Node> tiles = new ArrayList<Node>();
	
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
		updatesTiles(level);
	}
	
	/*
	* Util
	* */
	private void updatesTiles(Level level) {
		tiles.clear();
		
		Node thisNode = getThisNode();
		
		int x0 = (thisNode.getMapX() - range) / 32;
		int y0 = (thisNode.getMapY() - range) / 32;
		int x1 = (thisNode.getMapX() + range) / 32;
		int y1 = (thisNode.getMapY() + range) / 32;
		
		Node node;
		int cx = 0;
		for (;y0 < y1; y0++) {
			for (cx = x0;cx < x1; cx++) {
				node = new Node(cx, y0);
				
				if (node.getMapDistanceTo(thisNode) > range) continue;
				
				if (thisNode.clearPath(node, level)) {
					tiles.add(node);
				}
				
			}
		}
		
	}
	
	public void configureParticles(Sprite[] particles, int particlesCount, int particlesTime) {
		if (particles != null) {
			this.particles = particles;
		}
		this.particlesCount = particlesCount;
		this.particlesTime = particlesTime;
	}
	
	protected void spawnParticles(Level level) {
		if (particles == null || tiles.size() <= 0) return;
		
		int count = (int)(particlesCount * random.nextDouble());
		
		int tileX;
		int tileY;
		int spawnX;
		int spawnY;
		Node node;
		int index;
		for (int i = 0; i < count; i++) {
			tileX = (int)(random.nextDouble() * Level.TILE_SIZE);
			tileY = (int)(random.nextDouble() * Level.TILE_SIZE);
			index = random.nextInt(tiles.size());
			node = tiles.get(index);
			spawnX = node.getMapX() + tileX;
			spawnY = node.getMapY() + tileY;
			
			level.addParticle(new Particle(spawnX, spawnY, level, particlesTime, 0, 0.1, particles));
		}
	}
	
	public int getRange() {
		return range;
	}
	protected Node getThisNode() {
		return new Node(((int)getInfo().getCenterX() >> 5), ((int)getInfo().getCenterY() >> 5));
	}
}
