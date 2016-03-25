package com.gmail.xfrednet.filefighter.graphics.gui.components;

import com.gmail.xfrednet.filefighter.Main;
import com.gmail.xfrednet.filefighter.graphics.gui.GUIComponent;

import java.awt.*;

/**
 * Created by xFrednet on 28.02.2016.
 */
public class GUIProgressBar extends GUIComponent {
	
	public static final Color DEFAULT_INFO_COLOR = new Color(0xffffffff, true);
	
	double progress = 0;
	double maxProgress = 100;
	double percentage = 0;
	int backgroundPadding = 0;
	Color progressColor;
	Color maxProgressColor;
	Color backgroundColor;
	
	//text
	Font infoFont;
	String info;
	Color infoColor;
	boolean showInfo = false;
	
	boolean showNumbers = false;
	
	/*
	* Constructor
	* */
	public GUIProgressBar(GUIComponent parent, int x, int y, int width, int height) {
		this(parent, x, y, width, height, 0);
	}
	public GUIProgressBar(GUIComponent parent, int x, int y, int width, int height, int padding) {
		super(parent, x, y, width, height, padding);
	}
	
	/*
	* Util
	* */
	public void updatePercentage() {
		if ((percentage = progress / maxProgress) > 1) {
			percentage = 1;
		}
	}
	
	@Override
	public void render(Graphics g) {
		super.render(g);
		
		if (backgroundColor != null) {
			g.setColor(backgroundColor);
			g.fillRect(screenX, screenY, width, height);
		}
		
		//maxProgressBar
		g.setColor(maxProgressColor);
		g.fillRect(screenX + backgroundPadding, screenY + backgroundPadding, width - backgroundPadding * 2, height - backgroundPadding * 2);
		
		//actual progressBar
		if (progress > 0) {
			g.setColor(progressColor);
			g.fillRect(screenX + backgroundPadding, screenY + backgroundPadding, (int) ((double)(width - backgroundPadding * 2) * percentage), height - backgroundPadding * 2);
		}
		
		//info text
		if (showInfo || showNumbers) {
			g.setColor(infoColor);
			g.setFont(infoFont);
		}
		
		if (showInfo) {
			g.drawString(info, screenX + backgroundPadding * 2, screenY + infoFont.getSize());
		}
		if (showNumbers) {
			String s = (int)progress + "/" + (int)maxProgress;
			g.drawString(s, (int) (screenX + width - backgroundPadding - g.getFontMetrics().getStringBounds(s, g).getWidth()), screenY + infoFont.getSize());
		}
		
		
	}
	/*
	* setters
	* */
	//colors & components
	public void setProgressColor(Color progressColor) {
		setProgressColor(progressColor, new Color(progressColor.getRed(), progressColor.getGreen(), progressColor.getBlue(), progressColor.getAlpha() / 4));
	}
	public void setProgressColor(Color progressColor, Color totalProgressColor) {
		this.progressColor = progressColor;
		this.maxProgressColor = totalProgressColor;
	}
	public void setBackground(Color backgroundColor, int padding) {
		this.backgroundColor = backgroundColor;
		backgroundPadding = padding;
		infoFont = new Font(Main.gameFont.getName(), Font.PLAIN, height - backgroundPadding * 2);
	}
	public void setInfo(String info) {
		setInfo(info, DEFAULT_INFO_COLOR);
	}
	public void setInfo(String info, Color infoColor) {
		this.info = info;
		this.infoColor = infoColor;
		
		infoFont = new Font(Main.gameFont.getName(), Font.PLAIN, height - backgroundPadding * 2);
		showInfo = true;
	}
	
	//Values
	public void setProgress(double progress) {
		this.progress = progress;
		updatePercentage();
	}
	public void setMaxProgress(double maxProgress) {
		this.maxProgress = maxProgress;
		updatePercentage();
	}
	public void showInfo(boolean showInfo) {
		this.showInfo = showInfo;
	}
	public void showNumbers(boolean showNumbers) {
		this.showNumbers = showNumbers;
		infoFont = new Font(Main.gameFont.getName(), Font.PLAIN, height - backgroundPadding * 2);
	}
	
	/*
	* getters
	* */
	public double getProgress() {
		return progress;
	}
	
	public double getMaxProgress() {
		return maxProgress;
	}
}
