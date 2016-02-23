package com.gmail.xfrednet.filefighter.entity;

import com.gmail.xfrednet.filefighter.Main;
import com.gmail.xfrednet.filefighter.entity.entitytask.Behavior;
import com.gmail.xfrednet.filefighter.graphics.gui.groups.GUILivingEntityEquipment;
import com.gmail.xfrednet.filefighter.item.item.Equipment;
import com.gmail.xfrednet.filefighter.item.item.Weapon;
import com.gmail.xfrednet.filefighter.item.item.Damage;
import com.gmail.xfrednet.filefighter.level.Level;

/**
 * Created by xFrednet on 06.02.2016.
 */
public abstract class LivingEntity extends Entity {
	
	public static final int MAX_ANIMATION_VALUE = 10000;
	public static final int ANIMATION_SPEED =  ((int) (Main.UPS * 0.2) == 0) ? 1 : (int) (Main.UPS * 0.2);
	public static final int STILL_STANDING_SPRITE_INDEX = 0;
	public static final double XP_LEVEL_INCREASE = 1.1;
	
	/*
	* attributes
	* */
	private static final int ATTRIBUTE_COUNT = 6;
	
	public static final int ATTRIBUTE_MAX_HEALTH = 0;
	public static final int ATTRIBUTE_PHYSICAL_DEFENCE = 1;
	public static final int ATTRIBUTE_MENTAL_DEFENCE = 2;
	public static final int ATTRIBUTE_STRENGTH = 3;
	public static final int ATTRIBUTE_INTELLIGENCE = 4;
	public static final int ATTRIBUTE_LUCK = 5;
	
	private static final double ATTRIBUTE_HEALTH_PER_POINT = 10;
	private static final double ATTRIBUTE_PHYSICAL_DEFENCE_PER_POINT = 2;
	private static final double ATTRIBUTE_MENTAL_DEFENCE_PER_POINT = 2;
	private static final double ATTRIBUTE_STRENGTH_PER_POINT = 5;
	private static final double ATTRIBUTE_INTELLIGENCE_PER_POINT = 5;
	private static final double ATTRIBUTE_LUCK_PER_POINT = 1;
	
	protected double[] attributes = new double[ATTRIBUTE_COUNT];
	
	/*
	* Skill points
	* */
	private static final int SKILL_POINT_CATEGORY_COUNT = 5;
	
	public static final int SKILL_POINT_HEALTH = 0;
	public static final int SKILL_POINT_DEFENCE = 1;
	public static final int SKILL_POINT_STRENGTH = 2;
	public static final int SKILL_POINT_INTELLIGENCE = 3;
	public static final int SKILL_POINT_LUCK = 4;
	
	protected int[] skillPoints = new int[SKILL_POINT_CATEGORY_COUNT];
	protected int unspentSkillPoints = 0;
	protected int level = 0;
	protected int xp;
	protected int xpNextLevel = 10;
	
	//current attributes
	protected double health;
	
	/*
	* Equipment
	* */
	public static final int EQUIPMENT_COUNT = 4 + 4 + 5;
	
	public static final int EQUIPMENT_PHYSICAL_ARMOR_HELMET = 0;
	public static final int EQUIPMENT_PHYSICAL_ARMOR_CHESTPLATE = 1;
	public static final int EQUIPMENT_PHYSICAL_ARMOR_PENTS = 2;
	public static final int EQUIPMENT_PHYSICAL_ARMOR_SHOES = 3;
	
	public static final int EQUIPMENT_MENTAL_ARMOR_HELMET = 4;
	public static final int EQUIPMENT_MENTAL_ARMOR_CHESTPLATE = 5;
	public static final int EQUIPMENT_MENTAL_ARMOR_PENTS = 6;
	public static final int EQUIPMENT_MENTAL_ARMOR_SHOES = 7;
	
	public static final int EQUIPMENT_RING_1 = 8;
	public static final int EQUIPMENT_RING_2 = 9;
	public static final int EQUIPMENT_NECKLACE = 10;
	public static final int EQUIPMENT_BRACELET = 11;
	public static final int EQUIPMENT_IMPLANT = 12;
	
	protected Equipment[] equipment = new Equipment[EQUIPMENT_COUNT];
	
