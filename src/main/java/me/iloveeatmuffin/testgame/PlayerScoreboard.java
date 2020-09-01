package me.iloveeatmuffin.testgame;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class PlayerScoreboard {

    private static ScoreboardManager m;
    private static Scoreboard b;
    private static Objective o;
    private static Score gameMode;
    private static Score time;
    private static Score coins;
    private static Score alive;
    private static Score dead;


    private static TestGame plugin = TestGame.getPlugin(TestGame.class);

    public void scoreLobby(Player player) {
        m = Bukkit.getScoreboardManager();
        b = m.getNewScoreboard();
        o = b.registerNewObjective("testtag", "","TEST GAME");
        o.setDisplaySlot(DisplaySlot.SIDEBAR);


        time = o.getScore(ChatColor.WHITE + "Time: " + ChatColor.GRAY + "Not Started");
        time.setScore(4);


        gameMode = o.getScore(ChatColor.WHITE + "Game: " + ChatColor.GREEN + "TEST GAME");
        gameMode.setScore(3);

        coins = o.getScore(ChatColor.WHITE + "Coins: " + ChatColor.GREEN + "100");
        coins.setScore(2);

        alive = o.getScore(ChatColor.WHITE + "Players: " + ChatColor.GREEN + plugin.playersInGame.size() + "" + ChatColor.WHITE + "/" + ChatColor.GREEN + plugin.gameManager.playersNeeded);
        alive.setScore(1);

        player.setScoreboard(b);
    }
}
