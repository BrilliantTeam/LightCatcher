package code;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.Levelled;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class LightCatcher extends JavaPlugin
{
    static void SpawnMarkForBlock(Block current)
    {
        current.getWorld().spawnParticle
        (
            Particle.BLOCK_MARKER,
            current.getLocation().add(0.5, 0.5, 0.5),
            1, current.getBlockData()
        );
    }

    public void onEnable()
    {
        Util.manager.registerEvents
        (
            new Listener()
            {
                @EventHandler
                public void ShowLight(PlayerMoveEvent event)
                {
                    Player player = event.getPlayer();

                    if (player.getInventory().getItemInMainHand().getType() == Material.LIGHT &&
                        player.getGameMode() != GameMode.CREATIVE)
                    {
                        Location location = player.getLocation().getBlock().getLocation();

                        for (int x = -3; x < 3; x += 1)
                        {
                            for (int y = -3; y < 3; y += 1)
                            {
                                for (int z = -3; z < 3; z += 1)
                                {
                                    Block current = location.clone().add(x, y, z).getBlock();

                                    if (current.getType() == Material.LIGHT)
                                    {SpawnMarkForBlock(current);}
                                }
                            }
                        }
                    }
                }

                @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
                public void BreakLightForPlayer(PlayerInteractEvent event)
                {
                    Block block = event.getClickedBlock();
                    Action action = event.getAction();
                    Player player = event.getPlayer();

                    if (block != null && block.getType() == Material.LIGHT)
                    {
                        if (action.isLeftClick() && player.getGameMode() != GameMode.CREATIVE)
                        {
                            if (player.breakBlock(block))
                            {
                                block.getWorld().dropItem
                                (block.getLocation().clone().add(0.5, 0.5, 0.5), new ItemStack(Material.LIGHT));
                            }
                        }
                        else if (action.isRightClick())
                        {
                            Levelled data = (Levelled) block.getBlockData().clone();

                            if (player.breakBlock(block))
                            {
                                if (!player.isSneaking())
                                {data.setLevel((data.getLevel() + 1) % 16);}
                                else if (player.isSneaking() && player.getGameMode() != GameMode.CREATIVE)
                                {
                                    data.setLevel((data.getLevel() + 15) % 16);
                                    event.setUseItemInHand(Event.Result.DENY);
                                    event.setCancelled(true);
                                }

                                block.setBlockData(data);
                                SpawnMarkForBlock(block);
                            }
                        }
                    }
                }
            },

            Util.plugin
        );
    }
}