	public Weapon weapon;
	public Behavior behavior;
	GUILivingEntityEquipment equipmentGUI = null;
	
	//texture related
	protected int direction = 0;
	protected int animation = 0;
	public boolean isStanding = false;
	
	/*
	* Constructor
	* */
	
	protected LivingEntity(Level level, String name, int xp) {
		this(level, name, xp, null);
	}
	protected LivingEntity(Level level, String name, int xp, Behavior behavior) {
		super(level, name);
		gainXP(xp);
		this.behavior = behavior;
		init();
	}
	
	private void gainXP(int xp) {
		this.xp = xp;
		while (this.xp >= xpNextLevel) {
			levelUp();
		}
		
		if (unspentSkillPoints > 0 && autoSpendSkillPoints()) {
			autoApplySkillPoints();
		}
		
	}
	
	private void levelUp() {
		if (xp >= xpNextLevel) {
			xp -= xpNextLevel;
			xpNextLevel *= XP_LEVEL_INCREASE;
			level++;
			unspentSkillPoints++;
			
			leveledUp();
		}
	}
	
	protected void leveledUp() {}
	
	private void init() {
		updateCurrentSprite();
		updateAttributes();
		setHealth(attributes[ATTRIBUTE_MAX_HEALTH]);
	}
	
	private void autoApplySkillPoints() {
		if (getWeapon() == null) return;
		int[] chances = new int[SKILL_POINT_CATEGORY_COUNT];
		
		//setting chances
		if (getWeapon().getDamageType() == Damage.PHYSICAL_DAMAGE) {
			chances[SKILL_POINT_HEALTH] = 25;
			chances[SKILL_POINT_DEFENCE] = 15;
			chances[SKILL_POINT_STRENGTH] = 40;
			chances[SKILL_POINT_INTELLIGENCE] = 0;
			chances[SKILL_POINT_LUCK] = 20;
		} else {
			chances[SKILL_POINT_HEALTH] = 25;
			chances[SKILL_POINT_DEFENCE] = 15;
			chances[SKILL_POINT_STRENGTH] = 0;
			chances[SKILL_POINT_INTELLIGENCE] = 40;
			chances[SKILL_POINT_LUCK] = 20;
		}
		
		//setting up chances
		for (int i = 1; i < chances.length; i++) { 
			chances[i] += chances[i - 1];
		}
		int i;
		int chance;
		for (int s = 0; s < unspentSkillPoints; s++) {
			chance = random.nextInt(100) + 1;
			
			for (i = 0; i < chances.length; i++) {
				if (chance <= chances[i]) {
					skillPoints[i]++;
					unspentSkillPoints--;
					break;
				}
			}
			
		}
		
		updateAttributes();
		
//		System.out.println("Level: " + level);
//		
//		System.out.println("Live: " + skillPoints[SKILL_POINT_HEALTH]);
//		System.out.println("Defence: " + skillPoints[SKILL_POINT_DEFENCE]);
//		System.out.println("Strength: " + skillPoints[SKILL_POINT_STRENGTH]);
//		System.out.println("Intelligence: " + skillPoints[SKILL_POINT_INTELLIGENCE]);
//		System.out.println("Luck: " + skillPoints[SKILL_POINT_LUCK]);
//		System.out.println();
	}
	
	private boolean autoSpendSkillPoints() {
		return true;
	}
	
	public void setHealth(double health) {
		this.health = health;
		if (this.health > attributes[ATTRIBUTE_MAX_HEALTH]) {
			this.health = attributes[ATTRIBUTE_MAX_HEALTH];
		}
	}
	
	/*
	* Util
	* */
	public void move(double angle, Level level, double speed) {
		if (speed > 1) {
			double xm1 = 1 * Math.sin(angle);
			double ym1 = 1 * Math.cos(angle);
			
			while (speed > 1) {
				move(xm1, ym1, level);
				speed--;
			}
			
		}
		
		move(speed * Math.sin(angle), speed * Math.cos(angle), level);
	}
	private void move(double xm, double ym, Level level) {
		if (ym < 0) direction = 1;
		else if (ym > 0) direction = 0;

		if (!levelCollision(xm, 0, level)) {
			info.x += xm;
		}
		
		if (!levelCollision(0, ym, level)) {
			info.y += ym;
		}
		
	}
	
