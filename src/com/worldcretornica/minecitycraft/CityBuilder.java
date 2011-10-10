package com.worldcretornica.minecitycraft;

import java.util.Calendar;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandException;
import org.bukkit.entity.Player;
import org.getspout.spoutapi.block.SpoutBlock;

public class CityBuilder implements Runnable {

	public static Minecitycraft plugin;
	public World myworld;
	public Player myplayer;
	
	//private volatile Thread blinker;
	//private boolean threadSuspended;

	public CityBuilder(Minecitycraft instance, World world, Player player) {
		
		plugin = instance;
		myworld = world;
		myplayer = player;
		
	}
	
	
	public void run()
	{
		Calendar cStart;
		Calendar cEnd;
		
		try{
			cStart = Calendar.getInstance();
			
			myplayer.sendMessage(ChatColor.BLUE + plugin.pdfdescription + ChatColor.WHITE + " : Deploying camp");
			
			
			SpoutBlock centerblock = (SpoutBlock) myplayer.getLocation().subtract(0, 1, 0).getBlock();
			
			//Location loccenter = centerblock.getLocation();
			int minY = centerblock.getY(); //loccenter.getBlockY();
			int maxX = centerblock.getX() + 101;
			int minX = centerblock.getX() - 101;
			int maxZ = centerblock.getZ() + 101;
			int minZ = centerblock.getZ() - 101;
			int maxY = 128;
			
			//Clear the land, 21x21 around the center block
			myplayer.sendMessage("Clearing land");
			for(int y = minY; y <= maxY; y++)
			{
				for(int x = minX; x <= maxX; x++)
				{
					for(int z = minZ; z <= maxZ; z++)
					{
						if (y == minY)
						{
							SpoutBlock block = (SpoutBlock) myworld.getBlockAt(new Location(myworld, x, y, z));
							block.setTypeAsync(Material.GRASS);
						}
						else
						{
							SpoutBlock block = (SpoutBlock) myworld.getBlockAt(new Location(myworld, x, y, z));
							block.setTypeAsync(Material.AIR);
						}
					}
				}
			}
			
			wait(10);
			
			//Build a cottage :D
			minY = centerblock.getY();
			maxX = centerblock.getX() + 10;
			minX = centerblock.getX() - 10;
			maxZ = centerblock.getZ() + 10;
			minZ = centerblock.getZ() - 10;
			maxY = centerblock.getY() + 7;
			
			myplayer.sendMessage("Building cottage");
			
			for(int yy = minY; yy <= maxY; yy++)
			{
				for(int xx = minX; xx <= maxX; xx++)
				{
					for(int zz = minZ; zz <= maxZ; zz++)
					{
						if(yy == minY)
						{
							SpoutBlock block = (SpoutBlock) myworld.getBlockAt(xx, yy, zz);
							block.setTypeAsync(Material.COBBLESTONE);
						}else if(yy == maxY)
						{
							SpoutBlock block = (SpoutBlock) myworld.getBlockAt(xx, yy, zz);
							block.setTypeAsync(Material.STONE);
						}else if(xx == minX || xx == maxX || zz == minZ || zz == maxZ)
						{
							SpoutBlock block = (SpoutBlock) myworld.getBlockAt(xx, yy, zz);
							block.setTypeAsync(Material.WOOD);
						}
					}
				}
			}
			
			SpoutBlock bdoor1 = (SpoutBlock) myworld.getBlockAt(centerblock.getX(), minY + 1, minZ);
			bdoor1.setTypeAsync(Material.WOODEN_DOOR);
			SpoutBlock bdoor2 = (SpoutBlock) myworld.getBlockAt(centerblock.getX(), minY + 2, minZ);
			bdoor2.setTypeIdAndDataAsync(Material.WOODEN_DOOR.getId(), (byte) 8);
		
			cEnd = Calendar.getInstance();
			
			myplayer.sendMessage("Done : " + (cEnd.getTimeInMillis() - cStart.getTimeInMillis()));
			
		}catch(CommandException f)
		{
			plugin.logger.severe(f.getMessage());
		}catch(Exception e)
		{
			plugin.logger.severe(e.getMessage());
		}
	}
	
	
}
