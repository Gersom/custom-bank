package gersom.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import gersom.CustomBank;
import gersom.utils.General;
import net.milkbowl.vault.economy.Economy;

public class DeathMoneyEvents implements Listener {
    private final CustomBank plugin;

    public DeathMoneyEvents(CustomBank plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        // Verificar si la característica está habilitada
        if (!plugin.getConfigs().isLoseMoneyOnDeathEnabled()) {
            return;
        }

        Player player = event.getEntity();
        Economy econ = CustomBank.getEconomy();
        double balance = econ.getBalance(player);
        int costToPay = plugin.getConfigs().getLoseMoneyOnDeathCost();

        // Verificar si el jugador tiene suficiente dinero
        if (balance < costToPay) {
            // Si el jugador no tiene suficiente dinero, tomar todo lo que tenga
            if (balance > 0) {
                econ.withdrawPlayer(player, balance);
                sendDeathMessage(player, (int)balance);
            }
            return;
        }

        // Retirar el dinero
        econ.withdrawPlayer(player, costToPay);
        sendDeathMessage(player, costToPay);
    }

    private void sendDeathMessage(Player player, int amount) {
        // Verificar si los mensajes están habilitados
        if (!plugin.getConfigs().isLoseMoneyOnDeathMessageEnabled()) {
            return;
        }

        String message = plugin.getConfigs().getLoseMoneyOnDeathMessageText();
        message = plugin.getVars().replaceVars(message, amount);
        player.sendMessage(General.setColor(message));
    }
}