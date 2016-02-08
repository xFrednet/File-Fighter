package com.gmail.xfrednet.filefighter.entity.entitytask.behavior;

import com.gmail.xfrednet.filefighter.entity.LivingEntity;
import com.gmail.xfrednet.filefighter.entity.entitytask.Behavior;
import com.gmail.xfrednet.filefighter.level.Level;
import com.gmail.xfrednet.filefighter.util.Input;
import com.sun.glass.events.KeyEvent;

/**
 * Created by xFrednet on 07.02.2016.
 */
public class UserInputBehavior extends Behavior {
	
	private static final int MOVEMENT_UP_KEY = KeyEvent.VK_W;
	private static final int MOVEMENT_DOWN_KEY = KeyEvent.VK_S;
	private static final int MOVEMENT_LEFT_KEY = KeyEvent.VK_A;
	private static final int MOVEMENT_RIGHT_KEY = KeyEvent.VK_D;
	private static final int MOVEMENT_SPEED_KEY = KeyEvent.VK_E;
	
	Input input;
	double speed;
	
	public UserInputBehavior(Input input, double speed) {
		this.input = input;
		this.speed = speed;
	}
	
	@Override
	public void update(LivingEntity entity, Level level) {
		int xm = 0;
		int ym = 0;
		
		if (input.isKeyDown(MOVEMENT_UP_KEY)) ym -= speed;
		if (input.isKeyDown(MOVEMENT_DOWN_KEY)) ym += speed;
		if (input.isKeyDown(MOVEMENT_LEFT_KEY)) xm -= speed;
		if (input.isKeyDown(MOVEMENT_RIGHT_KEY)) xm += speed;
		
		//System.out.println("[INFO] Camera: xm: " + xm + ", ym " + ym);
		
		if (xm != 0 || ym != 0) {
			entity.move(xm, ym, level);
			
			if (input.isKeyDown(MOVEMENT_SPEED_KEY)) {
				entity.move(xm, ym, level);
			}
			
			entity.isStanding = false;
		} else {
			entity.isStanding = true;
		}
	}
}
