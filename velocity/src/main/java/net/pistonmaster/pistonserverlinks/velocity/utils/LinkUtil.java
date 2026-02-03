package net.pistonmaster.pistonserverlinks.velocity.utils;

import com.velocitypowered.api.util.ServerLink;
import net.pistonmaster.pistonserverlinks.shared.model.ServerLinkType;

public final class LinkUtil {
  private LinkUtil() {
  }

  public static ServerLink.Type toVelocityLink(ServerLinkType type) {
    if (type == null) {
      return null;
    }

    return switch (type) {
      case BUG_REPORT -> ServerLink.Type.BUG_REPORT;
      case COMMUNITY_GUIDELINES -> ServerLink.Type.COMMUNITY_GUIDELINES;
      case SUPPORT -> ServerLink.Type.SUPPORT;
      case STATUS -> ServerLink.Type.STATUS;
      case FEEDBACK -> ServerLink.Type.FEEDBACK;
      case COMMUNITY -> ServerLink.Type.COMMUNITY;
      case WEBSITE -> ServerLink.Type.WEBSITE;
      case FORUMS -> ServerLink.Type.FORUMS;
      case NEWS -> ServerLink.Type.NEWS;
      case ANNOUNCEMENTS -> ServerLink.Type.ANNOUNCEMENTS;
    };
  }
}
