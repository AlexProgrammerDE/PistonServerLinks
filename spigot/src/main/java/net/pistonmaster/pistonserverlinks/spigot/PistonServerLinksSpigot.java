package net.pistonmaster.pistonserverlinks.spigot;

import de.exlll.configlib.NameFormatters;
import de.exlll.configlib.YamlConfigurationProperties;
import de.exlll.configlib.YamlConfigurations;
import net.pistonmaster.pistonserverlinks.shared.config.ServerLinksConfig;
import net.pistonmaster.pistonserverlinks.spigot.commands.MainCommand;
import net.pistonmaster.pistonserverlinks.spigot.links.LinksManager;
import net.pistonmaster.pistonserverlinks.spigot.listener.LinkListener;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

public class PistonServerLinksSpigot extends JavaPlugin {
  private LinksManager linksManager;

  @Override
  public void onEnable() {
    var config = loadConfig();
    linksManager = new LinksManager(config);

    linksManager.registerLinks();

    getServer().getPluginManager().registerEvents(new LinkListener(linksManager), this);

    var mainCommand = new MainCommand(this);
    var command = getCommand("pistonserverlinks");
    if (command != null) {
      command.setExecutor(mainCommand);
      command.setTabCompleter(mainCommand);
    }

    new Metrics(this, 24920);

    getLogger().info("PistonServerLinks enabled!");
  }

  @Override
  public void onDisable() {
    if (linksManager != null) {
      linksManager.unregisterLinks();
    }
  }

  public void reloadServerLinks() {
    linksManager.unregisterLinks();
    var config = loadConfig();
    linksManager.setConfig(config);
    linksManager.registerLinks();
  }

  private ServerLinksConfig loadConfig() {
    var configPath = getDataFolder().toPath().resolve("config.yml");
    return YamlConfigurations.update(
        configPath,
        ServerLinksConfig.class,
        YamlConfigurationProperties.newBuilder()
            .setNameFormatter(NameFormatters.LOWER_UNDERSCORE)
            .build()
    );
  }
}
