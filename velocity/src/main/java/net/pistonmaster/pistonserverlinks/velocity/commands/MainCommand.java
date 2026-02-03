package net.pistonmaster.pistonserverlinks.velocity.commands;

import com.velocitypowered.api.command.SimpleCommand;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.pistonmaster.pistonserverlinks.velocity.PistonServerLinksVelocity;

public class MainCommand implements SimpleCommand {
  private final PistonServerLinksVelocity plugin;

  public MainCommand(PistonServerLinksVelocity plugin) {
    this.plugin = plugin;
  }

  @Override
  public void execute(Invocation invocation) {
    var source = invocation.source();
    var args = invocation.arguments();

    if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
      if (!source.hasPermission("pistonserverlinks.reload")) {
        source.sendMessage(Component.text("You do not have permission to use this command.", NamedTextColor.RED));
        return;
      }

      plugin.reloadServerLinks();
      source.sendMessage(Component.text("PistonServerLinks configuration reloaded.", NamedTextColor.GREEN));
      return;
    }

    source.sendMessage(Component.text("PistonServerLinks v" + plugin.getVersion(), NamedTextColor.GOLD));
  }

  @Override
  public CompletableFuture<List<String>> suggestAsync(Invocation invocation) {
    if (invocation.arguments().length <= 1 && invocation.source().hasPermission("pistonserverlinks.reload")) {
      return CompletableFuture.completedFuture(List.of("reload"));
    }
    return CompletableFuture.completedFuture(List.of());
  }

  @Override
  public boolean hasPermission(Invocation invocation) {
    return true;
  }
}
