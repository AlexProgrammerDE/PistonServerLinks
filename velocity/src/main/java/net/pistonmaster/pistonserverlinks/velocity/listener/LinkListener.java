package net.pistonmaster.pistonserverlinks.velocity.listener;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PostLoginEvent;
import com.velocitypowered.api.network.ProtocolState;
import com.velocitypowered.api.network.ProtocolVersion;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.util.ServerLink;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.pistonmaster.pistonserverlinks.velocity.links.LinksManager;

@SuppressFBWarnings("EI_EXPOSE_REP2")
public class LinkListener {
  private final LinksManager linksManager;

  public LinkListener(LinksManager linksManager) {
    this.linksManager = linksManager;
  }

  @Subscribe
  public void onPostLogin(PostLoginEvent event) {
    Player player = event.getPlayer();

    ProtocolState protocolState = player.getProtocolState();
    if (protocolState != ProtocolState.CONFIGURATION && protocolState != ProtocolState.PLAY) {
      return;
    }

    ProtocolVersion protocolVersion = player.getProtocolVersion();
    if (!protocolVersion.noLessThan(ProtocolVersion.MINECRAFT_1_21)) {
      return;
    }

    List<ServerLink> allLinks = new ArrayList<>(linksManager.links());

    for (Map.Entry<String, ServerLink> entry : linksManager.playerLinks().entrySet()) {
      if (player.hasPermission(entry.getKey())) {
        allLinks.add(entry.getValue());
      }
    }

    player.setServerLinks(allLinks);
  }
}
