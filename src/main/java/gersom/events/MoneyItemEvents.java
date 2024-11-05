package gersom.events;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import gersom.CustomBank;
import gersom.utils.General;
import net.milkbowl.vault.economy.Economy;

public class MoneyItemEvents implements Listener {
    private final CustomBank plugin;

    public MoneyItemEvents(CustomBank plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    @SuppressWarnings({"", "CallToPrintStackTrace", "UnnecessaryReturnStatement"})
    public void onPlayerInteract(PlayerInteractEvent event) {
        // para comprobar si es shift + clic derecho
        // if ( !event.getPlayer().isSneaking() ||
        //     (event.getAction() != Action.RIGHT_CLICK_AIR && 
        //      event.getAction() != Action.RIGHT_CLICK_BLOCK)) {
        //     return;
        // }
        // Verificar si es shift + clic derecho
        if (event.getAction() != Action.RIGHT_CLICK_AIR && 
            event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        // Verificar si el item es válido y es una cabeza de jugador
        if (item == null || item.getType() != Material.PLAYER_HEAD) {
            return;
        }

        // Verificar si el item tiene lore
        ItemMeta meta = item.getItemMeta();
        if (meta == null || !meta.hasLore()) {
            return;
        }

        // Obtener el lore y el símbolo de la moneda
        List<String> lore = meta.getLore();
        String coinSymbol = plugin.getConfigs().getCoinSymbol();

        // Buscar en el lore la línea que contiene el símbolo de la moneda
        String amountStr = null;
        for (String line : lore) {
            if (line.contains(coinSymbol)) {
                // Encuentra la posición del símbolo
                int symbolIndex = line.indexOf(coinSymbol);
                
                // Obtén la subcadena antes del símbolo y elimina caracteres no numéricos
                amountStr = "";
                int i = symbolIndex - 1;
                
                // Retrocede hasta encontrar un caracter que no sea número
                while (i >= 0 && (Character.isDigit(line.charAt(i)) || line.charAt(i) == '.')) {
                    amountStr = line.charAt(i) + amountStr;
                    i--;
                }
                break;
            }
        }

        // Si no se encontró el símbolo o el monto en el lore
        if (amountStr == null || amountStr.isEmpty()) {
            return;
        }

        try {
            // Convierte el string a número
            int amount = Integer.parseInt(amountStr);
            
            // Verificar que la cantidad sea válida
            if (amount <= 0) {
                return;
            }
            
            // Depositar el dinero
            Economy econ = CustomBank.getEconomy();
            econ.depositPlayer(player, amount);

            // Consumir el item (reducir en 1 la cantidad)
            if (item.getAmount() > 1) {
                item.setAmount(item.getAmount() - 1);
            } else {
                player.getInventory().setItemInMainHand(null);
            }

            // Enviar mensaje de confirmación
            String message = plugin.getConfigs().getLangItemConsumed();
            message = plugin.getVars().replaceVars(message, amount);
            player.sendMessage(General.setColor(message));

            // Cancelar el evento para evitar interacciones no deseadas
            event.setCancelled(true);

        } catch (NumberFormatException e) {
            // Si hay un error al parsear el número, no hacer nada
            return;
        }
    }
}