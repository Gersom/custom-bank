package gersom.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import gersom.CustomBank;
import gersom.utils.General;
import net.milkbowl.vault.economy.Economy;

public class ReduceMoneyCommand {
    @SuppressWarnings({"", "CallToPrintStackTrace"})
    public static void commandLogic(CustomBank plugin, CommandSender sender, String[] args) {
        String msgArgs = "Usage: /bank setmoney <player> <amount>";
        String msgNotPlayer = "Player not found!";
        String msgNotEnoughMoney = "Player does not have enough money! Current balance:";
        if (plugin.getConfigs().getLanguage().equals("es")) {
            msgArgs = "Uso: /bank givemoney <jugador> <cantidad>";
            msgNotPlayer = "!Jugador no encontrado!";
            msgNotEnoughMoney = "!El jugador no tiene suficiente dinero! Saldo actual:";
        }

        // Verificar si se proporcionaron suficientes argumentos
        if (args.length < 3) {
            sender.sendMessage(General.setColor(
                "&c" + plugin.getConfigs().getPrefix() + " " + msgArgs
            ));
            return;
        }

        // Obtener el jugador objetivo
        String targetName = args[1];
        Player targetPlayer = Bukkit.getPlayer(targetName);
        if (targetPlayer == null) {
            sender.sendMessage(General.setColor(
                "&c" + plugin.getConfigs().getPrefix() + " " + msgNotPlayer
            ));
            return;
        }

        // Intentar parsear la cantidad
        int amount;
        try {
            // Reemplazar comas por puntos para manejar ambos formatos
            String numberStr = args[2].replace(',', '.');
            // Convertir a double primero para poder manejar decimales
            double tempAmount = Double.parseDouble(numberStr);
            
            // Verificar que el número sea positivo
            if (tempAmount < 0) {
                String msgEnoughMoney = plugin.getConfigs().getLangWithdrawTooMuchMoney();
                msgEnoughMoney = msgEnoughMoney.replace("{prefix}", plugin.getConfigs().getPrefix());
                sender.sendMessage(General.setColor(msgEnoughMoney));
                return;
            }

            // Redondear al entero más cercano
            amount = (int) Math.round(tempAmount);
        } catch (NumberFormatException e) {
            String msgInvalidNumber = plugin.getConfigs().getLangWithdrawInvalidNumber();
            msgInvalidNumber = msgInvalidNumber.replace("{prefix}", plugin.getConfigs().getPrefix());
            sender.sendMessage(General.setColor(msgInvalidNumber));
            return;
        }

        // Verificar si el jugador tiene suficiente dinero
        Economy econ = CustomBank.getEconomy();
        double currentBalance = econ.getBalance(targetPlayer);

        if (currentBalance < amount) {
            sender.sendMessage(General.setColor(
                "&c" + plugin.getConfigs().getPrefix() + " " + msgNotEnoughMoney + " &e" + 
                (int)currentBalance + plugin.getConfigs().getCoinSymbol()
            ));
            return;
        }

        // Retirar el dinero usando Vault
        econ.withdrawPlayer(targetPlayer, amount);

        // Enviar mensajes de confirmación
        String message = plugin.getConfigs().getLangReduceMoney();
        message = plugin.getVars().replaceVars(message, amount);
        message = message.replace("{player}", targetPlayer.getName());
        
        sender.sendMessage(General.setColor(message));
    }
}
