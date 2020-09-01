package me.iloveeatmuffin.testgame.Listener;

import me.iloveeatmuffin.testgame.TestGame;
import me.iloveeatmuffin.testgame.game.PlayerManager;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class GameMechanics implements Listener {

    private TestGame plugin = TestGame.getPlugin(TestGame.class);

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.setJoinMessage("");
        if (!plugin.gameManager.isStarted) {
            Player player = event.getPlayer();
            UUID uuid = player.getUniqueId();

            plugin.playermanager.put(uuid, new PlayerManager(uuid, false, 0, false));
            plugin.playersInGame.add(uuid);
            plugin.gameManager.lobbyWait(player);

            Bukkit.getOnlinePlayers().forEach(online -> plugin.playerScoreboard.scoreLobby(online));

            if (plugin.gameManager.lobbySpawn != null) {
                player.teleport(plugin.gameManager.lobbySpawn);
            }
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        event.setQuitMessage("");
        if (plugin.playermanager.containsKey(uuid) && plugin.playersInGame.contains(uuid)) {
            plugin.playermanager.remove(uuid);
            plugin.playersInGame.remove(uuid);
        }
    }


    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        if (plugin.gameManager.isStarted) {
            event.setCancelled(true);
        }
    }


    @EventHandler
    public void foodLevelChange(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void placeBlockEvent(BlockPlaceEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void breakBlockEvent(BlockBreakEvent event) {
        event.setCancelled(true);
    }
}

