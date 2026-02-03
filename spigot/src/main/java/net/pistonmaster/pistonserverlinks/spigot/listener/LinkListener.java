package net.pistonmaster.pistonserverlinks.spigot.listener;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.net.URI;
import java.util.Map;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.pistonmaster.pistonserverlinks.shared.config.LinkEntry;
import net.pistonmaster.pistonserverlinks.shared.model.ServerLinkType;
import net.pistonmaster.pistonserverlinks.spigot.links.LinksManager;
import net.pistonmaster.pistonserverlinks.spigot.utils.LinkUtil;
import net.pistonmaster.pistonserverlinks.spigot.utils.PlaceholderUtil;
import org.bukkit.ServerLinks;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLinksSendEvent;

@SuppressFBWarnings("EI_EXPOSE_REP2")
public class LinkListener implements Listener {
  private static final LegacyComponentSerializer LEGACY_SERIALIZER =
      LegacyComponentSerializer.legacySection();

  private final LinksManager linksManager;

  public LinkListener(LinksManager linksManager) {
    this.linksManager = linksManager;
  }

  @EventHandler
  public void onPlayerLinksSend(PlayerLinksSendEvent event) {
    Player player = event.getPlayer();
    ServerLinks serverLinks = event.getLinks();

    for (Map.Entry<String, LinkEntry> entry : linksManager.getConfig().getPlayerLinks().entrySet()) {
      LinkEntry link = entry.getValue();
      String permission = link.getPermission();

      if (permission != null && player.hasPermission(permission)) {
        ServerLinks.Type type = LinkUtil.toBukkitLink(ServerLinkType.fromName(link.getType()));
        URI uri = URI.create(link.getUrl());

        if (type == null) {
          var component = MiniMessage.miniMessage().deserialize(link.getName(), PlaceholderUtil.tags(player));
          var legacyText = LEGACY_SERIALIZER.serialize(component);
          serverLinks.addLink(legacyText, uri);
        } else {
          serverLinks.addLink(type, uri);
        }
      }
    }
  }
}
