package net.pistonmaster.pistonserverlinks.spigot.links;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.pistonmaster.pistonserverlinks.shared.config.LinkEntry;
import net.pistonmaster.pistonserverlinks.shared.config.ServerLinksConfig;
import net.pistonmaster.pistonserverlinks.shared.model.ServerLinkType;
import net.pistonmaster.pistonserverlinks.spigot.utils.LinkUtil;
import net.pistonmaster.pistonserverlinks.spigot.utils.PlaceholderUtil;
import org.bukkit.Bukkit;
import org.bukkit.ServerLinks;

public class LinksManager {
  private static final Logger LOGGER = Logger.getLogger(LinksManager.class.getName());
  private static final LegacyComponentSerializer LEGACY_SERIALIZER =
      LegacyComponentSerializer.legacySection();

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

      LOGGER.info("Registering link: " + name);

      if (type == null) {
        var component = MiniMessage.miniMessage().deserialize(link.getName(), PlaceholderUtil.tags(null));
        var legacyText = LEGACY_SERIALIZER.serialize(component);
        registeredLinks.add(Bukkit.getServerLinks().addLink(legacyText, uri));
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
