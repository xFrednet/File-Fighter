package com.gmail.xfrednet.filefighter.graphics.gui.components;

import com.gmail.xfrednet.filefighter.Main;
import com.gmail.xfrednet.filefighter.graphics.gui.GUIComponent;

import java.awt.*;

/**
 * Created by xFrednet on 26.03.2016 at 00:10.
 */
public class GUILevelClearedMessage extends GUIComponent {
	public static final Color TEXT_COLOR = new Color(0xff00ff21, true);
	public static final Color BACKGROUND_COLOR = new Color(0xaa000000, true);
	
	private static final int PADDING = 10;
	private static final int WIDTH = 1170 + PADDING * 2;
	private static final int HEIGHT = 150 + PADDING * 2;
	
	private static final Font FONT = new Font(Main.gameFont.getName(), Font.PLAIN, 150);
	private static final String MESSAGE = "LEVEL CLEARED";
	
	/*
	* Class
	* */
	Color textColor = TEXT_COLOR;
	Color backgroundColor = BACKGROUND_COLOR;
	double multiplier = 0.1;
	boolean rising = true;
	
	public GUILevelClearedMessage(GUIComponent parent) {
		super(parent, (Main.WIDTH * Main.scale) / 2 - WIDTH / 2, (Main.HEIGHT * Main.scale) / 2 - HEIGHT / 2, WIDTH, HEIGHT);
	}
	
	@Override
	public void update() {
		if (rising) {
			multiplier += 0.025;
			if (multiplier >= 1) {
				rising = false;
				multiplier = 1;
			}
		} else {
			multiplier -= 0.025;
			if (multiplier <= 0.1) {
				remove();
				return;
			}
		}
		
		textColor = new Color(TEXT_COLOR.getRed(),
				TEXT_COLOR.getGreen(),
				TEXT_COLOR.getBlue(),
				(int) (TEXT_COLOR.getAlpha() * multiplier));
		backgroundColor = new Color(BACKGROUND_COLOR.getRed(),
				BACKGROUND_COLOR.getGreen(),
				BACKGROUND_COLOR.getBlue(),
				(int) (BACKGROUND_COLOR.getAlpha() * multiplier));
	}
	
	@Override
	public void render(Graphics g) {
		g.setColor(backgroundColor);
		g.fillRect(getScreenX(), getScreenY(), width, height);
		
		g.setColor(textColor);
		g.setFont(FONT);
		g.drawString(MESSAGE, getScreenX() + PADDING, getScreenY() + PADDING + 118);
	}
	
}
