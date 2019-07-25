package NewMobCash;

import java.text.DecimalFormat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;

public class Vault {

	public static EconomyResponse r = null;
	private static Economy econ = null;
	private static Chat chat = null;
	  
	public static boolean setupChat()
	{
		RegisteredServiceProvider<Chat> rsp = Bukkit.getServer().getServicesManager().getRegistration(Chat.class);
		chat = (Chat)rsp.getProvider();
	    return chat != null;
	}
	  
	public static void pay(Player p, int amount)
	{
		r = econ.withdrawPlayer(p, amount);
	}
	  
	public static String getGroup(Player p)
	{
		return chat.getPrimaryGroup(p);
	}
	  
	public static double getBalance(Player p)
	{
		return econ.getBalance(p);
	}
	  
	public static boolean setupEconomy()
	{
		if (Bukkit.getServer().getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp == null) {
			return false;
		}
		econ = (Economy)rsp.getProvider();
		return econ != null;
	}
	
	public static void addMoney(Player p, float how)
	{
		econ.depositPlayer(p, how);
		DecimalFormat format = new DecimalFormat("##.##");
		Messages.sendActionText(p, "§7§lHai guadagnato§b§l "+format.format(how)+" §8§l» §f§l$" +format.format(getBalance(p)));
	}
}
