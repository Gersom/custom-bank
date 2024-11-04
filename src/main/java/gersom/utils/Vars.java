/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package gersom.utils;

import org.bukkit.plugin.PluginDescriptionFile;

import gersom.CustomBank;

/**
 *
 * @author Gersom
 */
public class Vars {
    public static String version;
    public static String name;
    public static String title;
    public static String author;
    private static CustomBank plugin;

    public Vars (CustomBank plugin) {
        Vars.plugin = plugin;
    }

    public void initialize(PluginDescriptionFile plugin) {
        name = plugin.getName();
        version = plugin.getVersion();
        author = String.join(", ", plugin.getAuthors());
    }

    public String replaceVars(String message) {
        return replaceVars(message, null);
    }

    public String replaceVars(String message, Integer amount) {
        message = message.replace("{prefix}", plugin.getConfigs().getPrefix());
        message = message.replace("{coin_name}", plugin.getConfigs().getCoinName());
        message = message.replace("{coin_symbol}", plugin.getConfigs().getCoinSymbol());
        if (amount != null) {
            message = message.replace("{amount}", "" + amount);
        }
        return message;
    }
}
