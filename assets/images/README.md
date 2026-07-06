# Image Assets

These are the image files the game now loads automatically:

- `player.png`
- `wall.png`
- `hazard.png`
- `jewel.png`
- `game-background.png`
- `menu-background.png`
- `result-background.png`

## Good places to get replacement art

- `https://kenney.nl/assets`
- `https://opengameart.org/`
- `https://itch.io/game-assets`
- `https://craftpix.net/`

## Recommended formats

- Sprites: transparent `PNG`
- Backgrounds: regular `PNG`

## Recommended sizes

- `player.png`: `512x512` or larger
- `wall.png`: `512x512` or larger
- `hazard.png`: `512x512` or larger
- `jewel.png`: `512x512` or larger
- Backgrounds: `1600x900` or larger

## How replacement works

- Keep the same filenames if you want the game to pick them up automatically.
- The game trims transparent padding around sprite images before drawing them.
- The game scales sprites down to fit the tile size at runtime.
- Backgrounds are scaled to fill the logical game screen automatically.
- The player sprite now works best when it is roughly square-ish because the game uses squash and stretch animation.

## Style advice

- Use side-view platformer art for the player.
- Keep wall and hazard sprites front-facing and tile-friendly.
- Use clean silhouettes and high contrast for pickups.
- Avoid tiny details that disappear when scaled down in-game.
