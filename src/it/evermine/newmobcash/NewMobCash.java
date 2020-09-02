package it.evermine.newmobcash;

import java.util.Random;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;
import it.evermine.everlibrary.EverLibraryUtils;
import it.evermine.prorankskyblock.main.SBRank;
import it.evermine.skyblockaddons.main.SkyblockAddons;

public class NewMobCash extends JavaPlugin implements Listener {

	private static NewMobCash instance;
	final int value_mobcoin = 1000;

	public void onEnable() {
		instance = this;
		getServer().getPluginManager().registerEvents(this, this);
	}

	public static NewMobCash getInstance() {
		return instance;
	}

	public static float getRandomDoubleBetweenRange() {
		double x = (Math.random() * ((4 - 3) + 1)) + 3;
		return (float) x;
	}

	@EventHandler
	public void onEntityDeath(EntityDeathEvent e) {
		if (e.getEntity() != null && (e.getEntity().getKiller() instanceof Player)
				&& !(e.getEntity() instanceof Player)) {
			Player killer = e.getEntity().getKiller();
			SBRank rank = SkyblockAddons.getRankFromPlayer(killer.getName());
			if (rank != null) {
				float money = getRandomDoubleBetweenRange();

				if (rank.getID() >= 20) {
					money = (float) (money + money * 0.50);
				}

				if (e.getEntity() instanceof Monster && e.getEntity().getType() != EntityType.PIG_ZOMBIE) {
					reward(killer.getName());
				}

				EverLibraryUtils.giveMoney(killer, money);
			}
		}
	}

	public void reward(String p) {
		Random random = new Random();
		int num = random.nextInt(value_mobcoin + 500);

		if (num <= 1) {
			getServer().dispatchCommand(getServer().getConsoleSender(), "cc give p mobcoin 1 " + p);
		}
	}
}
