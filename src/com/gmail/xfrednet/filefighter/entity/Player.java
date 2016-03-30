package com.gmail.xfrednet.filefighter.entity;

import com.gmail.xfrednet.filefighter.Main;
import com.gmail.xfrednet.filefighter.graphics.Camera;
import com.gmail.xfrednet.filefighter.graphics.GUIManager;
import com.gmail.xfrednet.filefighter.graphics.Sprite;
import com.gmail.xfrednet.filefighter.graphics.gui.GUIComponent;
import com.gmail.xfrednet.filefighter.graphics.gui.GUIComponentGroup;
import com.gmail.xfrednet.filefighter.graphics.gui.groups.*;
import com.gmail.xfrednet.filefighter.item.Item;
import com.gmail.xfrednet.filefighter.item.ItemStorage;
import com.gmail.xfrednet.filefighter.item.item.equipment.Armor;
import com.gmail.xfrednet.filefighter.item.item.equipment.accessory.bracelet.GoldBracelet;
import com.gmail.xfrednet.filefighter.item.item.equipment.accessory.necklace.GoldDiamondNecklace;
import com.gmail.xfrednet.filefighter.item.item.equipment.accessory.ring.SilverDiamondRing;
import com.gmail.xfrednet.filefighter.item.item.equipment.accessory.ring.BronzeRing;
import com.gmail.xfrednet.filefighter.item.item.equipment.armor.boots.LeatherBoots;
import com.gmail.xfrednet.filefighter.item.item.equipment.armor.chestplates.LeatherChestplate;
import com.gmail.xfrednet.filefighter.item.item.equipment.armor.helmets.LeatherHelmet;
import com.gmail.xfrednet.filefighter.item.item.equipment.armor.pents.LeatherPents;
import com.gmail.xfrednet.filefighter.item.item.potion.HealthPotion;
import com.gmail.xfrednet.filefighter.item.item.potion.StaminaPotion;
import com.gmail.xfrednet.filefighter.item.item.weapon.areaweapon.MP3Player;
import com.gmail.xfrednet.filefighter.item.item.weapon.gun.FirefoxFlameThrower;
import com.gmail.xfrednet.filefighter.item.item.weapon.gun.PaperGun;
import com.gmail.xfrednet.filefighter.item.itemstorage.Backpack;
import com.gmail.xfrednet.filefighter.item.itemstorage.ToolBar;
import com.gmail.xfrednet.filefighter.level.Level;
import com.gmail.xfrednet.filefighter.util.Input;
import com.sun.glass.events.KeyEvent;

import java.awt.*;

/**
 * Created by xFrednet on 07.02.2016.
 */
public class Player extends LivingEntity {
	
	//animation
	public static final int MAX_ANIMATION_VALUE = 10000;
	public static final int STILL_STANDING_SPRITE_INDEX = 0;
	public static final int ANIMATION_SPRITES = 8;
	public static final int ANIMATION_SPEED = ((int) (Main.UPS * 0.1) == 0) ? 1 : (int) (Main.UPS * 0.1);
	//input Keys
	private static final int MOVEMENT_UP_KEY = KeyEvent.VK_W;
	private static final int MOVEMENT_DOWN_KEY = KeyEvent.VK_S;
	private static final int MOVEMENT_LEFT_KEY = KeyEvent.VK_A;
	private static final int MOVEMENT_RIGHT_KEY = KeyEvent.VK_D;
	private static final int MOVEMENT_SPEED_KEY = KeyEvent.VK_SHIFT;
	private static final int MOVEMENT_SLOW_KEY = KeyEvent.VK_CONTROL;
	private static final int DISCONNECT_CAMERA = KeyEvent.VK_NUMPAD0;
	private static final int USE_HEALTH_POTION = KeyEvent.VK_H;
	private static final int USE_STAMINA_POTION = KeyEvent.VK_F;
	//GUI
	private static final int TOGGLE_EQUIPMENT_GUI = KeyEvent.VK_C;
	private static final int TOGGLE_BACKPACK_GUI = KeyEvent.VK_E;
	
