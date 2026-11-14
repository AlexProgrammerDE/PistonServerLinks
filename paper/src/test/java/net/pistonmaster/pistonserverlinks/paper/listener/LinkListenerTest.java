package net.pistonmaster.pistonserverlinks.paper.listener;

import static org.mockito.Mockito.*;

import java.net.URI;
import net.pistonmaster.pistonserverlinks.paper.links.LinksManager;
import net.pistonmaster.pistonserverlinks.shared.config.LinkEntry;
import net.pistonmaster.pistonserverlinks.shared.config.ServerLinksConfig;
import org.bukkit.Server;
import org.bukkit.ServerLinks;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.junit.jupiter.api.Test;

class LinkListenerTest {
  @Test
  void sendsPermissionLinksAfterJoinWithoutChangingGlobalLinks() {
    var config = new ServerLinksConfig();
    config.getPlayerLinks().clear();
    config.getPlayerLinks().put("staff", new LinkEntry("Staff", "https://example.com/staff", "WEBSITE", "staff"));
    var player = mock(Player.class);
    var server = mock(Server.class);
    var global = mock(ServerLinks.class);
    var personal = mock(ServerLinks.class);
    when(player.getServer()).thenReturn(server);
    when(server.getServerLinks()).thenReturn(global);
    when(global.copy()).thenReturn(personal);
    when(player.hasPermission("staff")).thenReturn(true);

    new LinkListener(new LinksManager(config)).onPlayerJoin(new PlayerJoinEvent(player, ""));

    verify(personal).addLink(ServerLinks.Type.WEBSITE, URI.create("https://example.com/staff"));
    verify(player).sendLinks(personal);
    verify(global).copy();
    verifyNoMoreInteractions(global);
  }

  @Test
  void excludesLinksWithoutPermissionOrWithoutAPermissionSetting() {
    var config = new ServerLinksConfig();
    config.getPlayerLinks().clear();
    config.getPlayerLinks().put("staff", new LinkEntry("Staff", "https://example.com/staff", "WEBSITE", "staff"));
    config.getPlayerLinks().put("unset", new LinkEntry("Unset", "https://example.com/unset", "WEBSITE", null));
    var player = mock(Player.class);
    var server = mock(Server.class);
    var global = mock(ServerLinks.class);
    var personal = mock(ServerLinks.class);
    when(player.getServer()).thenReturn(server);
    when(server.getServerLinks()).thenReturn(global);
    when(global.copy()).thenReturn(personal);

    new LinkListener(new LinksManager(config)).onPlayerJoin(new PlayerJoinEvent(player, ""));

    verifyNoInteractions(personal);
    verify(player).sendLinks(personal);
  }
}
