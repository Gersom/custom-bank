package gersom.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import gersom.CustomBank;
import gersom.utils.General;
import net.milkbowl.vault.economy.Economy;

public class GiveMoneyCommand {
    @SuppressWarnings({"", "CallToPrintStackTrace"})
    public static void commandLogic(CustomBank plugin, CommandSender sender, String[] args) {
        String msgArgs = "Usage: /bank givemoney <player> <amount>";
        String msgNotPlayer = "Player not found!";
        if (plugin.getConfigs().getLanguage().equals("es")) {
            msgArgs = "Uso: /bank givemoney <jugador> <cantidad>";
            msgNotPlayer = "!Jugador no encontrado!";
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
            if (tempAmount <= 0) {
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

        // Depositar el dinero usando Vault
        Economy econ = CustomBank.getEconomy();
        econ.depositPlayer(targetPlayer, amount);

        // Enviar mensajes de confirmación
        String message = plugin.getConfigs().getLangGiveMoney();
        message = plugin.getVars().replaceVars(message, amount);
        message = message.replace("{player}", targetPlayer.getName());
        
        sender.sendMessage(General.setColor(message));
    }
}
