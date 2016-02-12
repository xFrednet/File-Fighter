package com.gmail.xfrednet.filefighter.entity.entitytask.behavior;

import com.gmail.xfrednet.filefighter.entity.LivingEntity;
import com.gmail.xfrednet.filefighter.entity.Projectile;
import com.gmail.xfrednet.filefighter.entity.entitytask.Behavior;
import com.gmail.xfrednet.filefighter.entity.projectiles.PaperProjectile;
import com.gmail.xfrednet.filefighter.graphics.Camera;
import com.gmail.xfrednet.filefighter.level.Level;
import com.gmail.xfrednet.filefighter.util.Input;
import com.sun.glass.events.KeyEvent;
import com.sun.glass.events.MouseEvent;

/**
 * Created by xFrednet on 07.02.2016.
 */
public class UserInputBehavior extends Behavior {
	
	private static final int MOVEMENT_UP_KEY = KeyEvent.VK_W;
	private static final int MOVEMENT_DOWN_KEY = KeyEvent.VK_S;
	private static final int MOVEMENT_LEFT_KEY = KeyEvent.VK_A;
	private static final int MOVEMENT_RIGHT_KEY = KeyEvent.VK_D;
	private static final int MOVEMENT_SPEED_KEY = KeyEvent.VK_E;
	private static final int DISCONNECT_CAMERA = KeyEvent.VK_NUMPAD0;
	
	private static final int MOUSE_ATTACK_BUTTON = Input.LEFT_MOUSE_BUTTON;
	
	Input input;
	Camera camera;
	double speed;
	
	public UserInputBehavior(Input input, double speed) {
		this.input = input;
		this.speed = speed;
		this.camera = null;
	}
	public UserInputBehavior(Input input, double speed, Camera camera) {
		this.input = input;
		this.speed = speed;
		this.camera = camera;
	}
	
	@Override
	public void update(LivingEntity entity, Level level) {
		movement(entity, level);
		attack(entity, level);
	}
	private void movement(LivingEntity entity, Level level) {
		int xm = 0;
		int ym = 0;
		
		if (input.isKeyDown(MOVEMENT_UP_KEY)) ym -= 1;
		if (input.isKeyDown(MOVEMENT_DOWN_KEY)) ym += 1;
		if (input.isKeyDown(MOVEMENT_LEFT_KEY)) xm -= 1;
		if (input.isKeyDown(MOVEMENT_RIGHT_KEY)) xm += 1;
		
		//System.out.println("[INFO] UserInputBehavior: xm: " + xm + ", ym " + ym);
		
		if (xm != 0 || ym != 0) {
			entity.move(Math.atan2(xm, ym), level, speed);
			
			if (input.isKeyDown(MOVEMENT_SPEED_KEY)) {
				entity.move(Math.atan2(xm, ym), level, speed);
			}
			
			if (camera != null)
				if (!input.isKeyDown(DISCONNECT_CAMERA)) {
					camera.centerXOn((int) entity.getInfo().getCenterX());
					camera.centerYOn((int) entity.getInfo().getCenterY());
				}
			
			entity.isStanding = false;
		} else {
			entity.isStanding = true;
		}
	}
	private void attack(LivingEntity entity, Level level) {
		if (input.isMouseButtonDown(MOUSE_ATTACK_BUTTON)) {
			double angle = entity.getAngleTo(input.getMouseLevelX(level), input.getMouseLevelY(level));
			Projectile p = new PaperProjectile(level, "NAME", angle, entity);
			level.add(p);	
		}
	}
	
	public Camera getCamera() {
		return camera;
	}
	public void setCamera(Camera camera) {
		this.camera = camera;
	}
	
}
