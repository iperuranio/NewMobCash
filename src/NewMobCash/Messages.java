package NewMobCash;

import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.PacketPlayOutTitle;

public class Messages {

	public static void sendActionText(Player player, String message)
	{
	    PacketPlayOutTitle title = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.ACTIONBAR, IChatBaseComponent.ChatSerializer.a("{\"text\": \" " + message + "\"}"), 200, 3000, 900);
	    	    
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(title);
	}
}
