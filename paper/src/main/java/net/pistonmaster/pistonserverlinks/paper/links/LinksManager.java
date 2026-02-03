package net.pistonmaster.pistonserverlinks.paper.links;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.pistonmaster.pistonserverlinks.paper.utils.LinkUtil;
import net.pistonmaster.pistonserverlinks.paper.utils.PlaceholderUtil;
import net.pistonmaster.pistonserverlinks.shared.config.LinkEntry;
import net.pistonmaster.pistonserverlinks.shared.config.ServerLinksConfig;
import net.pistonmaster.pistonserverlinks.shared.model.ServerLinkType;
import org.bukkit.Bukkit;
import org.bukkit.ServerLinks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LinksManager {
  private static final Logger LOGGER = LoggerFactory.getLogger(LinksManager.class);
  private final List<ServerLinks.ServerLink> registeredLinks = new ArrayList<>();
  private ServerLinksConfig config;

  public LinksManager(ServerLinksConfig config) {
    this.config = config;
  }

  public void setConfig(ServerLinksConfig config) {
    this.config = config;
  }

  public ServerLinksConfig getConfig() {
    return config;
  }

  public void registerLinks() {
    for (Map.Entry<String, LinkEntry> entry : config.getLinks().entrySet()) {
      var name = entry.getKey();
      var link = entry.getValue();
      var type = LinkUtil.toBukkitLink(ServerLinkType.fromName(link.getType()));
      var uri = URI.create(link.getUrl());

      LOGGER.info("Registering link: {}", name);

      if (type == null) {
        registeredLinks.add(Bukkit.getServerLinks().addLink(
            MiniMessage.miniMessage().deserialize(link.getName(), PlaceholderUtil.tags(null)),
            uri
        ));
      } else {
        registeredLinks.add(Bukkit.getServerLinks().addLink(type, uri));
      }
    }
  }

  public void unregisterLinks() {
    for (ServerLinks.ServerLink link : registeredLinks) {
      Bukkit.getServerLinks().removeLink(link);
    }
    registeredLinks.clear();
  }
}
