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
	
	public GUIBackground(GUIComponent parent) {
		this(parent, 0, 0, MATCH_PARENT, MATCH_PARENT, BACKGROUND_COLOR);
	}
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
			g.setColor(graphicsColor);
			g.fillRect(screenX, screenY, width, height);
			
			g.setColor(SEPARATOR_COLOR);
			g.drawRect(screenX + 1, screenY + 1, width - 3, height - 3);
			g.drawRect(screenX, screenY, width - 1, height - 1);
			return;
		}	
		
		if (type == SCREEN_BACKGROUND) {
			g.setColor(SEPARATOR_COLOR);
			int x = screenX - screenX % 3;
			int y = screenY - screenY % 3;
			g.drawRect(x + 1, y + 1, width - 3, height - 3);
			g.drawRect(x, y, width - 1, height - 1);
		}
	}
	
	@Override
	public void render(Screen screen) {
		if (type == SCREEN_BACKGROUND) {
			screen.drawFilledRectangle(screenX / Main.scale, screenY/ Main.scale, width / Main.scale, height / Main.scale, screenBackgroundColor, true);
		}
	}
}
