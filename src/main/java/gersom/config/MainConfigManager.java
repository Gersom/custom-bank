package gersom.config;

import java.util.List;

import gersom.CustomMoney;

public class MainConfigManager {
    private final CustomConfig configFile;
    private LanguageManager languageManager;
    private final CustomMoney plugin;

    public MainConfigManager(CustomMoney plugin) {
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
        return configFile.getString("prefix", "[CustomMoney] ");
    }

    public String getLanguage() {
        return configFile.getString("language", "en");
    }

    public String getCoin() {
        return configFile.getString("coin", "GerCoins");
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
    public String getLangCommandsList() {
        return languageManager.getLangCommandsList();
    }
    public String getLangCommandPlayerOnly() {
        return languageManager.getLangCommandPlayerOnly();
    }
    public String getLangCommandHelpText() {
        return languageManager.getLangCommandHelpText();
    }
    public String getLangCommandReload() {
        return languageManager.getLangCommandReload();
    }
    public String getLangCommandNotFound() {
        return languageManager.getLangCommandNotFound();
    }
    public String getLangCommandNotPermission() {
        return languageManager.getLangCommandNotPermission();
    }
    public String getLangCommandClearRecords() {
        return languageManager.getLangCommandClearRecords();
    }
}