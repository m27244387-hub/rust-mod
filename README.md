# Rust Building System — MVP 0.1.0

Minecraft Java 1.20.1 / Forge 47.2.0 / Java 17.

## What is implemented
- Forge mod entry point and deferred registries.
- Building Plan item: server-side right-click placement of a basic wall; if the target has air below, it places a twig foundation.
- Building Hammer item: right-click a registered building piece to advance its `tier` blockstate from Twig to Armored.
- Four initial building blocks: twig foundation, wall, floor, roof.
- Resource/model/lang JSON templates and a Gradle Forge build.

## Important MVP limitations
This is the first compiling-oriented scaffold, not yet a complete Rust clone:
- Tier changes are currently free; resource costs are not wired.
- HP, Tool Cupboard ownership/authorization, upkeep, stability graph, raid damage, doors and code locks are not implemented yet.
- Placement currently uses a simple adjacent-block placement rather than a full Rust grid/radial menu.
- Models are vanilla-style placeholder models. Replace the placeholder textures with original Rust-inspired art before release.

## Build
1. Install a 64-bit JDK 17.
2. Open this folder in IntelliJ IDEA or run Gradle from a terminal.
3. On first setup, generate the Gradle wrapper if needed: `gradle wrapper --gradle-version 8.1.1`.
4. Run `./gradlew genIntellijRuns` (optional for IntelliJ) and `./gradlew build`.
5. The mod jar is produced in `build/libs/`.

On Windows use `gradlew.bat build`.

## Test
Run `./gradlew runClient`, create a creative world, and obtain:
- `/give @p rustbuilding:building_plan`
- `/give @p rustbuilding:building_hammer`
- `/give @p rustbuilding:twig_foundation`
- `/give @p rustbuilding:building_wall`

Use the Building Plan on a block face to place a piece. Use the Hammer on a building piece to cycle its tier.

## Next milestones
1. Proper placement preview, piece selector, grid snapping and rotation.
2. Per-piece HP and damage persistence.
3. Tool Cupboard, UUID authorization and building privilege.
4. Stability graph and collapse propagation.
5. Upkeep/decay scheduler.
6. Explosives, damage profiles, doors and code locks.
