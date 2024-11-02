package gersom.commands;

import org.bukkit.command.CommandSender;

import gersom.CustomMoney;
import gersom.utils.General;
import gersom.utils.Vars;

public class SubCommands {
    private final CustomMoney plugin;

    public SubCommands(CustomMoney plugin) {
        this.plugin = plugin;
    }

    public void handleSubCommands(CommandSender sender, String[] args) {
        if (args[0].equalsIgnoreCase("help") 
            || args[0].equalsIgnoreCase("list")
            || "?".equals(args[0])
        ) {
            showListCommands(sender);
        } else if (args[0].equalsIgnoreCase("reload")) {
            reloadConfig(sender);
        } else if (args[0].equalsIgnoreCase("author")) {
            showAuthor(sender);
        } else if (args[0].equalsIgnoreCase("version")) {
            showVersion(sender);
        } else {
            showNotFoundCommandText(sender);
        }
    }

    public void showNotFoundCommandText(CommandSender sender) {
        sender.sendMessage(General.setColor(
            "&c" + plugin.getConfigs().getPrefix() + plugin.getConfigs().getLangCommandNotFound()
        ));
        showHelpText(sender);
    }

    private void showListCommands(CommandSender sender) {
        sender.sendMessage(General.generateTextFrame(Vars.name));
        sender.sendMessage("");
        sender.sendMessage(General.setColor("_ " + plugin.getConfigs().getLangCommandsList() + ":"));
        sender.sendMessage(General.setColor("  &6/cm reload"));
        sender.sendMessage(General.setColor("  &6/cm author"));
        sender.sendMessage(General.setColor("  &6/cm version"));
        sender.sendMessage("");
        sender.sendMessage(General.generateSeparator());
    }

    private void reloadConfig(CommandSender sender) {
        plugin.getConfigs().reloadConfig();

        sender.sendMessage(General.setColor(
            "&a" + plugin.getConfigs().getPrefix() + "&a" + plugin.getConfigs().getLangCommandReload()
        ));
    }

    private void showAuthor(CommandSender sender) {
        sender.sendMessage("");
        sender.sendMessage(General.setColor(
            "&b" + plugin.getConfigs().getPrefix() + "&r&bAuthor: " + Vars.author
        ));
        sender.sendMessage("");
    }

    private void showVersion(CommandSender sender) {
        sender.sendMessage("");
        sender.sendMessage(General.setColor(
            "&b" + plugin.getConfigs().getPrefix() + "&r&bVersión: " + Vars.version
        ));
        sender.sendMessage("");
    }

    public void showHelpText(CommandSender sender) {
        sender.sendMessage("");
        sender.sendMessage(General.setColor("* " + plugin.getConfigs().getLangCommandHelpText()));
        sender.sendMessage(General.setColor("  &6/tsb help"));
    }

    public void showAboutText(CommandSender sender, String playerName) {
        sender.sendMessage(General.generateTextFrame(Vars.name));
        sender.sendMessage("");
        sender.sendMessage(General.setColor(
            "* " + plugin.getConfigs().getLangWelcome() + " &b&l" + playerName
        ));
        for (String line : plugin.getConfigs().getLangDescription()) {
            sender.sendMessage(General.setColor("&a  " + line));
        }
        showHelpText(sender);
        sender.sendMessage("");
        sender.sendMessage(General.generateSeparator());
    }
}
