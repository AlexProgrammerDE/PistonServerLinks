package net.pistonmaster.pistonserverlinks.shared.model;

public enum ServerLinkType {
  BUG_REPORT("BUG_REPORT", "REPORT_BUG"),
  COMMUNITY_GUIDELINES("COMMUNITY_GUIDELINES"),
  SUPPORT("SUPPORT"),
  STATUS("STATUS"),
  FEEDBACK("FEEDBACK"),
  COMMUNITY("COMMUNITY"),
  WEBSITE("WEBSITE"),
  FORUMS("FORUMS"),
  NEWS("NEWS"),
  ANNOUNCEMENTS("ANNOUNCEMENTS");

  private final String name;
  private final String[] aliases;

  ServerLinkType(String name, String... aliases) {
    this.name = name;
    this.aliases = aliases != null ? aliases : new String[0];
  }

  public static ServerLinkType fromName(String name) {
    if (name == null) {
      return null;
    }

    for (ServerLinkType type : values()) {
      if (type.name.equalsIgnoreCase(name)) {
        return type;
      }
      for (String alias : type.aliases) {
        if (alias.equalsIgnoreCase(name)) {
          return type;
        }
      }
    }

    return null;
  }

  public String getName() {
    return name;
  }

  public String[] getAliases() {
    return aliases;
  }
}
