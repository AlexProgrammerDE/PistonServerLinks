package net.pistonmaster.pistonserverlinks.paper.listener;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.net.URI;
import java.util.Map;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.pistonmaster.pistonserverlinks.paper.links.LinksManager;
import net.pistonmaster.pistonserverlinks.paper.utils.LinkUtil;
import net.pistonmaster.pistonserverlinks.paper.utils.PlaceholderUtil;
import net.pistonmaster.pistonserverlinks.shared.config.LinkEntry;
import net.pistonmaster.pistonserverlinks.shared.model.ServerLinkType;
import org.bukkit.ServerLinks;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLinksSendEvent;

@SuppressFBWarnings("EI_EXPOSE_REP2")
public class LinkListener implements Listener {
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
          serverLinks.addLink(
              MiniMessage.miniMessage().deserialize(link.getName(), PlaceholderUtil.tags(player)),
              uri
          );
        } else {
          serverLinks.addLink(type, uri);
        }
      }
    }
  }
}
