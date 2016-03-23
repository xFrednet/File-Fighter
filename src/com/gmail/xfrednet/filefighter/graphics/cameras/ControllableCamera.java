package com.gmail.xfrednet.filefighter.graphics.cameras;

import com.gmail.xfrednet.filefighter.graphics.Camera;
import com.gmail.xfrednet.filefighter.graphics.Screen;
import com.gmail.xfrednet.filefighter.level.Level;
import com.gmail.xfrednet.filefighter.util.Input;
import com.sun.glass.events.KeyEvent;

/**
 * Created by xFrednet on 06.02.2016.
 */
public class ControllableCamera extends Camera {
	
	private static final int CAMERA_UP_KEY = KeyEvent.VK_UP;
	private static final int CAMERA_DOWN_KEY = KeyEvent.VK_DOWN;
	private static final int CAMERA_LEFT_KEY = KeyEvent.VK_LEFT;
	private static final int CAMERA_RIGHT_KEY = KeyEvent.VK_RIGHT;
	private static final int CAMERA_SPEED_KEY = KeyEvent.VK_CONTROL;
	
	private Input input;
	private int speed = 1;
	
	/*
	* Constructor
	* */
	public ControllableCamera(Level level, Screen screen, Input input) {
		this(0, 0, level, screen, input);
		this.input = input;
	}
	public ControllableCamera(int x, int y, Level level, Screen screen, Input input) {
		super(x, y, level, screen);
		if (input == null) System.out.println("[ERROR] ControllableCamera: input = null");
		this.input = input;
	}
	
	@Override
	public void update() {
		int xm = 0;
		int ym = 0;
		
		if (input.isKeyDown(CAMERA_UP_KEY)) ym -= speed;
		if (input.isKeyDown(CAMERA_DOWN_KEY)) ym += speed;
		if (input.isKeyDown(CAMERA_LEFT_KEY)) xm -= speed;
		if (input.isKeyDown(CAMERA_RIGHT_KEY)) xm += speed;
		
		//System.out.println("[INFO] Camera: xm: " + xm + ", ym " + ym);
		
		if (xm != 0 || ym != 0) {
			moveCamera(x + xm, y + ym);
			
			if (input.isKeyDown(CAMERA_SPEED_KEY)) {
				moveCamera(x + xm, y + ym);
			}
		}
	}
}