	//puckUp
	public static final int PICKUP_WAIT_TIME = Main.UPS / 4;
	public static final int PICKUP_KEY = KeyEvent.VK_R;
	
	//mouse buttons
	private static final int MOUSE_PRIMARY_ACTION_BUTTON = Input.LEFT_MOUSE_BUTTON;
	private static final int MOUSE_SECONDARY_ACTION_BUTTON = Input.RIGHT_MOUSE_BUTTON;
	
	Input input;
	Camera camera;
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
	
	//"static" cool downs 
	int healthPotionDelayTimer = 0;
	int staminaPotionDelayTimer = 0;
	
	//ItemStorage
	Backpack backpack;
	ToolBar toolbar;
	Item inHandItem = null;

	
	/*
	* constructor
	* */
	public Player(int x, int y, Input input, String name, GUIManager guiManager) {
		super(null, name, 0);
		//setInfo(x, y);
		setInfo(x, y, 18, 32, 7, 0);
		//weapon = new PaperGun();
		//weapon = new FirefoxFlameThrower();
		team = PLAYER_TEAM;
		
		this.input = input;
		
		backpack = new Backpack();
		toolbar = new ToolBar(input);
		
		toolbar.switchItem(this, new FirefoxFlameThrower());
		toolbar.switchItem(this, new PaperGun());
		toolbar.switchItem(this, new MP3Player());
		toolbar.switchItem(this, StaminaPotion.newSmallStaminaPotion(10));
		/*
		* Testing
		* */
		//equipment
		backpack.setItem(this, new LeatherHelmet(), 0);
		backpack.setItem(this, new LeatherChestplate(), 4);
		backpack.setItem(this, new LeatherPents(), 8);
		backpack.setItem(this, new LeatherBoots(), 12);
		backpack.setItem(this, new GoldDiamondNecklace(), 1);
		backpack.setItem(this, new BronzeRing(), 5);
		backpack.setItem(this, SilverDiamondRing.newSpeedRing(), 9);
		backpack.setItem(this, new GoldBracelet(), 13);
		backpack.switchItem(this, HealthPotion.newSmallHealthPotion(5), 2);
		backpack.switchItem(this, HealthPotion.newSmallHealthPotion(11), 6);
		backpack.switchItem(this, HealthPotion.newSmallHealthPotion(10), 7);
		
		//accessories.switchItem(this, SilverDiamondRing.newSpeedRing(), 2);
		
		guiManager.addPlayerGUI(playerHud = new PlayerHud(guiManager));
		updateAttributes();
	}
	
	@Override
	protected boolean autoSpendSkillPoints() {
		return false;
	}
	
	/*
	* Util
	* */
	public Item pickup(Item item) {
		item = toolbar.addIfPresentItem(item);
		item = backpack.addIfPresentItem(item);
		
		if (item == null) return null; 
		item = toolbar.addItem(this, item);
		if (item == null) return null;
		item = backpack.addItem(this, item);
		return item;
	}
	
	/*
	* Update & Update Util
	* */
	@Override
	public void update(Level level) {
		super.update(level);
		updateEntityCoolDownTimers();
		updateItemShortages(level);
		updateInput(level);
		updateMovement(level);
		updateItemPickup(level);
		updateGUI();
		updateAnimation();
		updateCurrentSprite();
		
	}
	
