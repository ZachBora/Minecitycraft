package com.worldcretornica.minecitycraft;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.generator.BlockPopulator;

public class CityChunkPopulator extends BlockPopulator {
	
	public static Minecitycraft plugin;
	
	private static final int CITYCHANGE = 1;
	
	public CityChunkPopulator(Minecitycraft instance)
	{
		plugin = instance;
	}
	
	@Override
	public void populate(World world, Random rand, Chunk chunk) {
		
		int rint = rand.nextInt(3);
		
		//plugin.getServer().broadcastMessage("populating chunk rand int: " + rint);
		
		
		int centerX = (chunk.getX() << 4);
		int centerZ = (chunk.getZ() << 4);
		int centerY = 64;
		
		//plugin.getServer().broadcastMessage("populating chunk at: " + centerX + ", " + centerZ);
		
		int minY = centerY; //loccenter.getBlockY();
		int maxX = centerX + 15;
		int minX = centerX;
		int maxZ = centerZ + 15;
		int minZ = centerZ;
		int maxY = 128;
		
		//Clear the land, 21x21 around the center block
		for(int y = minY; y <= maxY; y++)
		{
			for(int x = minX; x <= maxX; x++)
			{
				for(int z = minZ; z <= maxZ; z++)
				{
					if (y == minY)
					{
						//SpoutBlock block = (SpoutBlock) world.getBlockAt(x, y, z);
						Block block = world.getBlockAt(x, y, z);
						//block.setTypeAsync(Material.GRASS);
						block.setType(Material.GRASS);
					}
					else
					{
						//SpoutBlock block = (SpoutBlock) world.getBlockAt(x, y, z);
						Block block = world.getBlockAt(x, y, z);
						//block.setTypeAsync(Material.AIR);
						block.setType(Material.AIR);
					}
				}
			}
		}
		
		if (rint <= CITYCHANGE)
		{
			//Build a cottage :D
			minY = centerY;
			maxX = centerX + 14;
			minX = centerX + 1;
			maxZ = centerZ + 14;
			minZ = centerZ + 1;
			maxY = centerY + 20 + rand.nextInt(20);
			
			if (rand.nextBoolean())
				maxY += rand.nextInt(15);
			
			int rooftype = rand.nextInt(3);
			int floortype = rand.nextInt(2);
			boolean glasscenter = rand.nextBoolean();
			int walltype = rand.nextInt(3);
			int wallglasstype = rand.nextInt(3);
			
			for(int yy = minY; yy <= maxY; yy++)
			{
				for(int xx = minX; xx <= maxX; xx++)
				{
					for(int zz = minZ; zz <= maxZ; zz++)
					{
						//Floor
						if(yy == minY)
						{
							//SpoutBlock block = (SpoutBlock) world.getBlockAt(xx, yy, zz);
							Block block = world.getBlockAt(xx, yy, zz);
							//block.setTypeAsync(Material.COBBLESTONE);
							if (floortype == 0)
								block.setType(Material.DOUBLE_STEP);
							else
								block.setType(Material.STONE);
						//Roof
						}else if(yy == maxY)
						{
							//SpoutBlock block = (SpoutBlock) world.getBlockAt(xx, yy, zz);
							Block block = world.getBlockAt(xx, yy, zz);
							//block.setTypeAsync(Material.STONE);
							
							if (glasscenter && !(xx == minX || xx == maxX || zz == minZ || zz == maxZ))
							{
								block.setType(Material.GLASS);
							}
							else{
								if (rooftype == 0 || rooftype == 1)
								{
									block.setType(Material.STONE);
								}
								else
								{
									block.setType(Material.DOUBLE_STEP);
								}
								
							}
						//Walls
						}else if(xx == minX || xx == maxX || zz == minZ || zz == maxZ)
						{
							//SpoutBlock block = (SpoutBlock) world.getBlockAt(xx, yy, zz);
							Block block = world.getBlockAt(xx, yy, zz);
							
							if (wallglasstype == 0)
							{
								if ((xx == minX || xx == maxX) && (zz == minZ || zz == maxZ))
								{
									block.setType(getWall(walltype));
								}else{
									block.setType(Material.GLASS);
								}
							}else if(wallglasstype == 1)
							{
								//if ((xx != minX && xx != maxX && (xx >= (maxX - minX)/2 + 1 || xx <= (maxX - minX)/2 - 1))
								 //|| (zz != minZ && zz != maxZ && (zz >= (maxZ - minZ)/2 + 1 || zz <= (maxZ - minZ)/2 - 1)))
								if ((xx != minX && xx != maxX && (xx >= maxX - 4 || xx <= minX + 4))
								 || (zz != minZ && zz != maxZ && (zz >= maxZ - 4 || zz <= minZ + 4)))
								{
									block.setType(Material.GLASS);
								}else{
									block.setType(getWall(walltype));
								}
							}else{	
								if ((xx == minX || xx == maxX) && zz == (maxZ + minZ)/2 
									|| (zz == minZ || zz == maxZ) && xx == (maxX + minX)/2)
								{
									block.setType(Material.GLASS);
								}else{
									block.setType(getWall(walltype));
								}
							}
							
							//block.setTypeAsync(Material.WOOD);
							//block.setType(Material.STONE);
						}
					}
				}
			}
			
			/*
			//SpoutBlock bdoor2 = (SpoutBlock) world.getBlockAt(centerX, minY + 2, minZ);
			Block bdoor2 = world.getBlockAt(centerX, minY + 2, minZ);
			//bdoor2.setTypeIdAndDataAsync(Material.WOODEN_DOOR.getId(), (byte) 8);
			bdoor2.setType(Material.WOODEN_DOOR);
			bdoor2.setData((byte) 8);
			//SpoutBlock bdoor1 = (SpoutBlock) world.getBlockAt(centerX, minY + 1, minZ);
			Block bdoor1 = world.getBlockAt(centerX, minY + 1, minZ);
			//bdoor1.setTypeAsync(Material.WOODEN_DOOR);
			bdoor1.setType(Material.WOODEN_DOOR);
			*/
		}else{
			for(int x = minX; x <= maxX; x++)
			{
				for(int z = minZ; z <= maxZ; z++)
				{
					//SpoutBlock block = (SpoutBlock) world.getBlockAt(x, y, z);
					Block block = world.getBlockAt(x, minY, z);
					//block.setTypeAsync(Material.GRASS);
					block.setType(Material.WOOL);
					block.setData((byte) 15);
				}
			}
		}
	}

	
	public Material getWall(int walltype)
	{
		if(walltype == 0)
		{
			return Material.STONE;
		}else if (walltype == 1)
		{
			return Material.BRICK;
		}else if(walltype == 2)
		{
			return Material.SANDSTONE;
		}else{
			return Material.CLAY_BRICK;
		}
	}
}
