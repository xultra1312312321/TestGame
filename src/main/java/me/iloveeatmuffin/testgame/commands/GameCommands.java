package me.iloveeatmuffin.testgame.commands;

import me.iloveeatmuffin.testgame.TestGame;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GameCommands implements CommandExecutor {
    private TestGame plugin = TestGame.getInstance();
    private Commands commands = plugin.commands;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (command.getName().equalsIgnoreCase(commands.cmd2)) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                Location pLoc = player.getLocation();
                if (!player.isOp()) return true;
                if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("spawn")) {
                        plugin.getConfig().set("gameSpawn", pLoc);
                        plugin.saveConfig();
                        player.sendMessage(ChatColor.GREEN + "已經設定了遊戲重生點");
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "錯誤名稱");
                }
            } else {
                sender.sendMessage(ChatColor.RED + "只有玩家才能用哦!");
            }
        }
        return true;
    }
}

