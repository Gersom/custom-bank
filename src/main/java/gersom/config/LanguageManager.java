package gersom.config;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import gersom.CustomBank;
import gersom.utils.Console;

public class LanguageManager {
    private final CustomBank plugin;
    private CustomConfig langConfig;
    private static final List<String> SUPPORTED_LANGUAGES = Arrays.asList("en", "es", "other");

    public LanguageManager(CustomBank plugin) {
        this.plugin = plugin;
        createLanguageFiles();
        loadLanguageConfig();
    }

    private void createLanguageFiles() {
        for (String lang : SUPPORTED_LANGUAGES) {
            File langFile = new File(plugin.getDataFolder() + File.separator + "lang", lang + ".yml");
            if (!langFile.exists()) {
                plugin.saveResource("lang" + File.separator + lang + ".yml", false);
                Console.sendMessage(
                    "&a" + plugin.getConfigs().getPrefix() + 
                    " Created language file: " + lang + ".yml"
                );
            }
        }
    }

    private void loadLanguageConfig() {
        String language = plugin.getConfigs().getLanguage();
        if (!SUPPORTED_LANGUAGES.contains(language)) {
            Console.sendMessage(
                "&c" + plugin.getConfigs().getPrefix() +
                " Warning: Unsupported language '" + language + "'. Defaulting to 'en'."
            );
            language = "en";
        }
        
        Console.sendMessage(
            "&a" + plugin.getConfigs().getPrefix() +
            " Loading language: " + language
        );
        langConfig = new CustomConfig(language + ".yml", "lang", plugin);
        langConfig.registerConfig();
    }

    public void reloadLanguageConfig() {
        loadLanguageConfig();
    }

    public String getMessage(String path, String defaultValue) {
        String message = langConfig.getString(path, defaultValue);
        return message.isEmpty() ? defaultValue : message;
    }

    public List<String> getMessageList(String path) {
        List<String> messages = langConfig.getStringList(path);
        return messages.isEmpty() ? Arrays.asList(path + " not found") : messages;
    }

    // Add methods for each message you need to retrieve
    // Plugin messages
    public String getLangPluginEnabled() {
        return getMessage("plugin.enabled", "Plugin has been enabled!");
    }
    public String getLangPluginDisabled() {
        return getMessage("plugin.disabled", "Plugin has been disabled!");
    }

    // About messages
    public String getLangWelcome() {
        return getMessage("plugin.about.welcome", "Welcome!");
    }
    public List<String> getLangDescription() {
        return getMessageList("plugin.about.description");
    }

    // Commands messages
    // Withdraw messages
    public String getLangWithdrawItemObtained() {
        return getMessage("commands.withdraw.item_obtained", "&a{prefix} You have received a bag of money with &e{amount} {coin_name}&a!");
    }
    public String getLangWithdrawFullInventory() {
        return getMessage("commands.withdraw.full_inventory", "&c{prefix} Your inventory is full!");
    }
    public String getLangWithdrawInvalidNumber() {
        return getMessage("commands.withdraw.invalid_number", "&c{prefix} You must enter a valid integer");
    }
    public String getLangWithdrawNoMoney() {
        return getMessage("commands.withdraw.no_money", "&c{prefix} ¡You do not have enough &e{coin_name}&c!");
    }
    public String getLangWithdrawTooMuchMoney() {
        return getMessage("commands.withdraw.too_much_money", "&c{prefix} The amount must be greater than 0");
    }

    // Balance messages
    public String getLangBalance() {
        return getMessage("commands.balance", "&a{prefix} Your current balance is &e{amount} {coin_name}&a.");
    }

    // GiveMoney messages
    public String getLangGiveMoney() {
        return getMessage("commands.givemoney", "&a{prefix} You have given &e{amount} {coin_name}&a to &b{player}&a.");
    }

    // SetMoney messages
    public String getLangSetMoney() {
        return getMessage("commands.setmoney", "&a{prefix} You have changed the balance of &b{player}&a to &e{amount} {coin_name}&a.");
    }

    // ReduceMoney messages
    public String getLangReduceMoney() {
        return getMessage("commands.reducemoney", "&a{prefix} You have reduced the balance of &b{player}&a to &e{amount} {coin_name}&a.");
    }

    // Ranking messages
    public String getLangRanking() {
        return getMessage("commands.ranking", "&a{prefix} Your current rank is &e{rank}&a.");
    }

    public String getLangList() {
        return getMessage("commands.list", "List of commands");
    }
    public String getLangCommandPlayerOnly() {
        return getMessage("commands.player_only", "This command can only be used in-game!");
    }
    public String getLangCommandHelpText() {
        return getMessage("commands.help_text", "To see the list of commands, type");
    }
    public String getLangCommandReload() {
        return getMessage("commands.reload", "Reload the configs");
    }
    public String getLangCommandNotFound() {
        return getMessage("commands.not_found", "Command not found!");
    }
    public String getLangCommandNotPermission() {
        return getMessage("commands.no_permission", "You don't have permission to use this command!");
    }

    // Item money messages
    public String getLangItemName() {
        return getMessage("item_money.name", "&6&lMoney bag (&e{mount}{coin_symbol}&6)");
    }
    public List<String> getLangItemLore() {
        return getMessageList("item_money.lore");
    }
    public String getLangItemConsumed() {
        return getMessage("item_money.consumed", "&a{prefix} You have deposited &e{amount} {coin_name}&a in your account.");
    }
}
