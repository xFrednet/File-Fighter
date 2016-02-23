package com.gmail.xfrednet.filefighter.graphics.gui.components;

import com.gmail.xfrednet.filefighter.Main;
import com.gmail.xfrednet.filefighter.graphics.Screen;
import com.gmail.xfrednet.filefighter.graphics.gui.GUIComponent;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * Created by xFrednet on 09.02.2016.
 */
public class GUIText extends GUIComponent {
	
	public static final Color TEXT_COLOR = new Color(0xff000000, true);
	public static final Color DEFAULT_BACKGROUND_COLOR = new Color(0xaacccccc, true);
	public static final int PADDING = 2;
	
	protected String text;
	protected Color textColor;
	protected Color backgroundColor;
	protected Font font;
	protected boolean showBackground;
	protected boolean updateBounds = true;
	
	public GUIText(GUIComponent parent, int x, int y, String text) {
		this(parent, x, y, text, true);
	}
	public GUIText(GUIComponent parent, int x, int y, String text, boolean showBackground) {
		this(parent, x, y, text, showBackground, Main.gameFont);
	}
	public GUIText(GUIComponent parent, int x, int y, String text, boolean showBackground, Font font) {
		super(parent, x, y);
		
		this.text = text;
		this.showBackground = showBackground;
		this.font = font;
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
		g.setFont(font);
		FontMetrics fm = g.getFontMetrics();
		
		if (updateBounds) {
			Rectangle2D bounds = fm.getStringBounds(text, g);
			setBounds(x, y,(int)bounds.getWidth() + 2 * PADDING, (int)bounds.getHeight() + 2 * PADDING);
			
			updateBounds = false;
		}
		
		if (showBackground) {
			g.setColor((backgroundColor == null) ? DEFAULT_BACKGROUND_COLOR : backgroundColor);
			g.fillRect(screenX, screenY, width, height);
		}
		
		g.setColor((textColor == null) ? TEXT_COLOR : textColor);
		g.drawString(text, screenX + PADDING, screenY + PADDING +  fm.getAscent());
	}
	
	/*
	* setter
	* */
	public void setColor(Color textColor, Color backgroundColor) {
		this.textColor = textColor;
		this.backgroundColor = backgroundColor;
	}
	public void setText(String text) {
		this.text = text;
		updateBounds = true;
	}
	
}
