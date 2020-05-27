import combine from "./combine-fill";
import { TILES, PLAYER_ACTIONS } from "../commons";

function fillHelp(tiles, state, rect, tile) {
  const { dungeon, player } = state;
  const { action, currentLevel } = player;
  const dungeonWidth = dungeon.getWidth(currentLevel);
  const { startX, startY, width } = rect;
  const xi = action.position % dungeonWidth;
  const yi = Math.trunc(action.position / dungeonWidth);
  // if (
  //   xi >= startX &&
  //   xi <= startX + width &&
  //   yi >= startY &&
  //   yi <= startY + height
  // ) {
  tiles[xi - startX + (yi - startY) * width] = tile;
  // }

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
  const { position, visibles, currentLevel } = player;
  const dungeonWidth = dungeon.getWidth(currentLevel);

  return tiles.map(function (tile, i) {
    const px = i % width;
    const py = Math.trunc(i / width);
    const tilePos = startX + px + (startY + py) * dungeonWidth;

    if (tilePos === position) {
      return TILES.player;
    }
    if (visibles.indexOf(tilePos) !== -1) {
      return { ...tile, color: tile.color || "orange" };
    }

    return TILES.unknow;
  });
}

export default combine(fill, fillAction);
