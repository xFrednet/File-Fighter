package com.gmail.xfrednet.filefighter.entity.livingentitys;

import com.gmail.xfrednet.filefighter.entity.Entity;
import com.gmail.xfrednet.filefighter.entity.LivingEntity;
import com.gmail.xfrednet.filefighter.graphics.Sprite;
import com.gmail.xfrednet.filefighter.item.item.Damage;
import com.gmail.xfrednet.filefighter.level.Level;

/**
 * Created by xFrednet on 18.02.2016.
 */
public class Dummy extends LivingEntity {
	
	public static final double MAX_HEALTH = 1000;
	public static final double PHYSICAL_DEFENCE = 3;
	public static final double MENTAL_DEFENCE = 1;
	public static final double STRENGTH = 1;
	public static final double INTELLIGENCE = 1;
	public static final double LUCK = 1;
	private static final boolean SHOW_DAMAGE = true;
	
	
	public Dummy(double x, double y, Level level, String name) {
		super(level, name);
		super.setInfo(x, y, 28, 25, 2, 6);
		setAttributes(MAX_HEALTH, PHYSICAL_DEFENCE, MENTAL_DEFENCE, STRENGTH, INTELLIGENCE, LUCK);
	}
	
	@Override
	protected void updateCurrentSprite() {
		currentSprite = Sprite.dummy_entity_sprite;
	}
	
	@Override
	public void update(Level level) {
		super.update(level);
		nameTag.setName(name);
	}
	
	public void damage(Entity damageSource, Damage damage) {
		if (SHOW_DAMAGE) {
			name = ((damage.getDamageType() == Damage.PHYSICAL_DAMAGE) ? "P" : "M") +
					" " + damage.getDamageAmount();
		} else {
			name = ((damage.getDamageType() == Damage.PHYSICAL_DAMAGE) ? "P" : "M") +
					" " + getCalculatedDamage(damage);
		}
	}
	
}
