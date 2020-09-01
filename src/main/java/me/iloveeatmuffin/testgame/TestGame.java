package me.iloveeatmuffin.testgame;

import me.iloveeatmuffin.testgame.commands.Commands;
import me.iloveeatmuffin.testgame.game.GameManager;
import me.iloveeatmuffin.testgame.Listener.GameMechanics;
import me.iloveeatmuffin.testgame.game.PlayerManager;
import org.bukkit.Bukkit;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;


public class TestGame extends JavaPlugin {

    private static  TestGame instance;
    public HashMap<UUID, PlayerManager> playermanager = new HashMap<UUID,PlayerManager>();
    public ArrayList<UUID> playersInGame = new ArrayList<>();

    public GameMechanics gameMechanics;
    public GameManager gameManager;
    public Commands commands;
    public PlayerScoreboard playerScoreboard;

    public void onEnable() {
        setInstance(this);
        loadConfig();
        instanceClasses();
        gameManager.setupGame();
        commands.onEnable();

        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "\n\nTest Game has been Enabled\n\n");
        getServer().getPluginManager().registerEvents(new GameMechanics(), this);
    }

    public void onDisable() {
        Bukkit.getOnlinePlayers().forEach(player -> gameManager.gameStop(player));
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "\n\nTest Game has been Disabled\n\n");
    }

    public static TestGame getInstance() {
        return instance;
    }

    private static void setInstance(TestGame instance) {
        TestGame.instance = instance;
    }

    private void loadConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    private void instanceClasses(){
        gameMechanics = new GameMechanics();
        gameManager = new GameManager();
        playerScoreboard = new PlayerScoreboard();
        commands = new Commands();
    }
}