package com.gmail.xfrednet.filefighter.entity;

import com.gmail.xfrednet.filefighter.Main;
import com.gmail.xfrednet.filefighter.entity.entitytask.Behavior;
import com.gmail.xfrednet.filefighter.graphics.Sprite;
import com.gmail.xfrednet.filefighter.item.item.Equipment;
import com.gmail.xfrednet.filefighter.item.item.Weapon;
import com.gmail.xfrednet.filefighter.item.item.Damage;
import com.gmail.xfrednet.filefighter.item.itemstorage.StorageAccessories;
import com.gmail.xfrednet.filefighter.item.itemstorage.StorageArmor;
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
	public static final int ATTRIBUTE_COUNT = 10;
	
	public static final int ATTRIBUTE_MAX_HEALTH = 0;
	public static final int ATTRIBUTE_MAX_STAMINA = 1;
	public static final int ATTRIBUTE_PHYSICAL_DEFENCE = 2;
	public static final int ATTRIBUTE_MENTAL_DEFENCE = 3;
	public static final int ATTRIBUTE_PHYSICAL_DAMAGE = 4;
	public static final int ATTRIBUTE_MENTAL_DAMAGE = 5;
	public static final int ATTRIBUTE_LUCK = 6;
	public static final int ATTRIBUTE_HEALTH_REGENERATION = 7;
	public static final int ATTRIBUTE_STAMINA_REGENERATION = 8;
	public static final int ATTRIBUTE_SPEED = 9;
	/*
	* Speed !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	* */
	
	private static final double ATTRIBUTE_HEALTH_PER_POINT = 10;
	private static final double ATTRIBUTE_PHYSICAL_DEFENCE_PER_POINT = 2;
	private static final double ATTRIBUTE_MENTAL_DEFENCE_PER_POINT = 2;
	private static final double ATTRIBUTE_STRENGTH_PER_POINT = 3;
	private static final double ATTRIBUTE_INTELLIGENCE_PER_POINT = 3;
	private static final double ATTRIBUTE_LUCK_PER_POINT = 1;
	
	protected double[] attributes = new double[ATTRIBUTE_COUNT];
	
	/*
	* Skill points
	* */
	public static final int SKILL_POINT_CATEGORY_COUNT = 5;
	
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
	protected double stamina;
	//the Entity only generates if the hurtTimer is 0
	protected int hurtTimer = 0;
	
	/*
	* Equipment
	* */
	public static final int EQUIPMENT_COUNT = 4 + 4;
	
	public static final int EQUIPMENT_HELMET = 0;
	public static final int EQUIPMENT_CHESTPLATE = 1;
	public static final int EQUIPMENT_PENTS = 2;
	public static final int EQUIPMENT_SHOES = 3;
	
	public static final int EQUIPMENT_RING_1 = 4;
	public static final int EQUIPMENT_RING_2 = 5;
	public static final int EQUIPMENT_NECKLACE = 6;
	public static final int EQUIPMENT_BRACELET = 7;
	
	protected StorageArmor armor = new StorageArmor();
	protected StorageAccessories accessories = new StorageAccessories();
	protected Equipment[] equipment = new Equipment[EQUIPMENT_COUNT];
	
	public Weapon weapon;
	public Behavior behavior;
	
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
		updateAttributes();
		setHealth(attributes[ATTRIBUTE_MAX_HEALTH]);
	}
	
	//skill point
	private void autoApplySkillPoints() {
		if (!autoSpendSkillPoints()) return;
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
	protected boolean autoSpendSkillPoints() {
		return true;
	}
	
	public void setHealth(double health) {
		this.health = health;
		if (this.health > attributes[ATTRIBUTE_MAX_HEALTH]) {
			this.health = attributes[ATTRIBUTE_MAX_HEALTH];
		}
	}
	
	/*
	* Game loop Util
	* */
	@Override
	public void update(Level level) {
		if (weapon != null) weapon.update(level);
		if (unspentSkillPoints > 0) autoApplySkillPoints();
		updateAnimation();
		regenerate();
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
	}
	private void regenerate() {
		gainStamina(attributes[ATTRIBUTE_STAMINA_REGENERATION]);
		
		if (hurtTimer == 0) {
			if (health < attributes[ATTRIBUTE_MAX_HEALTH]) {
				
				//healing costs 10% of the Stamina regenerated each update
				if (useStamina(attributes[ATTRIBUTE_STAMINA_REGENERATION] / 10)) {
					heal(attributes[ATTRIBUTE_HEALTH_REGENERATION]);
				}
			}
		} else {
			hurtTimer--;
		}
	}
	
	//damage
	public void damage(Entity damageSource, Damage damage) {
		health -= getCalculatedDamage(damage);
		if (health <= 0) {
			died(damageSource);
		}
		hurtTimer = getHurtTime();
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
	
	//regeneration 
	public void heal(double health) {
		if (this.health == attributes[ATTRIBUTE_MAX_HEALTH]) return;
		if ((this.health += health) > attributes[ATTRIBUTE_MAX_HEALTH]) {
			this.health = attributes[ATTRIBUTE_MAX_HEALTH];
		}
	}
	public void gainStamina(double stamina) {
		if (this.stamina == attributes[ATTRIBUTE_MAX_STAMINA]) return;
		if ((this.stamina += stamina) > attributes[ATTRIBUTE_MAX_STAMINA]) {
			this.stamina = attributes[ATTRIBUTE_MAX_STAMINA];
		}
	}
	public boolean useStamina(double stamina) {
		if (!hasEnoughStamina(stamina)) return false;
		this.stamina -= stamina;
		return true;
	}
	public boolean hasEnoughStamina(double stamina) {
		return (this.stamina - stamina > 0);
	}
	
	/*
	* Skill points
	* */
	public void applySkillPoints(int skillPointCategory) {
		if (hasUnspentSkillPoints()) {
			if (skillPointCategory > 0 || skillPointCategory <= SKILL_POINT_CATEGORY_COUNT) {
				
				unspentSkillPoints--;
				skillPoints[skillPointCategory]++;
				
				updateAttributes();
			}
		}
	}
	//getters
	public boolean hasUnspentSkillPoints() {
		return unspentSkillPoints > 0;
	}
	public int getSpentSkillPoint(int skillPointCategory) {
		if (skillPointCategory < 0 || skillPointCategory >= SKILL_POINT_CATEGORY_COUNT) return 0;
		return skillPoints[skillPointCategory];
	}
	public int getUnspentSkillPoints() {
		return unspentSkillPoints;
	}
	public String getSkillPointName(int skillPoint) {
		switch (skillPoint) {
			case SKILL_POINT_HEALTH: return "Health";
			case SKILL_POINT_DEFENCE: return "Defence";
			case SKILL_POINT_STRENGTH: return "Strength";
			case SKILL_POINT_INTELLIGENCE: return "Intelligence";
			case SKILL_POINT_LUCK: return "Luck";
		}
		return "null";
	}
	public String[] getSkillPointInfo(int skillPoint) {
		switch (skillPoint) {
			case SKILL_POINT_HEALTH:
				return new String[] {"Increases Health by " + ATTRIBUTE_HEALTH_PER_POINT};
			case SKILL_POINT_DEFENCE:
				return new String[] {
						"Increases Physical-Defence by " + ATTRIBUTE_PHYSICAL_DEFENCE_PER_POINT,
						"Increases Mental-Defence by " + ATTRIBUTE_MENTAL_DEFENCE_PER_POINT};
			case SKILL_POINT_STRENGTH:
				return new String[] {"Increases Physical-Attack-Damage by " + ATTRIBUTE_STRENGTH_PER_POINT};
			case SKILL_POINT_INTELLIGENCE:
				return new String[] {"Increases Mental-Attack-Damage by " + ATTRIBUTE_INTELLIGENCE_PER_POINT};
			case SKILL_POINT_LUCK:
				return new String[] {"Increases Luck by " + ATTRIBUTE_LUCK_PER_POINT};
		}
		return new String[0];
	}
	
	/*
	* Attributes
	* */
	public void updateAttributes() {
		//health
		attributes[ATTRIBUTE_MAX_HEALTH] = getBaseAttribute(ATTRIBUTE_MAX_HEALTH) 
				+ getEquipmentAttributes(ATTRIBUTE_MAX_HEALTH) 
				+ skillPoints[SKILL_POINT_HEALTH] * ATTRIBUTE_HEALTH_PER_POINT;
		attributes[ATTRIBUTE_MAX_STAMINA] = getBaseAttribute(ATTRIBUTE_MAX_STAMINA)
				+ getEquipmentAttributes(ATTRIBUTE_MAX_STAMINA);
		
		//defence
		attributes[ATTRIBUTE_PHYSICAL_DEFENCE] = getBaseAttribute(ATTRIBUTE_PHYSICAL_DEFENCE) 
				+ getEquipmentAttributes(ATTRIBUTE_PHYSICAL_DEFENCE) 
				+ skillPoints[SKILL_POINT_DEFENCE] * ATTRIBUTE_PHYSICAL_DEFENCE_PER_POINT;
		attributes[ATTRIBUTE_MENTAL_DEFENCE] = getBaseAttribute(ATTRIBUTE_MENTAL_DEFENCE) 
				+ getEquipmentAttributes(ATTRIBUTE_MENTAL_DEFENCE) 
				+ skillPoints[SKILL_POINT_DEFENCE] * ATTRIBUTE_MENTAL_DEFENCE_PER_POINT;
		
		//strength / intelligence
		attributes[ATTRIBUTE_PHYSICAL_DAMAGE] = getBaseAttribute(ATTRIBUTE_PHYSICAL_DAMAGE) 
				+ getEquipmentAttributes(ATTRIBUTE_PHYSICAL_DAMAGE) 
				+ skillPoints[SKILL_POINT_STRENGTH] * ATTRIBUTE_STRENGTH_PER_POINT;
		attributes[ATTRIBUTE_MENTAL_DAMAGE] = getBaseAttribute(ATTRIBUTE_MENTAL_DAMAGE) 
				+ getEquipmentAttributes(ATTRIBUTE_MENTAL_DAMAGE) 
				+ skillPoints[SKILL_POINT_INTELLIGENCE] * ATTRIBUTE_INTELLIGENCE_PER_POINT;
		
		//luck
		attributes[ATTRIBUTE_LUCK] = getBaseAttribute(ATTRIBUTE_LUCK) 
				+ getEquipmentAttributes(ATTRIBUTE_LUCK) 
				+ skillPoints[SKILL_POINT_LUCK] * ATTRIBUTE_LUCK_PER_POINT;
		
		//regeneration
		attributes[ATTRIBUTE_HEALTH_REGENERATION] = getBaseAttribute(ATTRIBUTE_HEALTH_REGENERATION) 
				+ getEquipmentAttributes(ATTRIBUTE_HEALTH_REGENERATION) ;
		attributes[ATTRIBUTE_STAMINA_REGENERATION] = getBaseAttribute(ATTRIBUTE_STAMINA_REGENERATION) 
				+ getEquipmentAttributes(ATTRIBUTE_STAMINA_REGENERATION);
		
		//speed
		attributes[ATTRIBUTE_SPEED] = getBaseAttribute(ATTRIBUTE_SPEED)
				+ getEquipmentAttributes(ATTRIBUTE_SPEED);
	}
	private double getEquipmentAttributes(int attribute) {
		double returnValue = 0;
		for (int i = 0; i < EQUIPMENT_COUNT; i++) {
			if (getEquipment(i) != null) {
				returnValue += getEquipment(i).getAttributeModifier(attribute);
			}
		}
		return returnValue;
	}
	public double getAttribute(int attribute) {
		if (attribute < 0 || attribute >= ATTRIBUTE_COUNT) return 0;
		return attributes[attribute];
	}
	public String getAttributeName(int attribute) {
		switch (attribute) {
			case ATTRIBUTE_MAX_HEALTH:
				return "Max Health";
			case ATTRIBUTE_MAX_STAMINA:
				return "Max Stamina";
			case ATTRIBUTE_PHYSICAL_DEFENCE:
				return "Physical Defence";
			case ATTRIBUTE_MENTAL_DEFENCE:
				return "Mental Defence";
			case ATTRIBUTE_PHYSICAL_DAMAGE:
				return "Physical Damage";
			case ATTRIBUTE_MENTAL_DAMAGE:
				return "Mental Damage";
			case ATTRIBUTE_LUCK:
				return "Luck";
			case ATTRIBUTE_HEALTH_REGENERATION:
				return "Health Regeneration";
			case ATTRIBUTE_STAMINA_REGENERATION:
				return "Stamina Regeneration";
			case ATTRIBUTE_SPEED:
				return "Speed";
			default:
				return "Error";
		}
	}
	public double getHealth() {
		return health;
	}
	public double getStamina() {
		return stamina;
	}
	
	/*
	* getters
	* */
	public Weapon getWeapon() {
		return weapon;
	}
	protected int getHurtTime() {
		return Main.UPS;
	}
	
	//equipment
	public Equipment getEquipment(int equipment) {
		if (equipment < 0 || equipment >= EQUIPMENT_COUNT) return null;
		
		switch (equipment) {
			case EQUIPMENT_HELMET:
				return armor.get(EQUIPMENT_HELMET);
			case EQUIPMENT_CHESTPLATE:
				return armor.get(EQUIPMENT_CHESTPLATE);
			case EQUIPMENT_PENTS:
				return armor.get(EQUIPMENT_PENTS);
			case EQUIPMENT_SHOES:
				return armor.get(EQUIPMENT_SHOES);
			case EQUIPMENT_NECKLACE:
				return accessories.get(EQUIPMENT_NECKLACE);
			case EQUIPMENT_RING_1:
				return accessories.get(EQUIPMENT_RING_1);
			case EQUIPMENT_RING_2:
				return accessories.get(EQUIPMENT_RING_2);
			case EQUIPMENT_BRACELET:
				return accessories.get(EQUIPMENT_BRACELET);
			default:
				return this.equipment[equipment];
		}
	}
	public StorageArmor getArmor() {
		return armor;
	}
	public StorageAccessories getAccessories() {
		return accessories;
	}
	
	/*
	* setters
	* */
	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}
	
	/*
	* abstract Getters
	* */
	abstract protected double getBaseAttribute(int attribute);
	
}
