package gersom.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import gersom.CustomBank;
import gersom.utils.General;
import gersom.utils.MoneyUtils;
import gersom.utils.Vars;
import net.milkbowl.vault.economy.Economy;

public class SubCommands {
    private final CustomBank plugin;

    public SubCommands(CustomBank plugin) {
        this.plugin = plugin;
    }

    public void handleSubCommands(CommandSender sender, String[] args) {
        if (args[0].equalsIgnoreCase("withdraw")) {
            WithdrawCommand.commandLogic(plugin, (Player) sender, args); return;
        }
        if (args[0].equalsIgnoreCase("balance")) {
            balanceCommand(sender, args); return;
        }
        if (args[0].equalsIgnoreCase("ranking")) {
            showTopPlayers(sender); return;
        }
        if (args[0].equalsIgnoreCase("author")) {
            showAuthor(sender);return;
        }
        if (args[0].equalsIgnoreCase("givemoney")) {
            GiveMoneyCommand.commandLogic(plugin, sender, args); return;
        }
        if (args[0].equalsIgnoreCase("setmoney")) {
            SetMoneyCommand.commandLogic(plugin, sender, args); return;
        }
        if (args[0].equalsIgnoreCase("reducemoney")) {
            ReduceMoneyCommand.commandLogic(plugin, sender, args); return;
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
                sender.sendMessage(General.setColor("  &6/bank withdraw"));
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

        message = message.replace("{prefix}", plugin.getConfigs().getPrefix());
        message = message.replace("{coin_name}", plugin.getConfigs().getCoinName());
        message = message.replace("{coin_symbol}", plugin.getConfigs().getCoinSymbol());
        message = message.replace("{amount}", MoneyUtils.formatMoney(money) + plugin.getConfigs().getCoinSymbol());
        sender.sendMessage(General.setColor(message));
    }

    private void showTopPlayers(CommandSender sender) {
        Economy econ = CustomBank.getEconomy();
        List<PlayerBalance> topPlayers = new ArrayList<>();
        
        // Recopilar datos de todos los jugadores
        for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
            if (player.hasPlayedBefore()) {
                double balance = econ.getBalance(player);
                topPlayers.add(new PlayerBalance(player.getName(), balance));
            }
        }

        // Ordenar la lista por balance (mayor a menor)
        Collections.sort(topPlayers, Comparator.comparingDouble(PlayerBalance::getBalance).reversed());

        // Mostrar el encabezado
        String messageTop = "Top 10 Richest Players";
        if (plugin.getConfigs().getLanguage().equals("es")) {
            messageTop = "Top 10 Jugadores Más Ricos";
        }
        sender.sendMessage(General.generateSeparator());
        sender.sendMessage(General.setColor("&b  " + messageTop));
        sender.sendMessage("");

        // Mostrar top 10 (o menos si no hay suficientes jugadores)
        int limit = Math.min(10, topPlayers.size());
        for (int i = 0; i < limit; i++) {
            PlayerBalance pb = topPlayers.get(i);
            String playerName = pb.getPlayerName();
            double balance = pb.getBalance();
            
            // Diferentes colores para los primeros lugares
            String color = "&7";
            if (i == 0) color = "&6";
            if (i == 1) color = "&e";
            if (i == 2) color = "&f";
            
            sender.sendMessage(General.setColor(String.format(
                "%s#%d - %s%s: %d%s",
                color, (i + 1),
                color, playerName,
                (int) balance,
                plugin.getConfigs().getCoinSymbol()
            )));
        }

        // Mostrar el ranking del jugador que ejecuta el comando
        if (sender instanceof Player player) {
            int playerRank = -1;
            for (int i = 0; i < topPlayers.size(); i++) {
                if (topPlayers.get(i).getPlayerName().equals(player.getName())) {
                    playerRank = i + 1;
                    break;
                }
            }
            
            if (playerRank > 0) {
                String rankMessage = plugin.getConfigs().getLangRanking();
                rankMessage = rankMessage.replace("{rank}", "#" + playerRank);
                rankMessage = plugin.getVars().replaceVars(rankMessage, (int) topPlayers.get(playerRank - 1).getBalance());
                sender.sendMessage("");
                sender.sendMessage(General.setColor(rankMessage));
            }
        }

        sender.sendMessage("");
        sender.sendMessage(General.generateSeparator());
    }

    // Clase auxiliar para almacenar los datos del jugador
    private static class PlayerBalance {
        private final String playerName;
        private final double balance;

        public PlayerBalance(String playerName, double balance) {
            this.playerName = playerName;
            this.balance = balance;
        }

        public String getPlayerName() {
            return playerName;
        }

        public double getBalance() {
            return balance;
        }
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
            "&b" + plugin.getConfigs().getPrefix() + " &r&bAuthor " + Vars.author
        ));
        sender.sendMessage("");
    }

    private void showVersion(CommandSender sender) {
        sender.sendMessage("");
        sender.sendMessage(General.setColor(
            "&b" + plugin.getConfigs().getPrefix() + " &r&bVersión " + Vars.version
        ));
        sender.sendMessage("");
    }
}
