package com.gmail.xfrednet.filefighter.graphics.gui.groups;

import com.gmail.xfrednet.filefighter.entity.LivingEntity;
import com.gmail.xfrednet.filefighter.graphics.gui.GUIComponent;
import com.gmail.xfrednet.filefighter.graphics.gui.GUIComponentGroup;
import com.gmail.xfrednet.filefighter.graphics.gui.components.GUIBackground;
import com.gmail.xfrednet.filefighter.graphics.gui.components.GUITitle;

import java.awt.*;

/**
 * Created by xFrednet on 06.03.2016.
 */
public class GUILivingEntityAttributes extends GUIComponentGroup {
	
	public static final int GUI_WIDTH = 300;
	public static final int GUI_HEIGHT = 100;
	
	int contentY = 0;
	LivingEntity entity;
	GUIBackground background;
	
	public GUILivingEntityAttributes(GUIComponent parent, int x, int y, LivingEntity entity) {
		super(parent, x, y, GUI_WIDTH, GUI_HEIGHT);
		this.entity = entity;
		
		//background
		background = new GUIBackground(this);
		super.addComponent(background);
		
		//title
		addComponent(new GUITitle(this, 0, 0, entity.getName()));
		
		//attributes
		for (int i = 0; i < LivingEntity.ATTRIBUTE_COUNT; i++) {
			addComponent(new GUIAttributeInfo(this, 0, contentY, i));
		}
	}
	
	@Override
	public void addComponent(GUIComponent component) {
		contentY += component.getHeight() - 1;
		setBounds(x, y, width, contentY);
		components.get(0).setBounds(0, 0, MATCH_PARENT, MATCH_PARENT);
		
		super.addComponent(component);
	}
	
	protected class GUIAttributeInfo extends GUIComponent {
		
		String attributeName;
		String value;
		int valueX = 0;
		
		public GUIAttributeInfo(GUIComponent parent, int x, int y, int attribute) {
			super(parent, x, y, MATCH_PARENT, FONT.getSize() + PADDING * 2, 0);
			
			attributeName = entity.getAttributeName(attribute);
			
			value = String.valueOf(entity.getAttribute(attribute));
			value = this.value.substring(0, value.indexOf(".") + 2);
		}
		
		@Override
		public void render(Graphics g) {
			super.render(g);
			
			g.setColor(SEPARATOR_COLOR);
			g.drawRect(screenX, screenY, width - 1, height - 1);
			
			g.setColor(TEXT_COLOR);
			g.setFont(FONT);
			g.drawString(attributeName, screenX + PADDING, screenY + PADDING + FONT.getSize());
			
			if (valueX == 0) {
				valueX = (int) (screenX + width - PADDING - g.getFontMetrics().getStringBounds(value, g).getWidth());
			}
			g.drawString(value, valueX, screenY + PADDING + FONT.getSize());
			
		}
	}
	
}
