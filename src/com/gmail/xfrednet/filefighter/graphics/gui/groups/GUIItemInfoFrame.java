package com.gmail.xfrednet.filefighter.graphics.gui.groups;

import com.gmail.xfrednet.filefighter.Main;
import com.gmail.xfrednet.filefighter.graphics.gui.GUIComponent;
import com.gmail.xfrednet.filefighter.graphics.gui.GUIComponentGroup;
import com.gmail.xfrednet.filefighter.graphics.gui.components.GUIBackground;
import com.gmail.xfrednet.filefighter.item.Item;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by xFrednet on 04.03.2016.
 */
public class GUIItemInfoFrame extends GUIComponentGroup {
	
	public static final Color BACKGROUND_COLOR = new Color(0xffacacac, true);
	public static final Color TEXT_COLOR = new Color(0xff606060, true);
	public static final Color SKILL_POINT_CATEGORY_SEPARATOR_COLOR = new Color(0xff757575, true);
	public static final int PADDING = 2 * Main.scale;
	public static final int GUI_WIDTH = 250;
	
	static BufferedImage rIcon;
	
	int currentY = 0;
	Item item;
	
	public GUIItemInfoFrame(GUIComponent parent, int x, int y, Item item) {
		super(parent, x, y, GUI_WIDTH, 0);
		this.item = item;
		addComponent(new GUIBackground(this, 0, 0, MATCH_PARENT, MATCH_PARENT, BACKGROUND_COLOR));
		addItemInfo(new Title(this, 0, currentY, PADDING, item.getName(), true));
		
	}
	
	public void addItemInfo(GUIComponent component) {
		currentY += component.getHeight() - 1;
		setBounds(x, y, width, currentY + PADDING);
		components.get(0).setBounds(0, 0, MATCH_PARENT, MATCH_PARENT);
		addComponent(component);
	}
	
	public int getCurrentY() {
		return currentY;
	}
	
	public Item getItem() {
		return item;
	}
	
	/*
	* Util classes
	* */
	
	protected class Title extends GUIComponent {
		
		final int ICON_WIDTH = 24;
		final int ICON_X = GUI_WIDTH - ICON_WIDTH - PADDING * 2;
		final int ICON_Y = 0;
		
		
		
		String title;
		boolean showRButtonIcon;
		
		public Title(GUIComponent parent, int x, int y, int padding, String title) {
			this(parent, x, y ,padding, title, false);
		}
		public Title(GUIComponent parent, int x, int y, int padding, String title, boolean showRButtonIcon) {
			super(parent, x, y, MATCH_PARENT, SMALL_HEADLINE_FONT.getSize() + padding * 2, padding);
			this.title = title;
			this.showRButtonIcon = showRButtonIcon;
			
			if (showRButtonIcon && rIcon == null) {
				try {
					rIcon = ImageIO.read(GUIItemInfoFrame.class.getResource("/textures/gui/RButtonImage.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		@Override
		public void render(Graphics g) {
			g.setColor(TEXT_COLOR);
			g.setFont(SMALL_HEADLINE_FONT);
			g.drawString(title, screenX, screenY - padding + SMALL_HEADLINE_FONT.getSize());
			
			if (showRButtonIcon) {
				g.drawImage(rIcon, screenX + ICON_X, screenY + ICON_Y, null);
			}
		}
		
	}
	
	public static class GUIItemInfo extends GUIComponent {
		
		public static final int GUI_WIDTH = GUIItemInfoFrame.GUI_WIDTH - PADDING * 2;
		
		boolean showBorder = true;
		
		protected GUIItemInfo(GUIItemInfoFrame parent, int height) {
			super(parent, PADDING, parent.getCurrentY(), GUI_WIDTH, height, 0);
		}
		
		public void showBorder(boolean showBorder) {
			this.showBorder = showBorder;
		}
		
		@Override
		public void render(Graphics g) {
			super.render(g);
			
			if (showBorder) {
				g.setColor(SEPARATOR_COLOR);
				g.drawRect(screenX, screenY, width - 1, height - 1);
			}
		}
	}
	
	public static class GUItemNumberInfo extends GUIItemInfo {
		
		private static final int HEIGHT = FONT.getSize() + PADDING;
		private static final int NUMBER_X = GUI_WIDTH - 20;
		
		String name;
		String number;
		int charX = 0;
		
		public GUItemNumberInfo(GUIItemInfoFrame parent, String name, double number) {
			super(parent, HEIGHT);
			this.name = name;
			this.number = String.valueOf(number);
			this.number = this.number.substring(0, this.number.indexOf(".") + 2);
		}
		
		@Override
		public void render(Graphics g) {
			super.render(g);
			
			g.setColor(TEXT_COLOR);
			g.setFont(FONT);
			g.drawString(name, screenX + PADDING, screenY + FONT.getSize());
			
			if (charX == 0) {
				charX = (int) (screenX + PADDING + NUMBER_X - g.getFontMetrics().getStringBounds(number, g).getWidth());
			}
			
			g.drawString(number, charX, screenY + FONT.getSize());
			
		}
	}
	
	
}
