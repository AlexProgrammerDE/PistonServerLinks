package net.pistonmaster.pistonserverlinks.paper.commands;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.pistonmaster.pistonserverlinks.paper.PistonServerLinksPaper;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

@SuppressFBWarnings("EI_EXPOSE_REP2")
public class MainCommand implements CommandExecutor, TabCompleter {
  private final PistonServerLinksPaper plugin;

  public MainCommand(PistonServerLinksPaper plugin) {
    this.plugin = plugin;
  }

  @Override
  public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
    if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
      if (!sender.hasPermission("pistonserverlinks.reload")) {
        sender.sendMessage(Component.text("You do not have permission to use this command.", NamedTextColor.RED));
        return true;
      }

      plugin.reloadServerLinks();
      sender.sendMessage(Component.text("PistonServerLinks configuration reloaded.", NamedTextColor.GREEN));
      return true;
    }

    sender.sendMessage(Component.text("PistonServerLinks v" + plugin.getPluginMeta().getVersion(), NamedTextColor.GOLD));
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
