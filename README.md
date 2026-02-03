# PistonServerLinks

Custom server links plugin for Minecraft 1.21+. Adds configurable links to the multiplayer server menu and pause screen.

## Features

- Global and per-player server links with permission-based visibility
- 10 predefined Minecraft link types (Website, Discord, Bug Report, etc.) + custom links
- [MiniMessage](https://docs.advntr.dev/minimessage/index.html) formatting for link names
- [PlaceholderAPI](https://github.com/PlaceholderAPI/PlaceholderAPI) and [MiniPlaceholders](https://github.com/MiniPlaceholders/MiniPlaceholders) support
- YAML configuration via [ConfigLib](https://github.com/Exlll/ConfigLib)
- Supports **Paper**, **Spigot**, and **Velocity**

## Downloads

- [Modrinth](https://modrinth.com/plugin/pistonserverlinks)
- [GitHub Releases](https://github.com/AlexProgrammerDE/PistonServerLinks/releases)

## Building

Requires Java 21+.

```bash
git clone https://github.com/AlexProgrammerDE/PistonServerLinks.git
cd PistonServerLinks
./gradlew build
```

Artifacts will be in `build/libs/`.

## Commands

| Command | Permission | Description |
|---------|-----------|-------------|
| `/pistonserverlinks` | - | Shows plugin version |
| `/pistonserverlinks reload` | `pistonserverlinks.reload` | Reloads configuration |

Alias: `/psl`

## License

[GPL-3.0](LICENSE)
