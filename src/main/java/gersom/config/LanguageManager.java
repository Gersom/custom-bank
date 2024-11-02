package gersom.config;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import gersom.CustomMoney;
import gersom.utils.Console;

public class LanguageManager {
    private final CustomMoney plugin;
    private CustomConfig langConfig;
    private static final List<String> SUPPORTED_LANGUAGES = Arrays.asList("en", "es", "other");

    public LanguageManager(CustomMoney plugin) {
        this.plugin = plugin;
        createLanguageFiles();
        loadLanguageConfig();
    }

    private void createLanguageFiles() {
        for (String lang : SUPPORTED_LANGUAGES) {
            File langFile = new File(plugin.getDataFolder() + File.separator + "lang", lang + ".yml");
            if (!langFile.exists()) {
                plugin.saveResource("lang" + File.separator + lang + ".yml", false);
                Console.sendMessage("&aCreated language file: " + lang + ".yml");
            }
        }
    }

    private void loadLanguageConfig() {
        String language = plugin.getConfigs().getLanguage();
        if (!SUPPORTED_LANGUAGES.contains(language)) {
            Console.sendMessage("&cWarning: Unsupported language '" + language + "'. Defaulting to 'en'.");
            language = "en";
        }
        
        Console.sendMessage("&aLoading language: " + language);
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
    public String getLangCommandsList() {
        return getMessage("plugin.commands.list", "List of commands:");
    }
    public String getLangCommandPlayerOnly() {
        return getMessage("plugin.commands.player_only", "This command can only be used in-game!");
    }
    public String getLangCommandHelpText() {
        return getMessage("plugin.commands.help_text", "To see the list of commands, type");
    }
    public String getLangCommandReload() {
        return getMessage("plugin.commands.reload", "Reload the configs");
    }
    public String getLangCommandNotFound() {
        return getMessage("plugin.commands.not_found", "Command not found!");
    }
    public String getLangCommandNotPermission() {
        return getMessage("plugin.commands.no_permission", "You don't have permission to use this command!");
    }
    public String getLangCommandClearRecords() {
        return getMessage("plugin.commands.clear_records", "All the bosses' records have been deleted!");
    }
}