	protected void updateAnimation() {
		animation++;
		if (animation > MAX_ANIMATION_VALUE) {
			animation = animation % MAX_ANIMATION_VALUE;
		}
		
		updateCurrentSprite();
	}
	
	@Override
	public void update(Level level) {
		if (weapon != null) weapon.update(level);
		if (behavior != null) behavior.update(this, level);
		if (unspentSkillPoints > 0) autoApplySkillPoints(); 
		updateAnimation();
	}
	abstract protected void updateCurrentSprite();
	
	//damage
	public void damage(Entity damageSource, Damage damage) {
		health -= getCalculatedDamage(damage);
		if (health <= 0) {
			died(damageSource);
		}
	}
	protected void died(Entity damageSource) {
		removed = true;
	}
	protected double getCalculatedDamage(Damage damage) {
		double resultingDamage = damage.getDamageAmount();
		
		if (damage.getDamageType() == Damage.PHYSICAL_DAMAGE) {
			resultingDamage -= getAttribute(ATTRIBUTE_PHYSICAL_DEFENCE);
		} else {
			resultingDamage -= getAttribute(ATTRIBUTE_MENTAL_DEFENCE);
		}
		
		if (resultingDamage >= 0) {
			return resultingDamage;
		} else {
			return 0;
		}
	}
	
	//attributes
	public void updateAttributes() {
		//health
		attributes[ATTRIBUTE_MAX_HEALTH] = getBaseAttribute(ATTRIBUTE_MAX_HEALTH) + skillPoints[SKILL_POINT_HEALTH] * ATTRIBUTE_HEALTH_PER_POINT;
		
		//defence
		attributes[ATTRIBUTE_PHYSICAL_DEFENCE] = getBaseAttribute(ATTRIBUTE_PHYSICAL_DEFENCE) + skillPoints[SKILL_POINT_DEFENCE] * ATTRIBUTE_PHYSICAL_DEFENCE_PER_POINT;
		attributes[ATTRIBUTE_MENTAL_DEFENCE] = getBaseAttribute(ATTRIBUTE_MENTAL_DEFENCE) + skillPoints[SKILL_POINT_DEFENCE] * ATTRIBUTE_MENTAL_DEFENCE_PER_POINT;
		
		//strength / intelligence
		attributes[ATTRIBUTE_STRENGTH] = getBaseAttribute(ATTRIBUTE_STRENGTH) + skillPoints[SKILL_POINT_STRENGTH] * ATTRIBUTE_STRENGTH_PER_POINT;
		attributes[ATTRIBUTE_INTELLIGENCE] = getBaseAttribute(ATTRIBUTE_INTELLIGENCE) + skillPoints[SKILL_POINT_INTELLIGENCE] * ATTRIBUTE_INTELLIGENCE_PER_POINT;
		
		//luck
		attributes[ATTRIBUTE_LUCK] = getBaseAttribute(ATTRIBUTE_LUCK) + skillPoints[SKILL_POINT_LUCK] * ATTRIBUTE_LUCK_PER_POINT;
	}
	
	/*
	* getters
	* */
	public Weapon getWeapon() {
		return weapon;
	}
	
	//attributes
	public double getAttribute(int attribute) {
		if (attribute < 0 || attribute >= ATTRIBUTE_COUNT) return 0;
		return attributes[attribute];
	}
	
	public Equipment getEquipment(int equipment) {
		if (equipment < 0 || equipment >= EQUIPMENT_COUNT) return null;
		return this.equipment[equipment];
	}
	
	/*
	* abstract Getters
	* */
	protected abstract double getBaseAttribute(int attribute);
	
	public void showEquipmentGUI(boolean show) {
		if (equipmentGUI == null) {
			equipmentGUI = new GUILivingEntityEquipment(Main.hud, this);
			Main.hud.addComponent(equipmentGUI);
		}
		
		equipmentGUI.setVisible(show);
		
	}
	
	public boolean isEquipmentGUIShown() {
		if (equipmentGUI == null) {
			return false;
		} else {
			return equipmentGUI.getVisibility();
		}
	}
}
