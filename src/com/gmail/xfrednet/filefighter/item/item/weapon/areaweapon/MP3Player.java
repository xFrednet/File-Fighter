package com.gmail.xfrednet.filefighter.item.item.weapon.areaweapon;

import com.gmail.xfrednet.filefighter.graphics.Sprite;
import com.gmail.xfrednet.filefighter.item.Item;
import com.gmail.xfrednet.filefighter.item.item.Damage;
import com.gmail.xfrednet.filefighter.item.item.weapon.AreaWeapon;

/**
 * Created by xFrednet on 30.03.2016 at 01:52.
 */
public class MP3Player extends AreaWeapon {
	
	@Override
	public String getName() {
		return "MP3 Player";
	}
	
	@Override
	public Sprite getItemSprite() {
		return Sprite.Item.mp3_player;
	}
	
	@Override
	public Item clone() {
		return new MP3Player();
	}
	
	//damage
	@Override
	public int getDamageType() {
		return Damage.MENTAL_DAMAGE;
	}
	@Override
	public double getDamageAmount() {
		return 10;
	}
	
	
	@Override
	public double getRange() {
		return 200;
	}
	
	@Override
	public double getStaminaUsage() {
		return 5;
	}
	
}
