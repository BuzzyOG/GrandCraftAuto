package com.grandcraftauto.tasks;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.grandcraftauto.core.Main;
import com.grandcraftauto.game.weapons.Gun;
import com.grandcraftauto.utils.Utils;

public class ShootTask extends BukkitRunnable{
	
	private Main main = Main.getInstance();
	
	private Player player;
	private Gun gun;
	public ShootTask(Player p, Gun g){
		player = p;
		gun = g;
	}
	
	private int roundsShot = 0;
	private int cooldown = 0;
	
	@Override
	public void run(){
		if((gun.getRoundsPerShot() - roundsShot) > 0){
			Utils.shootBullet(player);
			int maxdura = gun.getMaterial().getMaxDurability();
			int durapershot = maxdura / gun.getClipSize();
			player.getItemInHand().setDurability((short) (player.getItemInHand().getDurability() + durapershot));
			roundsShot++;
		}else{
			if(cooldown < gun.getCooldown() && gun.getCooldown() != 0){
				cooldown += gun.getFiringRate();
			}else{
				if(main.shootCooldown.contains(player.getName())){
					main.shootCooldown.remove(player.getName());
				}
				this.cancel();
			}
		}
	}

}
