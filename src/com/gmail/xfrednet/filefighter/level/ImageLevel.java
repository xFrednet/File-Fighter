package com.gmail.xfrednet.filefighter.level;

import com.gmail.xfrednet.filefighter.entity.Player;
import com.gmail.xfrednet.filefighter.graphics.GUIManager;
import com.gmail.xfrednet.filefighter.graphics.Screen;
import com.gmail.xfrednet.filefighter.util.Input;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by xFrednet on 19.03.2016.
 */
public class ImageLevel extends Level {
	public ImageLevel(Player player, Input input, Screen screen, File image, GUIManager guiManager) {
		super(player, screen, guiManager);
		
		load(image);
	}
	
	private void load(File imageFile) {
		try {
			BufferedImage image = ImageIO.read(imageFile);
			WIDTH = image.getWidth();
			HEIGHT = image.getHeight();
			tileIDs = new int[WIDTH * HEIGHT];
			int[] pixels = image.getRGB(0, 0, WIDTH, HEIGHT, null, 0, WIDTH);
			
			for (int i = 0; i < pixels.length; i++) {
				
				//if statement because switch does not seam to work
				if (pixels[i] == 0xff808080) {
					tileIDs[i] = Tile.List.WALL_ID;
				} else {
					tileIDs[i] = Tile.List.SPACE_TILE_ID;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
