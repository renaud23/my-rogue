import combine from "./combine-fill";
import { TILES, PLAYER_ACTIONS } from "../commons";

function fillHelp(tiles, state, rect, tile, path) {
  const { dungeon, player } = state;
  const { action, currentLevel } = player;
  const dungeonWidth = dungeon.getWidth(currentLevel);
  const { startX, startY, width } = rect;
  const xi = action.position % dungeonWidth;
  const yi = Math.trunc(action.position / dungeonWidth);
  const pos = xi - startX + (yi - startY) * width;

  if (path) {
    path.forEach(function (p) {
      const px = p % dungeonWidth;
      const py = Math.trunc(p / dungeonWidth);
      const poss = px - startX + (py - startY) * width;
      tiles[poss] = { ...tiles[poss], char: ".", color: "chartreuse" };
    });
  }

  tiles[pos] = tile;

  return tiles;
}

function fillAction(tiles, state, rect) {
  const { player } = state;
  const { action } = player;

  if (action) {
    switch (action.type) {
      case PLAYER_ACTIONS.help:
        return fillHelp(tiles, state, rect, {
          ...TILES.unknow,
          color: "red",
        });
      case PLAYER_ACTIONS.navigate: {
        const {
          player: {
            action: { color, path },
          },
        } = state;
        return fillHelp(
          tiles,
          state,
          rect,
          {
            ...TILES.ironSight,
            color,
          },
          path
        );
      }
      case PLAYER_ACTIONS.shoot: {
        return fillHelp(tiles, state, rect, {
          ...TILES.ironSight,
          color: "red",
        });
      }

      default:
        return tiles;
    }
  }
  return tiles;
}

function fill(tiles, state, rect) {
  const { dungeon, player } = state;
  const { startX, startY, width } = rect;
  const { position, visibles, currentLevel, memory } = player;
  const dungeonWidth = dungeon.getWidth(currentLevel);

  return tiles.map(function (tile, i) {
    const px = i % width;
    const py = Math.trunc(i / width);
    const tilePos = startX + px + (startY + py) * dungeonWidth;

    if (tilePos === position) {
      return TILES.player;
    }
    if (visibles.indexOf(tilePos) !== -1) {
      return { ...tile, color: tile.color || "Gold" };
    }
    if (memory && memory[currentLevel] && tilePos in memory[currentLevel]) {
      return { ...tile, color: "MediumBlue" };
    }

    return { ...TILES.unknow, color: "MidnightBlue" };
  });
}

export default combine(fill, fillAction);
