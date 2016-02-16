package com.gmail.xfrednet.filefighter;

import com.gmail.xfrednet.filefighter.graphics.Sprite;
import com.gmail.xfrednet.filefighter.graphics.SpriteSheet;

/**
 * Created by xFrednet on 16.02.2016.
 */
public class LeitsTestClass {
	
	public final static int TEST_ENTITY_ANIMATED_SPRITE_COUNT = 16;
	public final static int TEST_ENTITY_ANIMATION_SPEED = 16;
	public static Sprite[] testEntity_entity_sprite = Sprite.loadEntityAnimation(0/*xPixel*/, 0/*yPixel*/, SpriteSheet.entities, Sprite.ENTITY_SPRITE_SIZE, TEST_ENTITY_ANIMATED_SPRITE_COUNT);
}
