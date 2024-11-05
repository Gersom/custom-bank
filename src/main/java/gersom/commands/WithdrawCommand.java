package gersom.commands;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;

import gersom.CustomBank;
import gersom.utils.General;
import net.milkbowl.vault.economy.Economy;


public class WithdrawCommand {
    private final CustomBank plugin;

    public WithdrawCommand(CustomBank plugin) {
        this.plugin = plugin;
    }

    @SuppressWarnings({"", "CallToPrintStackTrace"})
    public void commandLogic(Player player, String[] args) {
        if (args.length == 1) {
            String message = plugin.getConfigs().getLangWithdrawInvalidNumber();
            message = message.replace("{prefix}", plugin.getConfigs().getPrefix());
            player.sendMessage(General.setColor(message));
            return;
        }

        PlayerInventory inventory = player.getInventory();
        if (inventory.firstEmpty() == -1) {
            String message = plugin.getConfigs().getLangWithdrawFullInventory();
            message = message.replace("{prefix}", plugin.getConfigs().getPrefix());
            player.sendMessage(General.setColor(message));
            return;
        }

        int amount;
        try {
            // Reemplazar comas por puntos para manejar ambos formatos
            String numberStr = args[1].replace(',', '.');
            // Convertir a double primero para poder manejar decimales
            double tempAmount = Double.parseDouble(numberStr);
            
            // Verificar que el número sea positivo
            if (tempAmount <= 0) {
                String message = plugin.getConfigs().getLangWithdrawTooMuchMoney();
                message = message.replace("{prefix}", plugin.getConfigs().getPrefix());
                player.sendMessage(General.setColor(message));
                return;
            }

            // Redondear al entero más cercano
            amount = (int) Math.round(tempAmount);
        }
        
        catch (NumberFormatException e) {
            String message = plugin.getConfigs().getLangWithdrawInvalidNumber();
            message = message.replace("{prefix}", plugin.getConfigs().getPrefix());
            player.sendMessage(General.setColor(message));
            return;
        }

        // Retirar dinero
        boolean successWithdraw = withdrawMoney(player, amount);
        if (!successWithdraw) return;

        // Dar la cabeza al jugador
        ItemStack sackHead = generateSackHead(amount);
        inventory.addItem(sackHead);

        // Enviar mensaje de confirmación
        String message = plugin.getConfigs().getLangWithdrawItemObtained();
        message = plugin.getVars().replaceVars(message, amount);
        player.sendMessage(General.setColor(message));
    }

    @SuppressWarnings({"", "CallToPrintStackTrace"})
    private ItemStack generateSackHead(Integer amount) {
        // Crear la cabeza personalizada
        ItemStack sackHead = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta sackHeadMeta = (SkullMeta) sackHead.getItemMeta();
        
        PlayerProfile dummyProfile = Bukkit.createPlayerProfile(UUID.randomUUID(), "SackOfMoney");
        PlayerTextures textures = dummyProfile.getTextures();
        URL urlObject;
        try {
            urlObject = new URL("http://textures.minecraft.net/texture/9fd108383dfa5b02e86635609541520e4e158952d68c1c8f8f200ec7e88642d");
            textures.setSkin(urlObject);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        dummyProfile.setTextures(textures);
        sackHeadMeta.setOwnerProfile(dummyProfile);

        // Establecer nombre y lore
        String sackItemName = plugin.getConfigs().getLangItemName();
        sackItemName = plugin.getVars().replaceVars(sackItemName, amount);
        sackHeadMeta.setDisplayName(General.setColor(sackItemName));

        java.util.List<String> paperLore = new java.util.ArrayList<>();
        List<String> sackItemLore = plugin.getConfigs().getLangItemLore();
        for (String line : sackItemLore) {
            line = plugin.getVars().replaceVars(line, amount);
            paperLore.add(General.setColor(line));
        }
        sackHeadMeta.setLore(paperLore);

        // Aplicar los cambios al ItemStack
        sackHead.setItemMeta(sackHeadMeta);

        return sackHead;
    }

    private boolean withdrawMoney(Player player, Integer amount) {
        Economy econ = CustomBank.getEconomy();
        double money = econ.getBalance(player);

        if (money < amount) {
            String message = plugin.getConfigs().getLangWithdrawNoMoney();
            message = plugin.getVars().replaceVars(message);
            player.sendMessage(General.setColor(message));
            return false;
        } else {
            econ.withdrawPlayer(player, amount);
            return true;
        }
    }
}
