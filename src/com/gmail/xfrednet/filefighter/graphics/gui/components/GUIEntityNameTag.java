package com.gmail.xfrednet.filefighter.graphics.gui.components;

import com.gmail.xfrednet.filefighter.Main;
import com.gmail.xfrednet.filefighter.graphics.gui.GUIComponent;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * Created by xFrednet on 09.02.2016.
 */
public class GUIEntityNameTag extends GUIComponent {
	
	public static final Font NAME_TAG_FONT = new Font("X-Files", Font.PLAIN, 12); //(good)Architects Daughter //(Clear) Commons //(well)Coolsville
	public static final Color NAME_TAG_TEXT_COLOR = new Color(0xff00ff21, true);
	public static final int PADDING = 1;
	
	protected String name;
	protected boolean updateBounds = true;
	
	public GUIEntityNameTag(GUIComponent parent, int x, int y, String name) {
		super(parent, x - PADDING, y - PADDING + NAME_TAG_FONT.getSize());
		
		this.name = name;
	}
	
	/*
	* Util
	* */
	public void updateBounds() {
		super.updateBounds();
	}
	
	public void setMapPosition(int mapX, int mapY) {
		setBounds(mapX * Main.scale, mapY * Main.scale, 0 ,0);
	}
	
	@Override
	public void render(Graphics g) {
		g.setFont(NAME_TAG_FONT);
		FontMetrics fm = g.getFontMetrics();
		Rectangle2D bounds = fm.getStringBounds(name, g);
		
		g.setColor(NAME_TAG_TEXT_COLOR);
		g.drawString(name, (int)(screenX - (bounds.getWidth() / 2)) + PADDING, screenY + PADDING +  fm.getAscent());
		
	}
}
