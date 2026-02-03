package net.pistonmaster.pistonserverlinks.paper.utils;

import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public final class PlaceholderUtil {
  private PlaceholderUtil() {
  }

  public static TagResolver tags(Player player) {
    var builder = TagResolver.builder();

    if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
      builder.resolver(papiTag(player));
    }

    if (Bukkit.getPluginManager().isPluginEnabled("MiniPlaceholders")) {
      try {
        if (player != null) {
          builder.resolver(io.github.miniplaceholders.api.MiniPlaceholders.getAudienceGlobalPlaceholders(player));
        } else {
          builder.resolver(io.github.miniplaceholders.api.MiniPlaceholders.getGlobalPlaceholders());
        }
      } catch (Exception ignored) {
        // MiniPlaceholders not available
      }
    }

    return builder.build();
  }

  private static TagResolver papiTag(Player player) {
    return TagResolver.resolver("papi", (argumentQueue, context) -> {
      var placeholder = argumentQueue.popOr("papi tag requires an argument").value();
      var parsed = me.clip.placeholderapi.PlaceholderAPI.setPlaceholders(player, "%" + placeholder + "%");
      var component = LegacyComponentSerializer.legacySection().deserialize(parsed);
      return Tag.selfClosingInserting(component);
    });
  }
}
