package com.gmail.xfrednet.filefighter.graphics.gui.components;

import com.gmail.xfrednet.filefighter.Main;
import com.gmail.xfrednet.filefighter.graphics.Screen;
import com.gmail.xfrednet.filefighter.graphics.gui.GUIComponent;

import java.awt.*;

/**
 * Created by xFrednet on 20.02.2016.
 */
public class GUIBackground extends GUIComponent {
	
	public static final int SCREEN_BACKGROUND = 0;
	public static final int GRAPHICS_BACKGROUND = 1;
	
	int type;
	int screenBackgroundColor;
	Color graphicsColor;
	
	public GUIBackground(GUIComponent parent, int x, int y, int width, int height, Color color) {
		super(parent, x, y, width, height);
		graphicsColor = color;
		type = GRAPHICS_BACKGROUND;
	}
	public GUIBackground(GUIComponent parent, int x, int y, int width, int height, int color) {
		super(parent, x, y, width, height);
		screenBackgroundColor = color;
		type = SCREEN_BACKGROUND;
		
		
		
	}
	
	@Override
	public void render(Graphics g) {
		if (type == GRAPHICS_BACKGROUND) {
			g.fillRect(screenX, screenY, width, height);
		}	
	}
	
	@Override
	public void render(Screen screen) {
		if (type == SCREEN_BACKGROUND) {
			screen.drawFilledRectangle(screenX / Main.scale, screenY/ Main.scale, width / Main.scale, height / Main.scale, screenBackgroundColor, true);
		}
	}
}
