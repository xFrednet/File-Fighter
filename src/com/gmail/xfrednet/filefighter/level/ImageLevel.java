package com.gmail.xfrednet.filefighter.level;

import com.gmail.xfrednet.filefighter.entity.Player;
import com.gmail.xfrednet.filefighter.graphics.GUIManager;
import com.gmail.xfrednet.filefighter.graphics.Screen;
import com.gmail.xfrednet.filefighter.level.tile.WallTile;
import com.gmail.xfrednet.filefighter.level.tileentity.FolderTileEntity;
import com.gmail.xfrednet.filefighter.util.Input;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by xFrednet on 19.03.2016.
 */
public class ImageLevel extends Level {
	public ImageLevel(Player player, Screen screen, File image, GUIManager guiManager) {
		super(player, screen, guiManager);
		
		load(image);
		tileEntities.add(new FolderTileEntity(1, 1, null));
	}
	
	private void load(File imageFile) {
		try {
			BufferedImage image = ImageIO.read(imageFile);
			WIDTH = image.getWidth();
			HEIGHT = image.getHeight();
			tiles = new Tile[WIDTH * HEIGHT];
			int[] pixels = image.getRGB(0, 0, WIDTH, HEIGHT, null, 0, WIDTH);
			
			for (int i = 0; i < pixels.length; i++) {
				
				//if statement because switch does not seam to work
				if (pixels[i] == 0xff808080) {
					tiles[i] = new WallTile();
				} else {
					tiles[i] = Tile.List.spaceTile;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
