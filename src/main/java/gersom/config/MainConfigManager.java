package gersom.config;

import java.util.List;

import gersom.CustomBank;

public class MainConfigManager {
    private final CustomConfig configFile;
    private LanguageManager languageManager;
    private final CustomBank plugin;

    public MainConfigManager(CustomBank plugin) {
        this.plugin = plugin;
        this.configFile = new CustomConfig("config.yml", null, plugin);
        this.configFile.registerConfig();
    }

    public void initialize() {
        this.languageManager = new LanguageManager(plugin);
    }

    public void reloadConfig() {
        configFile.reloadConfig();
        languageManager.reloadLanguageConfig();
    }

    public void setLanguage(String language) {
        configFile.getConfig().set("language", language);
        configFile.saveConfig();
        languageManager.reloadLanguageConfig();
    }

    public String getPrefix() {
        return configFile.getString("prefix", "[CustomBank]");
    }

    public String getLanguage() {
        return configFile.getString("language", "en");
    }

    public String getCoinName() {
        return configFile.getString("coin.name", "GerCoins");
    }
    public String getCoinSymbol() {
        return configFile.getString("coin.symbol", "⛃");
    }

    // Delegate message methods to LanguageManager
    // Plugin messages
    public String getLangPluginEnabled() {
        return languageManager.getLangPluginEnabled();
    }
    public String getLangPluginDisabled() {
        return languageManager.getLangPluginDisabled();
    }

    // About messages
    public String getLangWelcome() {
        return languageManager.getLangWelcome();
    }
    public List<String> getLangDescription() {
        return languageManager.getLangDescription();
    }

    // Commands messages
    // Widthdraw messages
    public String getLangWidthdrawItemObtained() {
        return languageManager.getLangWidthdrawItemObtained();
    }
    public String getLangWidthdrawFullInventory() {
        return languageManager.getLangWidthdrawFullInventory();
    }
    public String getLangWidthdrawInvalidNumber() {
        return languageManager.getLangWidthdrawInvalidNumber();
    }
    public String getLangWidthdrawNoMoney() {
        return languageManager.getLangWidthdrawNoMoney();
    }
    public String getLangWidthdrawTooMuchMoney() {
        return languageManager.getLangWidthdrawTooMuchMoney();
    }

    // Balance messages
    public String getLangBalance() {
        return languageManager.getLangBalance();
    }

    // GiveMoney messages
    public String getLangGiveMoney() {
        return languageManager.getLangGiveMoney();
    }

    // SetMoney messages
    public String getLangSetMoney() {
        return languageManager.getLangSetMoney();
    }

    // ReduceMoney messages
    public String getLangReduceMoney() {
        return languageManager.getLangReduceMoney();
    }

    // Ranking messages
    public String getLangRanking() {
        return languageManager.getLangRanking();
    }

    public String getLangList() {
        return languageManager.getLangList();
    }
    public String getLangPlayerOnly() {
        return languageManager.getLangCommandPlayerOnly();
    }
    public String getLangHelpText() {
        return languageManager.getLangCommandHelpText();
    }
    public String getLangReload() {
        return languageManager.getLangCommandReload();
    }
    public String getLangNotFound() {
        return languageManager.getLangCommandNotFound();
    }
    public String getLangNotPermission() {
        return languageManager.getLangCommandNotPermission();
    }

    // Item messages
    public String getLangItemName() {
        return languageManager.getLangItemName();
    }
    public List<String> getLangItemLore() {
        return languageManager.getLangItemLore();
    }
    public String getLangItemConsumed() {
        return languageManager.getLangItemConsumed();
    }
}