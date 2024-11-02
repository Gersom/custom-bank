package gersom;

import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import gersom.commands.MainCommand;
import gersom.commands.MainTabCompleter;
import gersom.commands.WithdrawCommand;
import gersom.config.MainConfigManager;
import gersom.utils.Console;
import gersom.utils.General;
import gersom.utils.Vars;
import net.milkbowl.vault.economy.Economy;

public class CustomMoney extends JavaPlugin {
    private static Economy econ = null;
    private MainConfigManager configs;

    @Override
    public void onEnable() {
        // Inicializar Vars primero
        Vars.initialize(getDescription());

        // Crear la instancia de MainConfigManager
        this.configs = new MainConfigManager(this);

        // Inicializar configs
        this.configs.initialize();

        if (setupEconomy()) {
            Console.sendMessage(General.setColor(
                "&a" + getConfigs().getPrefix() + "&aVault dependency found! :D"
            ));
        } else {
            Console.sendMessage(General.setColor(
                "&c" + getConfigs().getPrefix() + "&cDisabled due to no Vault dependency found!"
            ));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        registerCommands();

        Console.sendMessage(General.generateHeadFrame());
        Console.printBlankLine();
        Console.sendMessage(
            "&a" + configs.getPrefix() + "&a&l> " + configs.getLangPluginEnabled()
        );
        Console.printBlankLine();
        Console.printFooter(getConfigs().getLanguage(), configs.getPrefix());
        Console.printBlankLine();
        Console.sendMessage(General.generateSeparator());
    }

    @Override
    public void onDisable() {}

    public void registerCommands() {
        // Registrar comando principal
        MainCommand mainCommand = new MainCommand(this);
        MainTabCompleter tabCompleter = new MainTabCompleter();
        PluginCommand commandCM = getCommand("cm");
        if (commandCM != null) {
            commandCM.setExecutor(mainCommand);
            commandCM.setTabCompleter(tabCompleter);
        }
        
        // Registrar comando withdraw/retirar
        WithdrawCommand withdrawCommand = new WithdrawCommand(this);
        PluginCommand commandWd = getCommand("withdraw");
        if (commandWd != null) {
            commandWd.setExecutor(withdrawCommand);
        }
    }

    public MainConfigManager getConfigs() {
        return configs;
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    public static Economy getEconomy() {
        return econ;
    }
}