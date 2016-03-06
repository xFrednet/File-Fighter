package com.gmail.xfrednet.filefighter.graphics.gui.components;

import com.gmail.xfrednet.filefighter.graphics.gui.GUIComponent;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

/**
 * Created by xFrednet on 06.03.2016.
 */
public class GUITitle extends GUIComponent {
	
	public static final int HEIGHT = SMALL_HEADLINE_FONT.getSize() + PADDING * 2;
	
	String title;

	public GUITitle(GUIComponent parent, int x, int y, String title) {
		super(parent, x, y, MATCH_PARENT, SMALL_HEADLINE_FONT.getSize() + PADDING * 2, PADDING);
		this.title = title;

		
	}
	
	@Override
	public void render(Graphics g) {
		g.setColor(TEXT_COLOR);
		g.setFont(SMALL_HEADLINE_FONT);
		g.drawString(title, screenX, screenY - padding + SMALL_HEADLINE_FONT.getSize());
	}
	
}
