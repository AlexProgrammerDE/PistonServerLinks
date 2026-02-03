package net.pistonmaster.pistonserverlinks.shared.config;

import de.exlll.configlib.Configuration;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Configuration
@NoArgsConstructor
@AllArgsConstructor
public class LinkEntry {
  private String name = "";
  private String url = "";
  private String type = null;
  private String permission = null;
}
