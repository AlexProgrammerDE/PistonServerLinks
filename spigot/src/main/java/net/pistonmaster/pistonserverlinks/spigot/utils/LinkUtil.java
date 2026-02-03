package net.pistonmaster.pistonserverlinks.spigot.utils;

import net.pistonmaster.pistonserverlinks.shared.model.ServerLinkType;
import org.bukkit.ServerLinks;

public final class LinkUtil {
  private LinkUtil() {
  }

  public static ServerLinks.Type toBukkitLink(ServerLinkType type) {
    if (type == null) {
      return null;
    }

    return switch (type) {
      case BUG_REPORT -> ServerLinks.Type.REPORT_BUG;
      case COMMUNITY_GUIDELINES -> ServerLinks.Type.COMMUNITY_GUIDELINES;
      case SUPPORT -> ServerLinks.Type.SUPPORT;
      case STATUS -> ServerLinks.Type.STATUS;
      case FEEDBACK -> ServerLinks.Type.FEEDBACK;
      case COMMUNITY -> ServerLinks.Type.COMMUNITY;
      case WEBSITE -> ServerLinks.Type.WEBSITE;
      case FORUMS -> ServerLinks.Type.FORUMS;
      case NEWS -> ServerLinks.Type.NEWS;
      case ANNOUNCEMENTS -> ServerLinks.Type.ANNOUNCEMENTS;
    };
  }
}
