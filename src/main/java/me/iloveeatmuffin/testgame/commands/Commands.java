package me.iloveeatmuffin.testgame.commands;

import me.iloveeatmuffin.testgame.TestGame;

public class Commands {
    private TestGame plugin = TestGame.getInstance();

    public String cmd1 = "lobby";
    public String cmd2 = "game";

    public void onEnable() {
        registerCommands();
    }

    private void registerCommands() {
        this.plugin.getCommand(cmd1).setExecutor(new LobbyCommands());
        this.plugin.getCommand(cmd2).setExecutor(new GameCommands());
    }
}
