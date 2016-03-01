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
	
	private static final boolean SHOW_DAMAGE = true;
	
	
	public Dummy(double x, double y, Level level, String name) {
		super(level, name, 0);
		super.setInfo(x, y, 28, 25, 2, 6);
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
	
	@Override
	protected double getBaseAttribute(int attribute) {
		switch (attribute) {
			case ATTRIBUTE_MAX_HEALTH: return 10;
			case ATTRIBUTE_MAX_STAMINA: return 100;
			case ATTRIBUTE_PHYSICAL_DEFENCE: return 1;
			case ATTRIBUTE_MENTAL_DEFENCE: return 0;
			case ATTRIBUTE_STRENGTH: return 0;
			case ATTRIBUTE_INTELLIGENCE: return 0;
			case ATTRIBUTE_LUCK: return 0;
			case ATTRIBUTE_HEALTH_REGENERATION: return 0;
			case ATTRIBUTE_STAMINA_REGENERATION: return 0.2;
			default: return 0;
		}
	}
	
}
