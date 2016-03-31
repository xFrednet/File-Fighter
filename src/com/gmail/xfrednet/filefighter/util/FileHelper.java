package com.gmail.xfrednet.filefighter.util;

import com.gmail.xfrednet.filefighter.entity.Entity;
import com.gmail.xfrednet.filefighter.entity.livingentitys.Dummy;
import com.gmail.xfrednet.filefighter.entity.livingentitys.TestEntity;
import com.gmail.xfrednet.filefighter.entity.livingentitys.enemy.*;
import com.gmail.xfrednet.filefighter.graphics.Screen;
import com.gmail.xfrednet.filefighter.graphics.Sprite;
import com.gmail.xfrednet.filefighter.graphics.SpriteSheet;
import com.gmail.xfrednet.filefighter.level.Level;
import com.sun.istack.internal.Nullable;
import com.sun.javafx.beans.annotations.NonNull;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Objects;

import static sun.awt.shell.ShellFolder.getShellFolder;

/**
 * Created by xFrednet on 15.03.2016.
 */
public class FileHelper {
	
	public static final int SPRITE_SIZE = 32;
	
	public static final String FOLDER = "FOLDER";
	public static final String UNKNOWN_FILE = "UNKNOWN";
	
	public static final int MIN_ALPHA_VALUE = 122;
	
	private static HashMap<String, Sprite> fileSprites = new HashMap<>();
	
	/*
	* File Entities
	* */
	public static Entity getFileEntity(@NonNull Level level, @Nullable File file, int x, int y) {
		if (file != null) {
			Entity fileEntity = getBlankFileEntity(level, file, x, y);
			fileEntity.setSprite(getFileSprite(file));
			return fileEntity;
		} else {
			System.out.println("[ERROR] FileHelper: file == null");
			return null;
		}
	}
	public static Entity getBlankFileEntity(Level level, File file, int x, int y) {
		String fileName = file.getName();
		
		//entity choice
		switch (getFileType(file)) {
			case "lnk": return getEntityByName(level, x, y, fileName); 
			
			//text files
			case "txt": return new TextFileEntity(x, y, level, getFileName(fileName));
			
			//JGP
			case "jpg": return new JPGFileEntity(x, y, level, level.getPlayer(), getFileName(fileName));
			case "jpeg": return new JPGFileEntity(x, y, level, level.getPlayer(), getFileName(fileName));
			case "jpe": return new JPGFileEntity(x, y, level, level.getPlayer(), getFileName(fileName));
			
			//music
			case "mp3": return new MP3File(level, x, y, getFileName(fileName));
			//Test code
			case "dummy": return new Dummy(250, 250, level, "dummy");
			
			//default
			default: return new FileEntity(x, y, level, getFileName(fileName));
		}
	}
	public static Entity getEntityByName(@NonNull Level level, double x, double y, String fileName) {
		
		fileName = fileName.toLowerCase();
		
		if (fileName.contains("firefox")) {
			return new FirefoxEntity(x, y, level, getFileName(fileName));
		}
		return new FileEntity(x, y, level, getFileName(fileName));
	}
	
	/*
	* FileType
	* */
	public static String getFileType(File file) {
		if (file.isDirectory()){
			return FOLDER;
		}
		return getFileType(file.getName());
	}
	public static String getFileType(String fileName) {
		int i = fileName.lastIndexOf('.');
		
		if (i > 0) {
			return fileName.substring(i + 1).toLowerCase();
		} else {
			return UNKNOWN_FILE;
		}
	}
	public static String getFileName(File file) {
		if (file.isDirectory()){
			return FOLDER;
		}
		return getFileName(file.getName());
	}
	public static String getFileName(String fileName) {
		int pos = fileName.lastIndexOf(".");
		if (pos > 0) {
			return fileName.substring(0, pos);
		} else {
			return "ERROR";
		}
	}
	
	/*
	* Image Loading
	* */
	public static Sprite getFileSprite(File file) {
		return getFileSprite(file, getFileType(file));
	}
	public static Sprite getFileSprite(File file, String fileType) {
		if (hasSeveralImages(fileType)) {
			return loadFileSprite(file);
		}
		if (!fileSprites.containsKey(fileType)) {
			fileSprites.put(fileType, loadFileSprite(file));
		}
		return fileSprites.get(fileType);
	}
	
	private static boolean hasSeveralImages(String key) {
		switch (key) {
			case "lnk": return true;
			case "url": return true;
		}
		return false;
	}
	
	public static Sprite loadFileSprite(File file) {
		BufferedImage image = loadFileImage(file, SPRITE_SIZE, SPRITE_SIZE);
		if (image == null) {
			System.out.println("[ERROR] FileHelper: could not load file image");
			return Sprite.null_sprite;
		}
		
		//pixels
		int[] pixels = new int[SPRITE_SIZE * SPRITE_SIZE];
		pixels = image.getRGB(0, 0, SPRITE_SIZE, SPRITE_SIZE, pixels, 0, SPRITE_SIZE);
		
		//alpha
		WritableRaster alphaRaster = image.getAlphaRaster();
		if (alphaRaster == null) {
			System.out.println("[ERROR] FileHelper: could not load alphaRaster " + file.getAbsoluteFile());
			return null;
		}
		
		int[] alpha = alphaRaster.getSamples(0, 0, alphaRaster.getWidth(), alphaRaster.getHeight(), 0, (int[]) null);
		for (int i = 0; i < alpha.length; i++) {
			if (alpha[i] <= MIN_ALPHA_VALUE) {
				pixels[i] = Screen.INVISIBLE_COLOR;
			}
		}
		
		return new Sprite(pixels, SPRITE_SIZE, SPRITE_SIZE);
	}
	public static BufferedImage loadFileImage(@NonNull File file, int width, int height) {
		try {
			//loading the Image
			Image image = getShellFolder(file).getIcon(true);
			
			//tests if the image has the right size
			if (image.getWidth(null) != width || image.getHeight(null) != height) {
				//testing if the Image is a BufferedImage
				if (image instanceof BufferedImage) {
					return (BufferedImage) image;
				}
			}
			
			//creates BufferedImage and/or resizes it 
			BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			
			//draws the image to the BufferedImage in the right size
			Graphics2D bGr = bufferedImage.createGraphics();
			bGr.drawImage(image, 0, 0, width, height, null);
			bGr.dispose();
			
			//returns the image
			return bufferedImage;
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
