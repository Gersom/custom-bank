package gersom.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class MainTabCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            // Comandos básicos (permiso use)
            if (sender.hasPermission("custombank.use")) {
                completions.add("withdraw");
                completions.add("balance");
                completions.add("ranking");
                completions.add("author");
            }

            // Comandos de administrador
            if (sender.hasPermission("custombank.admin")) {
                completions.add("givemoney");
                completions.add("setmoney");
                completions.add("reducemoney");
                completions.add("reload");
                completions.add("version");
                completions.add("help");
            }
        } else if (args.length == 2) {
            if ("givemoney".equals(args[0]) || "setmoney".equals(args[0]) || "reducemoney".equals(args[0])) {
                Bukkit.getOnlinePlayers().forEach(player -> completions.add(player.getName()));
            }
        }

        // Filtrar completaciones basadas en lo que el usuario ya ha escrito
        return completions.stream()
            .filter(completion -> completion.toLowerCase().startsWith(args[args.length - 1].toLowerCase()))
            .collect(Collectors.toList());
    }
}