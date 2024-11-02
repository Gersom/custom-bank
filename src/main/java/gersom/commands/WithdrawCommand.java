package gersom.commands;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;

import gersom.CustomMoney;
import gersom.utils.Console;
import gersom.utils.General;
import net.milkbowl.vault.economy.Economy;


public class WithdrawCommand implements CommandExecutor {
    private final CustomMoney plugin;

    public WithdrawCommand(CustomMoney plugin) {
        this.plugin = plugin;
    }

    @Override
    @SuppressWarnings({"", "CallToPrintStackTrace"})
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        // Si se ejecuta el comando desde la consola
        if (!(sender instanceof Player)) {
            Console.sendMessage("&c" + plugin.getConfigs().getPrefix() + plugin.getConfigs().getLangCommandPlayerOnly());
            return true;
        }

        // Verificar si hay espacio en el inventario
        Player player = (Player) sender;
        PlayerInventory inventory = player.getInventory();
        if (inventory.firstEmpty() == -1) {
            player.sendMessage(General.setColor(
                "&c" + plugin.getConfigs().getPrefix() + "&c¡Tu inventario está lleno!"
            ));
            return true;
        }

        // Retirar dinero
        boolean successWithdraw = withdrawMoney(player, 100);
        if (!successWithdraw) return true;

        // Dar la cabeza al jugador
        ItemStack sackHead = generateSackHead("100");
        inventory.addItem(sackHead);

        // Enviar mensaje de confirmación
        player.sendMessage(General.setColor(
            "&a" + plugin.getConfigs().getPrefix() + "&a¡Has recibido una bolsa de dinero con &e&l100 GerCoins&a!"
        ));

        return true;
    };

    @SuppressWarnings({"", "CallToPrintStackTrace"})
    private ItemStack generateSackHead(String mount) {
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
        sackHeadMeta.setDisplayName(General.setColor("&6Bolsa de dinero (&e&l" + mount + "$&6)"));
        java.util.List<String> paperLore = new java.util.ArrayList<>();
        paperLore.add(General.setColor("&aDale click derecho para cobrar"));
        sackHeadMeta.setLore(paperLore);

        // Aplicar los cambios al ItemStack
        sackHead.setItemMeta(sackHeadMeta);

        return sackHead;
    }

    private boolean withdrawMoney(Player player, double amount) {
        Economy econ = CustomMoney.getEconomy();
        double money = econ.getBalance(player);

        if (money < amount) {
            player.sendMessage(General.setColor(
                "&c" + plugin.getConfigs().getPrefix() + "&c¡No tienes suficiente dinero!"
            ));
            return false;
        } else {
            econ.withdrawPlayer(player, 100);
            return true;
        }
    }
}
