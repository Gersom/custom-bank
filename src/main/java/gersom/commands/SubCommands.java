package gersom.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import gersom.CustomBank;
import gersom.utils.General;
import gersom.utils.Vars;
import net.milkbowl.vault.economy.Economy;

public class SubCommands {
    private final CustomBank plugin;
    private final WithdrawCommand withdrawCommand;

    public SubCommands(CustomBank plugin) {
        this.plugin = plugin;
        this.withdrawCommand = new WithdrawCommand(plugin);
    }

    public void handleSubCommands(CommandSender sender, String[] args) {
        if (args[0].equalsIgnoreCase("widthdraw")) {
            withdrawCommand.commandLogic((Player) sender, args); return;
        }
        if (args[0].equalsIgnoreCase("balance")) {
            balanceCommand(sender, args); return;
        }
        if (args[0].equalsIgnoreCase("ranking")) {
            return;
        }
        if (args[0].equalsIgnoreCase("author")) {
            showAuthor(sender);return;
        }
        if (args[0].equalsIgnoreCase("givemoney")) {
            return;
        }
        if (args[0].equalsIgnoreCase("setmoney")) {
            return;
        }
        if (args[0].equalsIgnoreCase("reducemoney")) {
            return;
        }
        if (args[0].equalsIgnoreCase("reload")) {
            reloadConfig(sender); return;
        }
        if (args[0].equalsIgnoreCase("version")) {
            showVersion(sender); return;
        }
        if (args[0].equalsIgnoreCase("help")) {
            showListCommands(sender);
        }
    }

    private void showListCommands(CommandSender sender) {
        sender.sendMessage(General.generateTextFrame(Vars.name));
        sender.sendMessage("");
        sender.sendMessage(General.setColor("_ " + plugin.getConfigs().getLangList() + ":"));
        
        if (sender instanceof Player player) {
            if (player.hasPermission("custombank.use")) {
                sender.sendMessage(General.setColor("  &6/bank widthdraw"));
                sender.sendMessage(General.setColor("  &6/bank balance"));
                sender.sendMessage(General.setColor("  &6/bank ranking"));
                sender.sendMessage(General.setColor("  &6/bank author"));
            }
            if (player.hasPermission("custombank.admin")) {
                sender.sendMessage(General.setColor("  &6/bank givemoney"));
                sender.sendMessage(General.setColor("  &6/bank setmoney"));
                sender.sendMessage(General.setColor("  &6/bank reducemoney"));
                sender.sendMessage(General.setColor("  &6/bank reload"));
                sender.sendMessage(General.setColor("  &6/bank version"));
                sender.sendMessage(General.setColor("  &6/bank help"));
            }
        } else {
            sender.sendMessage(General.setColor("  &6/bank balance"));
            sender.sendMessage(General.setColor("  &6/bank ranking"));
            sender.sendMessage(General.setColor("  &6/bank givemoney"));
            sender.sendMessage(General.setColor("  &6/bank setmoney"));
            sender.sendMessage(General.setColor("  &6/bank reducemoney"));
            sender.sendMessage(General.setColor("  &6/bank reload"));
            sender.sendMessage(General.setColor("  &6/bank version"));
            sender.sendMessage(General.setColor("  &6/bank author"));
            sender.sendMessage(General.setColor("  &6/bank help"));
        }
        
        sender.sendMessage("");
        sender.sendMessage(General.generateSeparator());
    }

    @SuppressWarnings({"", "CallToPrintStackTrace"})
    private void balanceCommand(CommandSender sender, String[] args) {
        String message = plugin.getConfigs().getLangBalance();
        Economy econ = CustomBank.getEconomy();
        double money;

        if (sender instanceof Player player) {
            money = econ.getBalance(player);
        } else {
            if (args.length < 2) {
                sender.sendMessage(General.setColor(
                    "&c" + plugin.getConfigs().getPrefix() + " invalid player name"
                ));
                return;
            }

            Player player = Bukkit.getPlayer(args[1]);
            if (player != null) {
                money = econ.getBalance(player);
            } else {
                sender.sendMessage(General.setColor(
                    "&c" + plugin.getConfigs().getPrefix() + " player not found"
                ));
                return;
            }
        }

        message = plugin.getVars().replaceVars(message, (int) money);
        sender.sendMessage(General.setColor(message));
    }

    private void reloadConfig(CommandSender sender) {
        plugin.getConfigs().reloadConfig();

        sender.sendMessage(General.setColor(
            "&a" + plugin.getConfigs().getPrefix() + " &a" + plugin.getConfigs().getLangReload()
        ));
    }

    private void showAuthor(CommandSender sender) {
        sender.sendMessage("");
        sender.sendMessage(General.setColor(
            "&b" + plugin.getConfigs().getPrefix() + " &r&bAuthor: " + Vars.author
        ));
        sender.sendMessage("");
    }

    private void showVersion(CommandSender sender) {
        sender.sendMessage("");
        sender.sendMessage(General.setColor(
            "&b" + plugin.getConfigs().getPrefix() + " &r&bVersión: " + Vars.version
        ));
        sender.sendMessage("");
    }
}
