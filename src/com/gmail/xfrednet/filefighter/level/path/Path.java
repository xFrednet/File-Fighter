package com.gmail.xfrednet.filefighter.level.path;

import com.gmail.xfrednet.filefighter.entity.Entity;
import com.gmail.xfrednet.filefighter.entity.LivingEntity;
import com.gmail.xfrednet.filefighter.level.Level;
import com.sun.javafx.beans.annotations.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by xFrednet on 19.03.2016.
 */
public class Path {
	
	List<Node> nodes;
	Node cNode;
	int index;
	
	/*
	* Constructors
	* */
	public Path(Level level, Entity entity, Entity target) {
		this(level, entity, new Node(target));
	}
	public Path(Level level, Entity entity, Node goal) {
		this(level, new Node(entity), goal);
	}
	public Path(Level level, Node start, Node goal) {
		this(getPath(level, start, goal));
	}
	public Path(List<Node> nodes) {
		this.nodes = nodes;
		index = -1;
		newCNode();
	}
	
	/*
	* Util
	* */
	/*
	* returns true if it has finished the Path 
	* */
	public boolean followPath(LivingEntity entity, Level level, double speed) {
		if (hasFinished()) return true;
		
		double angle = cNode.getAngleTo(entity);
		double distance = cNode.getMapDistanceTo(entity);
		
		if (speed <= distance) {
			entity.move(angle, level, speed);
		} else {
			entity.move(angle, level, distance);
			speed -= distance;
			
			newCNode();
			return followPath(entity, level, speed);
		}
		return false;
	}
	
	private void newCNode() {
		index++;
		if (index >= nodes.size()) {
			cNode = null;
			return;
		}
		cNode = nodes.get(index);
	}
	
	/*
	* Pathfinder 
	* */
	public static List<Node> getPath(@NonNull Level level, @NonNull Node start, @NonNull Node goal) {
		
		List<Node> openList = new ArrayList<>();
		List<Node> closedList = new ArrayList<>();
		
		//adding start
		start.setUpFScore(goal);
		openList.add(start);
		
		//Values used in the loop
		int i;
		Node cNode;
		int minFScore;
		int minFScoreIndex;
		
		while (!openList.isEmpty()) {
			
			//gets the Node with the lowest fScore
			minFScoreIndex = 0;
			minFScore = openList.get(minFScoreIndex).getFScore();
			
			for (i = 0; i < openList.size(); i++) {
				if (openList.get(i).getFScore() < minFScore) {
					minFScoreIndex = 1;
					minFScore = openList.get(i).getFScore();
				}
			}
			
			//setting cNode
			cNode = openList.get(minFScoreIndex);
			openList.remove(cNode);
			closedList.add(cNode);
			
			//tests if the goal is reached
			if (cNode.equals(goal)) {
				return generatePath(cNode);
			}
			
			//Availability Test
			if (level.isSolid(cNode.getX(), cNode.getY())) {
				continue;
			}
			
			//Adding neighbors to the openList 
			for (Node neighbor : cNode.getNeighbors()) {
				//continues is the node was already Tested
				if (closedList.contains(neighbor) || openList.contains(neighbor)) continue;
				
				//adds the Node
				openList.add(neighbor);
				neighbor.calculateFScore(goal);
				
			}
			
		}
		
		return null;
		
	}
	
	private static List<Node> generatePath(Node cNode) {
		//parts
		List<Node> path = new ArrayList<>();
		double angle = 666;
		
		while (cNode.getParent() != null) {
			if (cNode.getAngleToParent() != angle) {
				angle = cNode.getAngleToParent();
				path.add(cNode);
			}
			cNode = cNode.getParent();
		}
		path.add(cNode);
		
		//nulling the parent to free up memory
		for (int i = 0; i < path.size(); i++) {
			path.get(i).nullParent();
		}
		
		//sorting the array
		Collections.reverse(path);
		
		return path;
	}
	
	/*
	* getters
	* */
	public boolean hasFinished() {
		return (index >= nodes.size() || cNode == null);
	}
	
}
