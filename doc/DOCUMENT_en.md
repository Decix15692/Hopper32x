# Hopper Minecart 32x Documentation

## How It Works

The mod injects into the `MinecartHopper.tick()` method using a Mixin with the injection point `@At("RETURN")`. After the vanilla tick logic finishes executing (which calls `suckInItems()` once), the Mixin calls `suckInItems()` an additional N-1 times, where N is the configured `transfer_per_tick` value.

```
Vanilla tick() -> suckInItems() x1
Hopper32x tick() -> suckInItems() x N (1 vanilla call + N-1 injected calls)
```

The injection point is chosen at the public method `tick()` using `@At("RETURN")`, rather than matching the internal private method via `@At(“INVOKE”)`. This avoids instruction matching failures caused by bytecode inlining or mapping differences.

## Project Structure

```
hopper32x/
  src/main/java/com/hopper32x/hopper32x/
    Hopper32x.java              -- Mod entry point, registers configuration
    Hopper32xConfig.java        -- ModConfigSpec configuration definition
    mixin/
      MinecartHopperMixin.java  -- Mixin: Injects extra suckInItems() calls
  src/main/templates/META-INF/
    neoforge.mods.toml          -- Mod metadata (contains Mixin declaration)
  src/main/resources/
    hopper32x.mixins.json       -- Mixin configuration file
  build.gradle                  -- ModDevGradle build script
  gradle.properties             -- Versions and project properties
```

## Building

```bash
./gradlew build
```

## Generating JAR File

```bash
./gradlew jar
```

The output JAR file is located in the `build/libs/` directory.

## Technical Notes

- The configuration type is `COMMON`, stored on the server and synced to the client; it works in both single-player and multi-player.
- `ModConfigSpec.IntValue.get()` uses an internal cache, so calling it every tick poses no performance issues.
- The Mixin accesses `suckInItems()` and `isEnabled()` via `@Shadow`, avoiding reflection overhead.
- The mod only takes effect when the Hopper Minecart is enabled (not disabled by redstone and not in cooldown).