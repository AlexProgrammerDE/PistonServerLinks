package net.pistonmaster.pistonserverlinks.shared.config;

import de.exlll.configlib.Comment;
import de.exlll.configlib.Configuration;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.Getter;

@Getter
@Configuration
public class ServerLinksConfig {
  @Comment({
    "Global server links visible to all players.",
    "Possible types: BUG_REPORT, COMMUNITY_GUIDELINES, SUPPORT, STATUS,",
    "  FEEDBACK, COMMUNITY, WEBSITE, FORUMS, NEWS, ANNOUNCEMENTS",
    "Leave type empty or omit it for a custom link with a custom display name.",
    "The name field supports MiniMessage formatting."
  })
  private Map<String, LinkEntry> links = createDefaultLinks();

  @Comment({
    "Per-player links that are only shown if the player has the specified permission.",
    "Each entry requires a 'permission' field."
  })
  private Map<String, LinkEntry> playerLinks = createDefaultPlayerLinks();

  private static Map<String, LinkEntry> createDefaultLinks() {
    var links = new LinkedHashMap<String, LinkEntry>();
    links.put("website", new LinkEntry("<gold>Our Website", "https://example.com", "WEBSITE", null));
    links.put("discord", new LinkEntry("<blue>Discord", "https://discord.gg/example", "COMMUNITY", null));
    links.put("bugs", new LinkEntry("Report a Bug", "https://example.com/bugs", "BUG_REPORT", null));
    return links;
  }

  private static Map<String, LinkEntry> createDefaultPlayerLinks() {
    var links = new LinkedHashMap<String, LinkEntry>();
    links.put("staff_panel", new LinkEntry("<red>Staff Panel", "https://example.com/staff", null, "pistonserverlinks.link.staff"));
    return links;
  }
}
