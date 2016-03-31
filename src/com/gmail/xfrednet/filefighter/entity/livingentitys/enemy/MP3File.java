package com.gmail.xfrednet.filefighter.entity.livingentitys.enemy;

import com.gmail.xfrednet.filefighter.entity.EnemyEntity;
import com.gmail.xfrednet.filefighter.entity.Entity;
import com.gmail.xfrednet.filefighter.entity.ItemEntity;
import com.gmail.xfrednet.filefighter.entity.areaeffects.MusicAreaEffect;
import com.gmail.xfrednet.filefighter.graphics.Screen;
import com.gmail.xfrednet.filefighter.item.item.Damage;
import com.gmail.xfrednet.filefighter.item.item.weapon.gun.MP3Player;
import com.gmail.xfrednet.filefighter.level.Level;

/**
 * Created by xFrednet on 31.03.2016.
 */
public class MP3File extends EnemyEntity {
	
	private static final Damage DAMAGE = new Damage(Damage.MENTAL_DAMAGE, 4);
	private static final int RANGE = 64;
	
	MusicAreaEffect areaEffect;
	
	public MP3File(Level level, double x, double y, String name) {
		super(level, name, 0);
		setInfo(x, y);
		areaEffect = new MusicAreaEffect(level, RANGE, this, DAMAGE);
	}
	
	@Override
	public void update(Level level) {
		if (level.getEnemies(this, RANGE).size() == 0) {
			moveRandom(level);
		}
		
		areaEffect.update(level);
		areaEffect.setPosition(level, this);
	}
	
	@Override
	public void render(Screen screen) {
		areaEffect.render(screen);
		super.render(screen);
	}
	
	@Override
	protected void died(Level level, Entity damageSource) {
		super.died(level, damageSource);
		
		if (random.nextInt(100) < getDropChance(damageSource, 5)) {
			ItemEntity e = new MP3Player().getItemEntity(level);
			e.setPosition((int)info.getCenterX(), (int)info.getCenterY());
			level.spawn(e);
		}
		
	}
}
