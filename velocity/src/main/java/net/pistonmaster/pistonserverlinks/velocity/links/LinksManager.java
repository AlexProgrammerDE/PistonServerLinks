package net.pistonmaster.pistonserverlinks.velocity.links;

import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.util.ServerLink;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.pistonmaster.pistonserverlinks.shared.config.LinkEntry;
import net.pistonmaster.pistonserverlinks.shared.config.ServerLinksConfig;
import net.pistonmaster.pistonserverlinks.shared.model.ServerLinkType;
import net.pistonmaster.pistonserverlinks.velocity.utils.LinkUtil;
import org.slf4j.Logger;

public class LinksManager {
  private final ProxyServer proxy;
  private final Logger logger;
  private final List<ServerLink> links = new ArrayList<>();
  private final Map<String, ServerLink> playerLinks = new HashMap<>();
  private ServerLinksConfig config;

  public LinksManager(ProxyServer proxy, Logger logger, ServerLinksConfig config) {
    this.proxy = proxy;
    this.logger = logger;
    this.config = config;
  }

  public void setConfig(ServerLinksConfig config) {
    this.config = config;
  }

  public void registerLinks() {
    for (Map.Entry<String, LinkEntry> entry : config.getLinks().entrySet()) {
      var name = entry.getKey();
      var link = entry.getValue();
      var type = LinkUtil.toVelocityLink(ServerLinkType.fromName(link.getType()));

      logger.info("Registering link: {}", name);

      if (type == null) {
        links.add(ServerLink.serverLink(
            MiniMessage.miniMessage().deserialize(link.getName(), resolvers()),
            link.getUrl()
        ));
      } else {
        links.add(ServerLink.serverLink(type, link.getUrl()));
      }
    }
  }

  public void registerPlayerLinks() {
    for (Map.Entry<String, LinkEntry> entry : config.getPlayerLinks().entrySet()) {
      var name = entry.getKey();
      var link = entry.getValue();
      var type = LinkUtil.toVelocityLink(ServerLinkType.fromName(link.getType()));
      var permission = link.getPermission();

      logger.info("Registering player link: {}", name);

      if (type == null) {
        playerLinks.put(permission, ServerLink.serverLink(
            MiniMessage.miniMessage().deserialize(link.getName(), resolvers()),
            link.getUrl()
        ));
      } else {
        playerLinks.put(permission, ServerLink.serverLink(type, link.getUrl()));
      }
    }
  }

  public void unregisterLinks() {
    links.clear();
    playerLinks.clear();
  }

  public List<ServerLink> links() {
    return Collections.unmodifiableList(links);
  }

  public Map<String, ServerLink> playerLinks() {
    return Collections.unmodifiableMap(playerLinks);
  }

  private TagResolver resolvers() {
    var builder = TagResolver.builder();

    if (proxy.getPluginManager().isLoaded("miniplaceholders")) {
      try {
        builder.resolver(io.github.miniplaceholders.api.MiniPlaceholders.getGlobalPlaceholders());
      } catch (Exception ignored) {
        // MiniPlaceholders not available
      }
    }

    return builder.build();
  }
}
