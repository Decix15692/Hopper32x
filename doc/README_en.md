# Hopper Minecart 32x

A lightweight NeoForge mod that increases the item transfer rate of Hopper Minecarts. Vanilla Hopper Minecarts transfer 1 item per tick; this mod boosts it to 32 (configurable, up to 1024).

## Requirements

| Dependency | Version |
|------|------|
| Minecraft | 1.21.11 |
| NeoForge | 21.11.44 |
| Java | 21 |
| ModDevGradle | 2.0.143 |

## Features

- Hopper Minecarts transfer 32 items per tick (vanilla is 1)
- Transfer rate is adjustable via configuration file
- Range: 1 (vanilla behavior) ~ 1024
- Hot Reload: Changes to the configuration file take effect immediately in-game without requiring a restart
- Minimal Intrusion: Only one Mixin injection; does not add any game content

## Important Notice
Mods with similar functionality or mods that modify ticks may cause compatibility errors.
If this occurs, please submit an issue.

## Installation

1. Download the mod JAR file and place it in the `.minecraft/version/your_minecraft_version/mods/` directory
2. Ensure NeoForge version is ≥ 21.11.44
3. Launch Minecraft

## Configuration

After the first launch, the mod will automatically generate `config/hopper32x-common.toml`.

```toml
#====================================
# Hopper32x Configuration
#====================================
#
#Items transferred per tick by hopper minecart.
#Default: 32 | Range: 1-1024
#Set to 1 to disable the mod effect (vanilla behavior).
transfer_per_tick = 32
```

| Parameter | Default | Range | Description |
|------|--------|------|------|
| `transfer_per_tick` | 32 | 1 - 1024 | Items transferred per tick. Set to 1 to match vanilla behavior. |

Changes take effect immediately after saving the configuration file; no restart is required.

For other technical details, please refer to [here](DOCUMENT_en.md)

## License

MIT