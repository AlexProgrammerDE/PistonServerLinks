package net.pistonmaster.pistonserverlinks.spigot.commands;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import net.pistonmaster.pistonserverlinks.spigot.PistonServerLinksSpigot;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

@SuppressFBWarnings("EI_EXPOSE_REP2")
public class MainCommand implements CommandExecutor, TabCompleter {
  private final PistonServerLinksSpigot plugin;

  public MainCommand(PistonServerLinksSpigot plugin) {
    this.plugin = plugin;
  }

  @Override
  public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
    if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
      if (!sender.hasPermission("pistonserverlinks.reload")) {
        sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
        return true;
      }

      plugin.reloadServerLinks();
      sender.sendMessage(ChatColor.GREEN + "PistonServerLinks configuration reloaded.");
      return true;
    }

    sender.sendMessage(ChatColor.GOLD + "PistonServerLinks v" + plugin.getDescription().getVersion());
    return true;
  }

  @Override
  public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
    if (args.length == 1 && sender.hasPermission("pistonserverlinks.reload")) {
      return List.of("reload");
    }
    return Collections.emptyList();
  }
}
