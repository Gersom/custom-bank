package gersom;

import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import gersom.commands.MainCommand;
import gersom.commands.MainTabCompleter;
import gersom.config.MainConfigManager;
import gersom.events.DeathMoneyEvents;
import gersom.events.MoneyItemEvents;
import gersom.utils.Console;
import gersom.utils.General;
import gersom.utils.Vars;
import net.milkbowl.vault.economy.Economy;

public class CustomBank extends JavaPlugin {
    private static Economy econ = null;
    private MainConfigManager configs;
    private MainCommand mainCommand;
    private Vars vars;

    @Override
    public void onEnable() {
        // Inicializar Vars primero
        this.vars = new Vars(this);
        this.vars.initialize(getDescription());

        Console.sendMessage(General.generateHeadFrame());
        Console.printBlankLine();

        // Inicializar MainConfigManager
        this.configs = new MainConfigManager(this);
        this.configs.initialize();

        boolean vaultEnabled = existsVault();
        if (!vaultEnabled) return;

        registerCommands();
        registerEvents();
        successEnable();
    }

    @Override
    public void onDisable() {
        if (configs != null) {
            Console.sendMessage(General.generateHeadFrame());
            Console.printBlankLine();
            Console.sendMessage(
                "&c" + configs.getPrefix() + "&c&l> " + configs.getLangPluginDisabled()
            );
            Console.printBlankLine();
            Console.printFooter(getConfigs().getLanguage(), configs.getPrefix());
            Console.printBlankLine();
            Console.sendMessage(General.generateSeparator());
        }
    }

    private void registerEvents() {
        // Evento jugador interactuando con un item
        getServer().getPluginManager().registerEvents(new MoneyItemEvents(this), this);

        // Evento muerte del jugador
        getServer().getPluginManager().registerEvents(new DeathMoneyEvents(this), this);
    }

    private void successEnable() {
        Console.sendMessage(
            "&a" + configs.getPrefix() + " " + configs.getLangPluginEnabled()
        );
        Console.printBlankLine();
        Console.printFooter(getConfigs().getLanguage(), configs.getPrefix());
        Console.printBlankLine();
        Console.sendMessage(General.generateSeparator());
    }

    private void registerCommands() {
        // Registrar comando principal
        mainCommand = new MainCommand(this);
        MainTabCompleter tabCompleter = new MainTabCompleter();
        PluginCommand commandCM = getCommand("bank");
        if (commandCM != null) {
            commandCM.setExecutor(mainCommand);
            commandCM.setTabCompleter(tabCompleter);
        }
    }

    private boolean existsVault() {
        if (!setupEconomy() ) {
            Console.sendMessage(General.setColor(
                "&c" + getConfigs().getPrefix() + "&c Disabled due to no Vault dependency found!"
                ));
                getServer().getPluginManager().disablePlugin(this);
                return false;
        }

        Console.sendMessage(General.setColor(
            "&a" + getConfigs().getPrefix() + " Vault dependency found! :D"
        ));
        return true;
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

    public MainConfigManager getConfigs() {
        return configs;
    }

    public static Economy getEconomy() {
        return econ;
    }

    public MainCommand getMainCommand() {
        return mainCommand;
    }

    public Vars getVars() {
        return vars;
    }
}