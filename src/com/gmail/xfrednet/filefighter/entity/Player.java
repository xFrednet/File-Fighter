package com.gmail.xfrednet.filefighter.entity;

import com.gmail.xfrednet.filefighter.Main;
import com.gmail.xfrednet.filefighter.graphics.Camera;
import com.gmail.xfrednet.filefighter.graphics.Sprite;
import com.gmail.xfrednet.filefighter.graphics.gui.GUIComponent;
import com.gmail.xfrednet.filefighter.graphics.gui.GUIComponentGroup;
import com.gmail.xfrednet.filefighter.graphics.gui.groups.GUIItemInfoFrame;
import com.gmail.xfrednet.filefighter.graphics.gui.groups.GUILivingEntityEquipment;
import com.gmail.xfrednet.filefighter.graphics.gui.groups.GUILivingEntityStatusBar;
import com.gmail.xfrednet.filefighter.item.Item;
import com.gmail.xfrednet.filefighter.item.ItemStorage;
import com.gmail.xfrednet.filefighter.item.item.equipment.Armor;
import com.gmail.xfrednet.filefighter.item.item.equipment.armor.LeatherBoots;
import com.gmail.xfrednet.filefighter.item.item.equipment.armor.LeatherChestplate;
import com.gmail.xfrednet.filefighter.item.item.equipment.armor.LeatherHelmet;
import com.gmail.xfrednet.filefighter.item.item.equipment.armor.LeatherPents;
import com.gmail.xfrednet.filefighter.item.item.weapon.gun.PaperGun;
import com.gmail.xfrednet.filefighter.item.itemstorage.Backpack;
import com.gmail.xfrednet.filefighter.level.Level;
import com.gmail.xfrednet.filefighter.util.Input;
import com.sun.glass.events.KeyEvent;

import java.awt.*;

/**
 * Created by xFrednet on 07.02.2016.
 */
public class Player extends LivingEntity {
	
	public static final int ANIMATION_SPRITES = 8;
	public static final int ANIMATION_SPEED = ((int) (Main.UPS * 0.1) == 0) ? 1 : (int) (Main.UPS * 0.1);
	//movement
	private static final int MOVEMENT_UP_KEY = KeyEvent.VK_W;
	private static final int MOVEMENT_DOWN_KEY = KeyEvent.VK_S;
	private static final int MOVEMENT_LEFT_KEY = KeyEvent.VK_A;
	private static final int MOVEMENT_RIGHT_KEY = KeyEvent.VK_D;
	private static final int MOVEMENT_SPEED_KEY = KeyEvent.VK_SHIFT;
	private static final int DISCONNECT_CAMERA = KeyEvent.VK_NUMPAD0;
	//GUI
	private static final int TOGGLE_EQUIPMENT_GUI = KeyEvent.VK_C;
	private static final int TOGGLE_BACKPACK_GUI = KeyEvent.VK_E;
	
	public static final int PICKUP_WAIT_TIME = Main.UPS / 4;
	public static final int PICKUP_KEY = KeyEvent.VK_R;
	
	private static final int MOUSE_ATTACK_BUTTON = Input.LEFT_MOUSE_BUTTON;
	
	Input input;
	Camera camera;
	double speed = 1.5;
	double speedBoost = 1.5;
	double runningStaminaUsage = 0.2;
	
	//GUI
	boolean equipmentButtonPressed = false;
	boolean backpackButtonPressed = false;
	PlayerHud playerHud;
	
	//pickup Item
	int pickupTimer = 0;
	Item currentPickupItem = null;
	double itemPickupRange = 30;
	
	//ItemStorage
	Backpack backpack;
	Item inHandItem = null;
	