	private void updateEntityCoolDownTimers() {
		if (healthPotionDelayTimer > 0) {
			healthPotionDelayTimer--;
		}
		if (staminaPotionDelayTimer > 0) {
			staminaPotionDelayTimer--;
		}
	}
	private void updateItemShortages(Level level) {
		toolbar.update(level);
		backpack.update(level);
		if (input.isMouseButtonDown(MOUSE_PRIMARY_ACTION_BUTTON)) {
			level.clicked(input.getMouseX(), input.getMouseY(), MOUSE_PRIMARY_ACTION_BUTTON, this);
			toolbar.useItemPrimaryAction(level, this, getAngleTo(input.getMouseLevelX(level), input.getMouseLevelY(level)));
		}
		if (input.isMouseButtonDown(MOUSE_SECONDARY_ACTION_BUTTON)) {
			level.clicked(input.getMouseX(), input.getMouseY(), MOUSE_SECONDARY_ACTION_BUTTON, this);
			toolbar.useItemSecondaryAction(level, this, getAngleTo(input.getMouseLevelX(level), input.getMouseLevelY(level)));
		}
	}
	private void updateInput(Level level) {
		//health potion
		if (input.isKeyDown(USE_HEALTH_POTION)) {
			Item item;
			if ((item = toolbar.hasItemClass(HealthPotion.class)) != null) {
				//executes if a health potion is present in the toolbar
				item.usePrimaryAction(level, this, 0);
			} else if ((item = backpack.hasItemClass(HealthPotion.class)) != null) {
				//executes if a health potion is present in the backpack
				item.usePrimaryAction(level, this, 0);
			}
		}
		//stamina potion 
		if (input.isKeyDown(USE_STAMINA_POTION)) {
			Item item;
			if ((item = toolbar.hasItemClass(StaminaPotion.class)) != null) {
				//executes if a stamina potion is present in the toolbar
				item.usePrimaryAction(level, this, 0);
			} else if ((item = backpack.hasItemClass(StaminaPotion.class)) != null) {
				//executes if a stamina potion is present in the backpack
				item.usePrimaryAction(level, this, 0);
			}
		}
	}
	private void updateMovement(Level level) {
		int xm = 0;
		int ym = 0;
		
		if (input.isKeyDown(MOVEMENT_UP_KEY)) ym -= 1;
		if (input.isKeyDown(MOVEMENT_DOWN_KEY)) ym += 1;
		if (input.isKeyDown(MOVEMENT_LEFT_KEY)) xm -= 1;
		if (input.isKeyDown(MOVEMENT_RIGHT_KEY)) xm += 1;
		
		if (xm != 0 || ym != 0) {
			
			if (input.isKeyDown(MOVEMENT_SPEED_KEY) && useStamina(runningStaminaUsage)) {
				move(Math.atan2(xm, ym), level, getAttribute(ATTRIBUTE_SPEED) * speedBoost);
			} else if (input.isKeyDown(MOVEMENT_SLOW_KEY)) {
				move(Math.atan2(xm, ym), level, 1);
			} else {
				move(Math.atan2(xm, ym), level, getAttribute(ATTRIBUTE_SPEED));
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
	private void updateItemPickup(Level level) {
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
		
		if (pickupTimer <= 0) {
			if (pickup(currentPickupItem) == null) {
				itemEntity.remove();
			}
		}
		
	}
	private void updateGUI() {
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
		
		if (input.isKeyDown(KeyEvent.VK_P)) {
			playerHud.addComponent(new GUILivingEntityAttributes(playerHud, 0, 100, this));
		}
		
	}
	private void updateAnimation() {
		animation++;
		if (animation > MAX_ANIMATION_VALUE) {
			animation = animation % MAX_ANIMATION_VALUE;
		}
	}
	private void updateCurrentSprite() {
		int spriteIndex;
		if (isStanding) {
			spriteIndex = STILL_STANDING_SPRITE_INDEX;
		} else {
			spriteIndex = (direction * ANIMATION_SPRITES) + ((animation / ANIMATION_SPEED) % ANIMATION_SPRITES);
		}
		
		sprite = new Sprite(Sprite.player_entity_sprites[spriteIndex]);
		for (int i = 0; i < EQUIPMENT_COUNT; i++) {
			if (getEquipment(i) != null && getEquipment(i) instanceof Armor) {
				sprite.add(((Armor) getEquipment(i)).getAnimatedSprite(spriteIndex));
			}
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
	public int getHealthPotionDelayTimer() {
		return healthPotionDelayTimer;
	}
	public int getStaminaPotionDelayTimer() {
		return staminaPotionDelayTimer;
	}
	public int getItemCount(Class itemClass) {
		return toolbar.getItemCount(itemClass) + backpack.getItemCount(itemClass);
	}
	
	/*
	* setter
	* */
	public void setCamera(Camera camera) {
		this.camera = camera;
	}
	
	public void setInHandItem(Item inHandItem) {
		this.inHandItem = inHandItem;
	}
	public void addStorageGUI(ItemStorage storage) {
		playerHud.addExtraStorageGUI(storage);
	}
	
	public void setHealthPotionDelayTimer(int healthPotionCoolDownTimer) {
		this.healthPotionDelayTimer = healthPotionCoolDownTimer;
	}
	public void setStaminaPotionDelayTimer(int staminaPotionDelayTimer) {
		this.staminaPotionDelayTimer = staminaPotionDelayTimer;
	}
	
	/*
	* Overridden
	* */
	@Override
	public double getBaseAttribute(int attribute) {
		if (attribute == ATTRIBUTE_SPEED) {
			return super.getBaseAttribute(attribute) + 2;
		}
		return super.getBaseAttribute(attribute);
	}
	
	/*
	* Util class
	* */
	private class PlayerHud extends GUIComponentGroup {
		
		public static final int STATUS_BAR_X = 20;
		public static final int STATUS_BAR_Y = 20;
		
		public static final int ITEM_INFO_X = 0;
		public static final int ITEM_INFO_Y = Main.HEIGHT * Main.scale - 200;
		
		public static final int EQUIPMENT_X = 402;
		public static final int EQUIPMENT_Y = 100;
		
		public static final int BACKPACK_X = 731;
		public static final int BACKPACK_Y = 99;
		
		public static final int EXTRA_STORAGE_X = 731;
		public static final int EXTRA_STORAGE_Y = 355;
		
		public static final int TOOLBAR_X = (Main.WIDTH * Main.scale - ToolBar.GUI_WIDTH) / 2;
		public static final int TOOLBAR_Y = (Main.HEIGHT * Main.scale) - ToolBar.GUI_HEIGHT;
		
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
		
		//Toolbar
		ItemStorage.GUIItemStorage toolbarGUI;
		
		/*
		* constructor
		* */
		public PlayerHud(GUIComponent parent) {
			super(parent, 0, 0, MATCH_PARENT, MATCH_PARENT);
			
			addComponent(statusBar = new GUILivingEntityStatusBar(this, STATUS_BAR_X, STATUS_BAR_Y, Player.this));
			
			equipmentGUI = new GUILivingEntityEquipment(this, EQUIPMENT_X, EQUIPMENT_Y + 6, Player.this);
			this.addComponent(equipmentGUI);
			equipmentGUI.setVisible(false);
			
			backpackGUI = backpack.getGUI(this, BACKPACK_X, BACKPACK_Y, Player.this);
			addComponent(backpackGUI);
			backpackGUI.setVisible(false);
			
			toolbarGUI = toolbar.getGUI(this, TOOLBAR_X, TOOLBAR_Y, Player.this);
			addComponent(toolbarGUI);
			toolbar.setItemFrameLock(true);
			
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
			setBackpackGUIVisibility(!backpackGUI.getVisibility());
		} 
		public void setBackpackGUIVisibility(boolean visibility) {
			if (visibility) {
				backpackGUI.setVisible(true);
				toolbar.setItemFrameLock(false);
			} else {
				backpackGUI.setVisible(false);
				toolbar.setItemFrameLock(true);
				if (extraStorageGUI != null) {
					extraStorageGUI.setVisible(false);
				}
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
			
			setBackpackGUIVisibility(true);
			
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
