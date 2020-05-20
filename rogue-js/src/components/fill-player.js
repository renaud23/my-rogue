import { getVisibles } from "../player";
import combine from "./combine-fill";
import { TILES, CODE_TO_TILE, PLAYER_ACTIONS, getTile } from "../commons";

function fillHelp(tiles, state, rect) {
  const { dungeon, player } = state;
  const { action } = player;
  const { startX, startY, width, height } = rect;
  const xi = action.position % dungeon.width;
  const yi = Math.trunc(action.position / dungeon.width);
  if (
    xi >= startX &&
    xi <= startX + width &&
    yi >= startY &&
    yi <= startY + height
  ) {
    tiles[xi - startX + (yi - startY) * width] = {
      ...TILES.unknow,
      color: "red",
    };
  }

  return tiles;
}

function fillAction(tiles, state, rect) {
  const { player } = state;
  const { action } = player;

  if (action) {
    switch (action.type) {
      case PLAYER_ACTIONS.help:
        return fillHelp(tiles, state, rect);
      default:
        return tiles;
    }
  }
  return tiles;
}

function fill(tiles, state, rect) {
  const { dungeon, player } = state;
  const { startX, startY, width } = rect;
  const { position } = player;

  const visibles = getVisibles(state);

  return tiles.map(function (tile, i) {
    const px = i % width;
    const py = Math.trunc(i / width);
    const tilePos = startX + px + (startY + py) * dungeon.width;

    if (tilePos === position) {
      return TILES.player;
    }
    if (visibles.indexOf(tilePos) !== -1) {
      return { ...tile, color: "orange" };
    }

    return TILES.unknow;
  });
}

export default combine(fill, fillAction);
