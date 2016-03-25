package com.gmail.xfrednet.filefighter.level.path;

import com.gmail.xfrednet.filefighter.entity.Entity;
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
	
}
