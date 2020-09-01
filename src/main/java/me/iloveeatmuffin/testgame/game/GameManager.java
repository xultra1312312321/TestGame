package me.iloveeatmuffin.testgame.game;

import me.iloveeatmuffin.testgame.TestGame;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;

public class GameManager implements Listener {

    private TestGame plugin = TestGame.getInstance();

    private int lobbyCountdown = 10;
    public int playersNeeded = 2;
    public boolean isStarted = false;

    public Location lobbySpawn;
    public Location gameSpawn;

    public void setupGame() {
        if (plugin.getConfig().contains("gameSpawn")) {
            this.gameSpawn = (Location) plugin.getConfig().get("gameSpawn");
            plugin.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Game Spawn Located");
        }

        if (plugin.getConfig().contains("lobbySpawn")) {
            this.lobbySpawn = (Location) plugin.getConfig().get("lobbySpawn");
            plugin.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Lobby Spawn Located");
        }

        playerCheck(Bukkit.getOnlinePlayers().size());
        for (Player online : Bukkit.getOnlinePlayers()) {
            plugin.playersInGame.add(online.getUniqueId());
            plugin.playermanager.put(online.getUniqueId(),
                    new PlayerManager(online.getUniqueId(), false, 0, false));
            lobbyWait(online);
            online.setFoodLevel(20);
            online.setHealth(20);
            plugin.playerScoreboard.scoreLobby(online);
            online.teleport(lobbySpawn);
        }
    }

    public void lobbyWait(Player player) {
        int online = Bukkit.getOnlinePlayers().size();
        player.sendMessage("§e目前 §f" + online + "§f/" + playersNeeded);
        playerCheck(online);
    }

    public void gameStart() {
        isStarted = true;
        Bukkit.getOnlinePlayers().forEach(player -> {
            player.setWalkSpeed(.5f);
            player.setInvulnerable(false);
            player.getInventory().clear();
            player.setPlayerListName(ChatColor.GREEN + player.getName());
            player.teleport(gameSpawn);
        });

    }

    public void gameStop(Player player) {
        player.setWalkSpeed(.2f);
        player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
        player.getInventory().clear();

        player.setGameMode(GameMode.ADVENTURE);
        isStarted = false;
        plugin.playersInGame.clear();
        plugin.playermanager.clear();

        player.setPlayerListName(ChatColor.GREEN + player.getName());

        if (lobbySpawn != null) {
            player.teleport(lobbySpawn);
        }
    }

    public boolean playerCheck(int online) {
        if (online >= playersNeeded) {
            if (!isStarted) {
                lobbyCountdown();
                setStarted(true);
            }
            return true;
        } else {
            return false;
        }
    }

    public void lobbyCountdown() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (lobbyCountdown > 0) {
                    if (playerCheck(plugin.playersInGame.size())) {
                        lobbyCountdown--;
                        Bukkit.getServer().broadcastMessage("§e遊戲還有 §f" + lobbyCountdown + " 秒開始");
                        for (Player online : Bukkit.getOnlinePlayers()) {
                            online.playSound(online.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2, 2);
                        }
                    } else {
                        Bukkit.getServer().broadcastMessage("§e有玩家離開直到 §f" + playersNeeded + "§e位玩家才會開始!");
                        this.cancel();
                        lobbyCountdown = 10;
                    }
                } else {
                    this.cancel();
                    gameStart();
                    Bukkit.getServer().broadcastMessage("§e遊戲開始");
                }
            }
        }.runTaskTimer(plugin, 0, 20);
    }

    public void setStarted(boolean isStarted) {
        this.isStarted = isStarted;
    }

}