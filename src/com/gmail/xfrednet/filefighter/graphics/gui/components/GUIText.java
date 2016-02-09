package com.gmail.xfrednet.filefighter.graphics.gui.components;

import com.gmail.xfrednet.filefighter.Main;
import com.gmail.xfrednet.filefighter.graphics.gui.GUIComponent;
import com.gmail.xfrednet.filefighter.graphics.gui.GUIComponentGroup;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * Created by xFrednet on 09.02.2016.
 */
public class GUIText extends GUIComponent {
	
	public static final Color DEFAULT_COLOR = new Color(0xff000000, true);
	public static final Color DEFAULT_BACKGROUND_COLOR = new Color(0xffcccccc, true);
	
	protected String text;
	protected Color color;
	protected Color backgroundColor;
	protected Font font;
	protected boolean updateBounds = true;
	
	public GUIText(GUIComponent parent, int x, int y, String text) {
		this(parent, x, y, text, DEFAULT_COLOR);
	}
	
	public GUIText(GUIComponent parent, int x, int y, String text, Color color) {
		this(parent, x, y, text, color, Main.gameFont);
	}
	
	public GUIText(GUIComponent parent, int x, int y, String text, Color color, Font font) {
		super(parent, x, y);
		
		this.text = text;
		this.color = color;
		this.font = font;
		
	}
	
	public GUIText addBackground(Color color) {
		backgroundColor = color;
		return this;
	}
	public GUIText addBackground() {
		return addBackground(DEFAULT_BACKGROUND_COLOR);
	}
	
	/*
	* Util
	* */
	public void updateBounds() {
		super.updateBounds();
		updateBounds = true;
	}
	
	@Override
	public void render(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		if (updateBounds) {
			g.setFont(font);
			Rectangle2D bounds = font.getStringBounds(text, screenX, screenY, g2.getFontRenderContext());
			setBounds(x, y, (int)bounds.getWidth(), (int)bounds.getHeight());
			
			System.out.println(  bounds.toString());
			
			updateBounds = false;
		}
		
		if (backgroundColor != null) {
			g.setColor(backgroundColor);
			g.drawRect(screenX, screenY, width, height);
		}
		
		g.setColor(color);
		g.drawString(text, screenX, screenY);
	}
}
