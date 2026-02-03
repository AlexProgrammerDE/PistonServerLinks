package net.pistonmaster.pistonserverlinks.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import de.exlll.configlib.NameFormatters;
import de.exlll.configlib.YamlConfigurationProperties;
import de.exlll.configlib.YamlConfigurations;
import java.nio.file.Path;
import net.pistonmaster.pistonserverlinks.shared.config.ServerLinksConfig;
import net.pistonmaster.pistonserverlinks.velocity.commands.MainCommand;
import net.pistonmaster.pistonserverlinks.velocity.links.LinksManager;
import net.pistonmaster.pistonserverlinks.velocity.listener.LinkListener;
import org.bstats.velocity.Metrics;
import org.slf4j.Logger;

@Plugin(
    id = "pistonserverlinks",
    name = "PistonServerLinks",
    version = "@version@",
    description = "@description@",
    url = "https://github.com/AlexProgrammerDE/PistonServerLinks",
    authors = {"AlexProgrammerDE"}
)
public class PistonServerLinksVelocity {
  private final ProxyServer proxy;
  private final Logger logger;
  private final Path dataDirectory;
  private final Metrics.Factory metricsFactory;
  private LinksManager linksManager;

  @Inject
  public PistonServerLinksVelocity(ProxyServer proxy, Logger logger, @DataDirectory Path dataDirectory, Metrics.Factory metricsFactory) {
    this.proxy = proxy;
    this.logger = logger;
    this.dataDirectory = dataDirectory;
    this.metricsFactory = metricsFactory;
  }

  @Subscribe
  public void onProxyInitialization(ProxyInitializeEvent event) {
    var config = loadConfig();
    linksManager = new LinksManager(proxy, logger, config);

    linksManager.registerLinks();
    linksManager.registerPlayerLinks();

    proxy.getEventManager().register(this, new LinkListener(linksManager));

    var commandManager = proxy.getCommandManager();
    var commandMeta = commandManager.metaBuilder("pistonserverlinks")
        .aliases("psl")
        .build();
    commandManager.register(commandMeta, new MainCommand(this));

    metricsFactory.make(this, 24920);

    logger.info("PistonServerLinks enabled!");
  }

  @Subscribe
  public void onProxyShutdown(ProxyShutdownEvent event) {
    if (linksManager != null) {
      linksManager.unregisterLinks();
    }
  }

  public void reloadServerLinks() {
    linksManager.unregisterLinks();
    var config = loadConfig();
    linksManager.setConfig(config);
    linksManager.registerLinks();
    linksManager.registerPlayerLinks();
  }

  public String getVersion() {
    return proxy.getPluginManager().ensurePluginContainer(this).getDescription().getVersion().orElse("unknown");
  }

  private ServerLinksConfig loadConfig() {
    var configPath = dataDirectory.resolve("config.yml");
    return YamlConfigurations.update(
        configPath,
        ServerLinksConfig.class,
        YamlConfigurationProperties.newBuilder()
            .setNameFormatter(NameFormatters.LOWER_UNDERSCORE)
            .build()
    );
  }
}