	/*
	* constructor
	* */
	public Player(int x, int y, Input input, String name, GUIComponentGroup parent) {
		super(null, name, 200);
		setInfo(x, y, 18, 32, 7, 0);
		weapon = new PaperGun();
		team = PLAYER_TEAM;
		
		this.input = input;
		
		
		backpack = new Backpack();
		
		/*
		* Testing
		* */
		//equipment
		equipment[EQUIPMENT_PHYSICAL_ARMOR_HELMET] = new LeatherHelmet();
		equipment[EQUIPMENT_PHYSICAL_ARMOR_CHESTPLATE] = new LeatherChestplate();
		equipment[EQUIPMENT_PHYSICAL_ARMOR_PENTS] = new LeatherPents();
		equipment[EQUIPMENT_PHYSICAL_ARMOR_SHOES] = new LeatherBoots();
		
		parent.addComponent(playerHud = new PlayerHud(parent));
		
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
	
	public void setCamera(Camera camera) {
		this.camera = camera;
	}
	
	@Override
	protected boolean autoSpendSkillPoints() {
		return false;
	}
	
	/*
	* Util
	* */
	
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
		if (input.isKeyDown(TOGGLE_EQUIPMENT_GUI) != equipmentButtonPressed) {
			equipmentButtonPressed = input.isKeyDown(TOGGLE_EQUIPMENT_GUI);
			
			if (equipmentButtonPressed) {
				playerHud.toggleEquipmentGUI();
			}
		}
		
		if (input.isKeyDown(TOGGLE_BACKPACK_GUI) != backpackButtonPressed) {
			backpackButtonPressed = input.isKeyDown(TOGGLE_BACKPACK_GUI);
			
			if (backpackButtonPressed) {
				playerHud.toggleBackpackGUI();
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
		
		if (xm != 0 || ym != 0) {
			
			if (input.isKeyDown(MOVEMENT_SPEED_KEY) && useStamina(runningStaminaUsage)) {
				move(Math.atan2(xm, ym), level, speed * speedBoost);
			} else {
				move(Math.atan2(xm, ym), level, speed);
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
			level.clicked(input.getMouseX(), input.getMouseY(), MOUSE_ATTACK_BUTTON, this);
			getWeapon().attack(level, this, getAngleTo(input.getMouseLevelX(level), input.getMouseLevelY(level)));
		}
	}
	private void itemPickup(Level level) {
		ItemEntity itemEntity = (ItemEntity) getClosestEntity(level.getItemEntities(info.getCenterX(), info.getCenterY(), itemPickupRange));
		Item item;
		if (itemEntity == null || (item = itemEntity.getItem()) == null) {
			playerHud.removeItemInfo();
			currentPickupItem = null;
			return;
		}
		playerHud.showItemInfoFrame(item);
		
		if (currentPickupItem != item) {
			currentPickupItem = item;
			pickupTimer = PICKUP_WAIT_TIME;
		} else {
			if (input.isKeyDown(PICKUP_KEY)) {
				pickupTimer--;
			} else {
				pickupTimer = PICKUP_WAIT_TIME;
			}
		}
		
		if (pickupTimer <= 0 && !backpack.isStorageFull()) {
			backpack.switchItem(item);
			itemEntity.remove();
		}
		
	}
	
	/*
	* Getter
	* */
	public Input getInput() {
		return input;
	}
	
	public Item getInHandItem() {
		return inHandItem;
	}
	
	/*
	* setter
	* */
	public void setInHandItem(Item inHandItem) {
		this.inHandItem = inHandItem;
	}
	
	public void addStorageGUI(ItemStorage storage) {
		playerHud.addExtraStorageGUI(storage);
	}
	
	/*
	* Util class
	* */
	private class PlayerHud extends GUIComponentGroup {
		
		public static final int STATUS_BAR_X = 20;
		public static final int STATUS_BAR_Y = 20;
		
		public static final int ITEM_INFO_X = 0;
		public static final int ITEM_INFO_Y = Main.HEIGHT * Main.scale - 200;
		
		public static final int EQUIPMENT_X = 400;
		public static final int EQUIPMENT_Y = 99;
		
		public static final int BACKPACK_X = 727;
		public static final int BACKPACK_Y = 99;
		
		public static final int EXTRA_STORAGE_X = 727;
		public static final int EXTRA_STORAGE_Y = 355;
		
		GUILivingEntityStatusBar statusBar;
		
		//Equipment
		GUILivingEntityEquipment equipmentGUI = null;
		boolean isEquipmentGUIShown = false;
		
		//item info
		GUIItemInfoFrame itemInfo;
		
		//Backpack
		ItemStorage.GUIItemStorage backpackGUI = null;
		
		//Extra StorageGUI
		ItemStorage.GUIItemStorage extraStorageGUI = null;
		
		/*
		* constructor
		* */
		public PlayerHud(GUIComponent parent) {
			super(parent, 0, 0, MATCH_PARENT, MATCH_PARENT);
			
			addComponent(statusBar = new GUILivingEntityStatusBar(this, STATUS_BAR_X, STATUS_BAR_Y, Player.this));
			
			if (equipmentGUI == null) {
				equipmentGUI = new GUILivingEntityEquipment(this, EQUIPMENT_X, EQUIPMENT_Y + 6, Player.this);
				this.addComponent(equipmentGUI);
				equipmentGUI.setVisible(false);
			}
			
			if (backpackGUI == null) {
				backpackGUI = backpack.getGUI(this, BACKPACK_X, BACKPACK_Y, Player.this);
				addComponent(backpackGUI);
				backpackGUI.setVisible(false);
			}
			
		}
		
		/*
		* Equipment GUI
		* */
		public void toggleEquipmentGUI() {
			isEquipmentGUIShown = !isEquipmentGUIShown;
			
			if (equipmentGUI == null) {
				equipmentGUI = new GUILivingEntityEquipment(this, Player.this);
				this.addComponent(equipmentGUI);
			}
			
			equipmentGUI.setVisible(isEquipmentGUIShown);
			
		}
		
		/*
		* Item Info Frame
		* */
		public void showItemInfoFrame(Item item) {
			if (item == null) {
				removeItemInfo();
				return;
			}
			if (itemInfo != null && itemInfo.getItem() != item) {
				removeItemInfo();
				itemInfo = item.getGUIItemInfoFrame(this, ITEM_INFO_X, ITEM_INFO_Y);
				addComponent(itemInfo);
			}
			if (itemInfo == null) {
				itemInfo = item.getGUIItemInfoFrame(this, ITEM_INFO_X, ITEM_INFO_Y);
				addComponent(itemInfo);
			}
		}
		public void removeItemInfo() {
			if (itemInfo != null) {
				removeComponent(itemInfo);
				itemInfo = null;
			}
		}
		
		/*
		* Backpack
		* */
		public void toggleBackpackGUI() {
			if (backpackGUI == null) {
				backpackGUI = backpack.getGUI(this, BACKPACK_X, BACKPACK_Y, Player.this);
				addComponent(backpackGUI);
			}
			
			if (backpackGUI.getVisibility()) {
				backpackGUI.setVisible(false);
				if (extraStorageGUI != null) {
					extraStorageGUI.setVisible(false);
				}
			} else {
				backpackGUI.setVisible(true);
			}
			
		} 
		
		/*
		* Extra Storage GUI
		* */
		public void addExtraStorageGUI(ItemStorage storage) {
			if (extraStorageGUI != null) {
				removeComponent(extraStorageGUI);
				extraStorageGUI.setVisible(false);
			}
			
			extraStorageGUI = storage.getGUI(this, EXTRA_STORAGE_X, EXTRA_STORAGE_Y, Player.this);
			extraStorageGUI.setVisible(true);
			addComponent(extraStorageGUI);
			
			if (backpackGUI == null) {
				backpackGUI = backpack.getGUI(this, BACKPACK_X, BACKPACK_Y, Player.this);
				addComponent(backpackGUI);
			}
			backpackGUI.setVisible(true);
			
		}
		
		/*
		* Util
		* */
		@Override
		public void render(Graphics g) {
			super.render(g);
			if (inHandItem != null) {
				Sprite s = inHandItem.getItemSprite();
				g.drawImage(s.getImage(), input.getMouseX(), input.getMouseY(), input.getMouseX() + s.WIDTH * Main.scale, input.getMouseY() + s.HEIGHT * Main.scale, s.getImageX(), s.getImageY(), s.getImageMaxX(), s.getImageMaxY(), null);
			}
		}
		
		
	}
	
}
