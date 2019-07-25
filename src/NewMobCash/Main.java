package NewMobCash;

import java.util.Random;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.java.JavaPlugin;

import Boost.BoosterUtils;
import Boost.Enums.BoosterType;
import main.SBRank;
import main.SkyblockAddons;

public class Main extends JavaPlugin implements Listener {
	
	private static Main instance;
	
	int value_mobcoin = 270;
	
	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(this, this);
		if (!Vault.setupEconomy()) 
		{
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
		instance = this;
	}
	
	public static Main get() 
    {
        return instance;
    }
	

	public static float getRandomDoubleBetweenRange(float min, float max)
	{
		double x = (Math.random()*((max-min)+1))+min;
		return (float) x;
	}
	
	@EventHandler
	public void onEntityDeath(EntityDeathEvent e) 
	{
		if (e.getEntity() != null && (e.getEntity().getKiller() instanceof Player) && !(e.getEntity() instanceof Player))
		{
			Player killer = e.getEntity().getKiller();
			SBRank rank = SkyblockAddons.getRankFromPlayer(killer.getName());
			if(rank != null)
			{
				float money = getRandomDoubleBetweenRange(8F, 15F);
				if(rank.getID() >= 20)
				{
					money = (float) (money + money*0.50);
				}
				if(e.getEntity() instanceof Monster && e.getEntity().getType() != EntityType.PIG_ZOMBIE)
				{
					reward(killer);
				}
					
				Vault.addMoney(killer, money);
			}
		}
	}
	
	public void reward(Player p)
	{
		Random random = new Random();
		int num = random.nextInt((int) ((value_mobcoin+100) - value_mobcoin*BoosterUtils.getMultiplier(p.getName(), BoosterType.MOB_KEY)));
		
		if(num <= 15)
		{
			getServer().dispatchCommand(getServer().getConsoleSender(), "cc give p mobcoin 1 " + p.getName());
			spawnFirework(p.getLocation(), getRanColor());
		}
	}
	
	public org.bukkit.Color getRanColor()
	{
		Random r = new Random();
		
		switch(r.nextInt(17))
		{
			case 0: return Color.AQUA; 
			case 1: return Color.BLACK; 
			case 2: return Color.BLUE; 
			case 3: return Color.FUCHSIA; 
			case 4: return Color.GRAY; 
			case 5: return Color.GREEN; 
			case 6: return Color.LIME; 
			case 7: return Color.MAROON;
			case 8: return Color.NAVY;
			case 9: return Color.OLIVE;
			case 10: return Color.ORANGE;
			case 11: return Color.PURPLE;
			case 12: return Color.RED;
			case 13: return Color.SILVER;
			case 14: return Color.TEAL;
			case 15: return Color.WHITE;
			case 16: return Color.YELLOW;
			
			default: return Color.GREEN;
		}
	}
	
	public FireworkEffect.Type getFire()
	{
		Random r = new Random();
		
		switch(r.nextInt(5))
		{
			case 0: return FireworkEffect.Type.BALL; 
			case 1: return FireworkEffect.Type.BALL_LARGE;
			case 2: return FireworkEffect.Type.BURST;
			case 3: return FireworkEffect.Type.CREEPER;
			case 4: return FireworkEffect.Type.STAR;
			
			default: return FireworkEffect.Type.STAR;
		}
	}

	public void spawnFirework(Location loc, Color c)
	{
		Firework f = (Firework)loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);
		FireworkMeta fm = f.getFireworkMeta();
		  
		fm.setPower(2);
		fm.addEffect(FireworkEffect.builder().withColor(c).with(getFire()).build());
		
		f.setFireworkMeta(fm);
	}
}
