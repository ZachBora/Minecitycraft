package com.worldcretornica.minecitycraft;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

public class Minecitycraft extends JavaPlugin {

public final Logger logger = Logger.getLogger("Minecraft");
	
	public String pdfdescription;
	private String pdfversion;
		
	// Permissions
    public PermissionHandler permissions;
    boolean permissions3;
    
    public ArrayList<Thread> threadlist;
    
    BlockPopulator bpcity;
	
	@Override
	public void onDisable() {
		/*
		for(Thread t : threadlist)
		{
			t.interrupt();
		}
		*/
		this.getServer().getWorlds().get(0).getPopulators().remove(bpcity);
		
		this.logger.info(pdfdescription + " version " + pdfversion + " disabled");
	}

	@Override
	public void onEnable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		pdfdescription = pdfFile.getName();
		pdfversion = pdfFile.getVersion();
		
		setupPermissions();
		checkConfig();
		
		//threadlist = new ArrayList<Thread>();
		
		bpcity = new CityChunkPopulator(this);
		
		this.getServer().getWorlds().get(0).getPopulators().add(bpcity);
		this.logger.info(pdfdescription + " version " + pdfversion + " is enabled!");
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if ((label.equalsIgnoreCase("minecitycraft") || label.equalsIgnoreCase("mcc")) && args.length == 0)
		{
			sender.sendMessage(ChatColor.BLUE + pdfdescription + " v" + pdfversion);
			sender.sendMessage(ChatColor.RED + "/minecitycraft build or /mcc build " + ChatColor.WHITE + "Command to establish a city at the player's location.");
			return true;
		}else if ((label.equalsIgnoreCase("minecitycraft") || label.equalsIgnoreCase("mcc")) && args.length == 1)
		{
			Player player = (Player) sender;
			
			player.sendMessage("tu peux pas test");
			
			if (args[0].toString().trim().equalsIgnoreCase("build"))
			{
				
				
				int nb = this.getServer().getWorlds().get(0).getPopulators().size();
				
				player.sendMessage("nb populators : " + nb);
				
				return true;
				
				/*
				Thread tCityBuilder = new Thread(new CityBuilder(this, player.getWorld(), player));
				threadlist.add(tCityBuilder);
				tCityBuilder.start();
				return true;
				*/
			}
			
			return true;
			
		}
		return false;
	}
	
	
	
	
	
	
	
	public String Caption(String caption)
	{
		return "";
	}
	
	private void checkConfig() {
		// TODO Auto-generated method stub
		
	}

	private void setupPermissions() {
        if(permissions != null)
            return;
        
        Plugin permTest = this.getServer().getPluginManager().getPlugin("Permissions");
        
        // Check to see if Permissions exists
        if (permTest == null) {
        	logger.info("[" + pdfdescription + "] Permissions not found, using SuperPerms");
        	return;
        }
    	// Check if it's a bridge
    	if (permTest.getDescription().getVersion().startsWith("2.7.7")) {
    		logger.info("[" + pdfdescription + "] Found Permissions Bridge. Using SuperPerms");
    		return;
    	}
    	
    	// We're using Permissions
    	permissions = ((Permissions) permTest).getHandler();
    	// Check for Permissions 3
    	permissions3 = permTest.getDescription().getVersion().startsWith("3");
    	logger.info("[" + pdfdescription + "] Permissions " + permTest.getDescription().getVersion() + " found");
    }
	
	public Boolean checkPermissions(Player player, String node) {
    	// Permissions
        if (this.permissions != null) {
            if (this.permissions.has(player, node))
                return true;
        // SuperPerms
        } else if (player.hasPermission(node)) {
              return true;
        } else if (player.isOp()) {
            return true;
        }
        return false;
    }

}
