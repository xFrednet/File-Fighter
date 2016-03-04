package com.gmail.xfrednet.filefighter.entity;

import com.gmail.xfrednet.filefighter.Main;
import com.gmail.xfrednet.filefighter.entity.entitytask.behavior.UserInputBehavior;
import com.gmail.xfrednet.filefighter.graphics.Camera;
import com.gmail.xfrednet.filefighter.graphics.Sprite;
import com.gmail.xfrednet.filefighter.item.item.equipment.Armor;
import com.gmail.xfrednet.filefighter.item.item.equipment.armor.LeatherBoots;
import com.gmail.xfrednet.filefighter.item.item.equipment.armor.LeatherChestplate;
import com.gmail.xfrednet.filefighter.item.item.equipment.armor.LeatherHelmet;
import com.gmail.xfrednet.filefighter.item.item.equipment.armor.LeatherPents;
import com.gmail.xfrednet.filefighter.item.item.weapon.gun.PaperGun;
import com.gmail.xfrednet.filefighter.level.Level;
import com.gmail.xfrednet.filefighter.util.Input;
import com.sun.glass.events.KeyEvent;

/**
 * Created by xFrednet on 07.02.2016.
 */
public class Player extends LivingEntity {
	
	public static final int ANIMATION_SPRITES = 8;
	public static final int ANIMATION_SPEED = ((int) (Main.UPS * 0.1) == 0) ? 1 : (int) (Main.UPS * 0.1);
	private static final int MOVEMENT_UP_KEY = KeyEvent.VK_W;
	private static final int MOVEMENT_DOWN_KEY = KeyEvent.VK_S;
	private static final int MOVEMENT_LEFT_KEY = KeyEvent.VK_A;
	private static final int MOVEMENT_RIGHT_KEY = KeyEvent.VK_D;
	private static final int MOVEMENT_SPEED_KEY = KeyEvent.VK_E;
	private static final int DISCONNECT_CAMERA = KeyEvent.VK_NUMPAD0;
	private static final int SHOW_HIDE_EQUIPMENT_GUI = KeyEvent.VK_C;
	
	private static final int MOUSE_ATTACK_BUTTON = Input.LEFT_MOUSE_BUTTON;
	
	Input input;
	Camera camera;
	double speed = 1.5;
	boolean equipmentButtonPressed = false;
	
	protected double itemPickupRange = 30;
	
	public Player(int x, int y, Input input, Camera camera, String name, Level level) {
		super(level, name, 200);
		setInfo(x, y, 18, 32, 7, 0);
		weapon = new PaperGun();
		team = PLAYER_TEAM;
		
		this.input = input;
		this.camera = camera;
		
		//equipment
		equipment[EQUIPMENT_PHYSICAL_ARMOR_HELMET] = new LeatherHelmet();
		equipment[EQUIPMENT_PHYSICAL_ARMOR_CHESTPLATE] = new LeatherChestplate();
		equipment[EQUIPMENT_PHYSICAL_ARMOR_PENTS] = new LeatherPents();
		equipment[EQUIPMENT_PHYSICAL_ARMOR_SHOES] = new LeatherBoots();
		
	}
	public Player(int x, int y, Input input, String name, Level level) {
		this(x, y, input, null, name, level);
	}
	
	@Override
	protected void updateCurrentSprite() {
		int spriteIndex;
		if (isStanding) {
			spriteIndex = STILL_STANDING_SPRITE_INDEX;
		} else {
			spriteIndex = (direction * ANIMATION_SPRITES) + ((int)(animation / ANIMATION_SPEED) % ANIMATION_SPRITES);
		}
		
		currentSprite = Sprite.player_entity_sprites[spriteIndex];
		for (int i = 0; i < EQUIPMENT_COUNT; i++) {
			if (equipment[i] != null && equipment[i] instanceof Armor) {
				currentSprite.add(((Armor) equipment[i]).getAnimatedSprite(spriteIndex));
			}
		}
		
	}
	
	@Override
	protected double getBaseAttribute(int attribute) {
		switch (attribute) {
			case ATTRIBUTE_MAX_HEALTH: return 100;
			case ATTRIBUTE_MAX_STAMINA: return 100;
			case ATTRIBUTE_PHYSICAL_DEFENCE: return 1;
			case ATTRIBUTE_MENTAL_DEFENCE: return 1;
			case ATTRIBUTE_STRENGTH: return 1;
			case ATTRIBUTE_INTELLIGENCE: return 1;
			case ATTRIBUTE_LUCK: return 1;
			case ATTRIBUTE_HEALTH_REGENERATION: return 0.01;
			case ATTRIBUTE_STAMINA_REGENERATION: return 0.2;
			default: return 0;
		}
	}
	
	public Camera getCamera() {
		return camera;
	}
	
	public void setCamera(Camera camera) {
		this.camera = camera;
	}
	
	protected boolean autoSpendSkillPoints() {
		return false;
	}
	
	/*
	* Update & Update Util
	* */
	public void update(Level level) {
		super.update(level);
		movement(level);
		attack(level);
		showGUI();
		
		if (input.isKeyDown(KeyEvent.VK_F)) {
			level.spawnParticles(getInfo().getCenterX(), getInfo().getCenterY(), 10, Sprite.smoke_particles);
		}
		
		itemPickup(level);
		
	}
	
	private void showGUI() {
		if (input.isKeyDown(SHOW_HIDE_EQUIPMENT_GUI) != equipmentButtonPressed) {
			equipmentButtonPressed = input.isKeyDown(SHOW_HIDE_EQUIPMENT_GUI);
			
			if (equipmentButtonPressed) {
				showEquipmentGUI(!isEquipmentGUIShown());
			}
		}
	}
	
	private void movement(Level level) {
		int xm = 0;
		int ym = 0;
		
		if (input.isKeyDown(MOVEMENT_UP_KEY)) ym -= 1;
		if (input.isKeyDown(MOVEMENT_DOWN_KEY)) ym += 1;
		if (input.isKeyDown(MOVEMENT_LEFT_KEY)) xm -= 1;
		if (input.isKeyDown(MOVEMENT_RIGHT_KEY)) xm += 1;
		
		//System.out.println("[INFO] UserInputBehavior: xm: " + xm + ", ym " + ym);
		
		if (xm != 0 || ym != 0) {
			
			if (!input.isKeyDown(MOVEMENT_SPEED_KEY)) {
				move(Math.atan2(xm, ym), level, speed);
			} else {
				move(Math.atan2(xm, ym), level, speed * 2);
				
			}
			
			if (camera != null)
				if (!input.isKeyDown(DISCONNECT_CAMERA)) {
					camera.centerXOn((int) getInfo().getCenterX());
					camera.centerYOn((int) getInfo().getCenterY());
				}
			
			isStanding = false;
		} else {
			isStanding = true;
		}
	}
	private void attack(Level level) {
		if (input.isMouseButtonDown(MOUSE_ATTACK_BUTTON)) {
			getWeapon().attack(level, this, getAngleTo(input.getMouseLevelX(level), input.getMouseLevelY(level)));
		}
	}
	private void itemPickup(Level level) {
		ItemEntity item = (ItemEntity) getClosestEntity(level.getItemEntities(info.getCenterX(), info.getCenterY(), itemPickupRange));
		if (item == null) return;
		
		System.out.println("Item " + item.getName());
		
	}	
}
