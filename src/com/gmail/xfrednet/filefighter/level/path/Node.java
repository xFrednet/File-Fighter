package com.gmail.xfrednet.filefighter.level.path;

import com.gmail.xfrednet.filefighter.entity.Entity;
import com.gmail.xfrednet.filefighter.graphics.Line;
import com.gmail.xfrednet.filefighter.level.Level;

/**
 * Created by xFrednet on 19.03.2016.
 */
public class Node {
	
	int x;
	int y;
	int fScore;
	Node parent;
	
	public Node(Entity entity) {
		this(entity.getInfo());
	}
	public Node(Entity.EntityInfo entity) {
		this((int) (entity.getCenterX() / Level.TILE_SIZE), (int) (entity.getCenterY() / Level.TILE_SIZE));
	}
	public Node(int x, int y) {
		this(x, y, null);
	}
	public Node(int x, int y, Node parent) {
		this.x = x;
		this.y = y;
		this.parent = parent;
	}
	
	/*
	* Util
	* */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Node) {
			Node testNode = (Node) obj;
			
			return testNode.getX() == x && testNode.getY() == y;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return this.getClass().getName() + "[x = " + x + ", y = " + y + ", fScore = " + fScore + "]";
	}
	
	public int calculateFScore(Node node) {
		int a = x - node.x;
		int b = y - node.y;
		//distance between two nodes 
		//no sqrt to save some calculations
		return a * a + b * b;
	}
	public void setUpFScore(Node node) {
		setFScore(calculateFScore(node));
	}
	
	public Node[] getNeighbors() {
		//   1
		// 2   3
		//   4
		return new Node[] {
			new Node(x    , y + 1, this),
			new Node(x - 1, y    , this),
			new Node(x + 1, y    , this),
			new Node(x    , y - 1, this),
		};
	}
	
	/*
	* setter
	* */
	public void setFScore(int fScore) {
		this.fScore = fScore;
	}
	public void nullParent() {
		parent = null;
	}
	
	/*
	* Getters
	* */
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getMapX() {
		return (x << 5);
	}
	public int getMapY() {
		return (y << 5);
	}
	
	public int getFScore() {
		return fScore;
	}
	
	public Node getParent() {
		return parent;
	}
	
	public double getAngleTo(Entity entity) {
		Entity.EntityInfo info = entity.getInfo();
		return Math.atan2((x * Level.TILE_SIZE) - info.getX(), (y * Level.TILE_SIZE) - info.getY());
	}
	public double getAngleToParent() {
		return getAngleTo(parent);
	}
	public double getAngleTo(Node node) {
		return Math.atan2(node.x - x, node.y - y);
	}
	
	public double getMapDistanceTo(Node node) {
		double a = (x - node.x) * Level.TILE_SIZE;
		double b = (y - node.y) * Level.TILE_SIZE;
		
		return Math.sqrt(a * a + b * b);
	}
	public double getMapDistanceTo(Entity entity) {
		Entity.EntityInfo info = entity.getInfo();
		double a = ((x * Level.TILE_SIZE) - info.getX());
		double b = ((y * Level.TILE_SIZE) - info.getY());
		
		return Math.sqrt(a * a + b * b);
	}
	
	public boolean clearPath(Node node, Level level) {
		//System.out.println(level.isSolid(node.getX(), node.getY()));
		if (level.isSolid(node.getX(), node.getY())) return false;
		Line line = new Line(this, node);
		int testDistance = 1;
		
		double xm = testDistance * line.getXM();
		double ym = testDistance * line.getYM();
		int distance = (int) getMapDistanceTo(node);
		
		double cx = line.getX0();
		double cy = line.getY0();
		
		for (int i = 0; i < distance; i += testDistance) {
			cx += xm;
			cy += ym;
			
			if (level.isSolid((int)cx / 32 + 1, (int)cy / 32)) {
				return false;
			}
		}
		
		return true;
	}
	
	
}
