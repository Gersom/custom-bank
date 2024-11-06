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

    public Boolean getEnableSound() {
        return configFile.getBoolean("sound", true);
    }

    public String getCoinName() {
        return configFile.getString("coin.name", "GerCoins");
    }
    public String getCoinSymbol() {
        return configFile.getString("coin.symbol", "⛃");
    }
    
    public String getMoneyBagType() {
        return configFile.getString("item_money.type", "ITEM");
    }
    public String getMoneyBagMaterial() {
        return configFile.getString("item_money.material", "PAPER");
    }
    public String getMoneyBagUrlTexture() {
        return configFile.getString("item_money.url-texture", "http://textures.minecraft.net/texture/9fd108383dfa5b02e86635609541520e4e158952d68c1c8f8f200ec7e88642d");
    }

    public boolean isLoseMoneyOnDeathEnabled() {
        return configFile.getBoolean("lose_money_on_death.enabled", false);
    }
    public int getLoseMoneyOnDeathCost() {
        return configFile.getInt("lose_money_on_death.cost", 100);
    }
    public boolean isLoseMoneyOnDeathMessageEnabled() {
        return configFile.getBoolean("lose_money_on_death.message.enabled", false);
    }
    public String getLoseMoneyOnDeathMessageText() {
        return configFile.getString("lose_money_on_death.message.text", "&cYou lost &e{amount} {coin_name}&c!");
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
    // Withdraw messages
    public String getLangWithdrawItemObtained() {
        return languageManager.getLangWithdrawItemObtained();
    }
    public String getLangWithdrawFullInventory() {
        return languageManager.getLangWithdrawFullInventory();
    }
    public String getLangWithdrawInvalidNumber() {
        return languageManager.getLangWithdrawInvalidNumber();
    }
    public String getLangWithdrawNoMoney() {
        return languageManager.getLangWithdrawNoMoney();
    }
    public String getLangWithdrawTooMuchMoney() {
        return languageManager.getLangWithdrawTooMuchMoney();
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