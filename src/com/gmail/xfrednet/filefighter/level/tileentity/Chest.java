package com.gmail.xfrednet.filefighter.level.tileentity;

import com.gmail.xfrednet.filefighter.entity.Player;
import com.gmail.xfrednet.filefighter.graphics.Sprite;
import com.gmail.xfrednet.filefighter.item.ItemStorage;
import com.gmail.xfrednet.filefighter.level.Level;
import com.gmail.xfrednet.filefighter.level.TileEntity;

/**
 * Created by xFrednet on 06.03.2016.
 */
public class Chest extends TileEntity {
	
	ItemStorage storage;
	
	public Chest(int x, int y) {
		super(x, y);
		storage = new ItemStorage(4, 4, "Chest");
	}
	public Chest(int x, int y, String name) {
		super(x, y);
		storage = new ItemStorage(4, 4, name);
	}
	
	@Override
	protected Sprite getSprite() {
		return Sprite.TileEntities.chest_sprite;
	}
	
	@Override
	public void mouseInteraction(int x, int y, int button, Level level, Player player) {
		if (button != MAIN_INTERACTION_BUTTON) return;
		
		player.addStorageGUI(storage);
		
	}
}
