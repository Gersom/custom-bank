package gersom.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import gersom.CustomBank;
import gersom.utils.Console;
import gersom.utils.General;
import gersom.utils.Vars;

public class MainCommand implements CommandExecutor {
    private final CustomBank plugin;
    private final SubCommands subCommands;

    public MainCommand(CustomBank plugin) {
        this.plugin = plugin;
        this.subCommands = new SubCommands(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        // Si se ejecuta el comando desde la consola
        if (!(sender instanceof Player)) {
            // No existe argumentos
            if (args.length == 0) {
                showAboutText(sender, "Console");
                return true;
            }

            // Si al menos hay un argumento
            if (args.length > 0) {
                String subCommand = args[0].toLowerCase();

                if (subCommand.equals("widthdraw")) {
                    Console.sendMessage(
                        "&c" +plugin.getConfigs().getPrefix() + " " + plugin.getConfigs().getLangPlayerOnly()
                    );
                    return true;
                }

                subCommands.handleSubCommands(sender, args);
                return true;
            }

            return true;
        }

        Player player = (Player) sender;

        // No existe argumentos
        if (args.length == 0) {
            // Mostrar información básica incluso sin permisos
            showAboutText(player, player.getName());
            return true;
        }

        // Si hay argumentos, verificar el comando específico
        if (args.length > 0) {
            String subCommand = args[0].toLowerCase();

            // Comandos que requieren permiso básico
            if (subCommand.equals("widthdraw") || 
                subCommand.equals("balance") || 
                subCommand.equals("ranking") || 
                subCommand.equals("author")) 
            {
                if (player.hasPermission("custommoney.use")) {
                    subCommands.handleSubCommands(player, args);
                } else {
                    player.sendMessage(General.setColor(
                        "&c" + plugin.getConfigs().getPrefix() + plugin.getConfigs().getLangNotPermission()
                    ));
                }
                return true;
            }

            // Comandos que requieren permiso de admin
            if (subCommand.equals("givemoney") || 
                subCommand.equals("setmoney") || 
                subCommand.equals("reducemoney") || 
                subCommand.equals("reload") || 
                subCommand.equals("version") || 
                subCommand.equals("help")) 
            {
                if (player.hasPermission("custommoney.admin")) {
                    subCommands.handleSubCommands(player, args);
                } else {
                    player.sendMessage(General.setColor(
                        "&c" + plugin.getConfigs().getPrefix() + plugin.getConfigs().getLangNotPermission()
                    ));
                }
                return true;
            }

            // Si el comando no existe
            showNotFoundCommandText(sender);
        }

        return true;
    }

    public void showAboutText(CommandSender sender, String playerName) {
        sender.sendMessage(General.generateTextFrame(Vars.name));
        sender.sendMessage("");
        sender.sendMessage(General.setColor(
            "* " + plugin.getConfigs().getLangWelcome() + " &b&l" + playerName
        ));
        for (String line : plugin.getConfigs().getLangDescription()) {
            sender.sendMessage(General.setColor("&7  " + line));
        }
        showHelpText(sender);
        sender.sendMessage("");
        sender.sendMessage(General.generateSeparator());
    }

    public void showNotFoundCommandText(CommandSender sender) {
        sender.sendMessage("");
        sender.sendMessage(General.setColor(
            "&c" + plugin.getConfigs().getPrefix() + plugin.getConfigs().getLangNotFound()
        ));
        showHelpText(sender);
        sender.sendMessage("");
    }

    public void showHelpText(CommandSender sender) {
        sender.sendMessage("");
        sender.sendMessage(General.setColor("* " + plugin.getConfigs().getLangHelpText()));
        sender.sendMessage(General.setColor("  &6/bank help"));
    }
}
