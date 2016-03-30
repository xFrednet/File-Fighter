package com.gmail.xfrednet.filefighter.graphics;

import com.gmail.xfrednet.filefighter.entity.Entity;
import com.gmail.xfrednet.filefighter.level.path.Node;

/**
 * Created by xFrednet on 30.03.2016.
 */
public class Line {
	
	private static final double NO_ANGLE = -100;
	
	private int x0;
	private int y0;
	private int x1;
	private int y1;
	
	private double angle = NO_ANGLE;
	private int distance = -1;
	
	public Line(Entity e0, Entity e1) {
		this((int)e0.getInfo().getCenterX(), (int)e0.getInfo().getCenterY(), (int)e1.getInfo().getCenterX(), (int)e1.getInfo().getCenterY());
	}
	public Line(Node node, Node node1) {
		this(node.getMapX(), node.getMapY(), node1.getMapX(), node1.getMapY());
	}
	public Line(int x0, int y0, int x1, int y1) {
		this.x0 = x0;
		this.y0 = y0;
		this.x1 = x1;
		this.y1 = y1;
	}
	
	public double getAngle() {
		if (angle == NO_ANGLE) {
			angle = Math.atan2(y1 - y0, x1 - x0);
		}
		
		return angle;
	}
	
	public int getDistance() {
		if (distance == -1) {
			int width = x1 - x0;
			int height = y1 - y0;
			distance = (int) Math.sqrt(width * width + height * height);
		}
		return distance;
	}
	
	public int getX0() {
		return x0;
	}
	public int getY0() {
		return y0;
	}
	
	public double getXM() {
		return Math.cos(getAngle());
	}
	public double getYM() {
		return Math.sin(getAngle());
	}
}
