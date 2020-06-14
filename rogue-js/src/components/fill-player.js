import { TILES, PLAYER_ACTIONS } from "../commons";

function getSpecial(tile, tilePos, state, rect) {
  const { player } = state;
  const { visibles, action } = player;

  if (action) {
    const { rangePositions } = action;
    if (
      rangePositions &&
      rangePositions.indexOf(tilePos) !== -1 &&
      visibles.indexOf(tilePos) !== -1
    ) {
      return { ...tile, bgColor: "rgba(50,140,10,0.4)" };
    }
  }

  return tile;
}

function isInPathAction(state, tilePos) {
  const { player } = state;
  const { action } = player;
  if (player) {
    if (action) {
      const { path } = action;
      if (path && path.indexOf(tilePos) !== -1) {
        return true;
      }
    }
  }

  return false;
}

function isNavigateOrShootAction(state, tilePos) {
  const { player } = state;
  const { action } = player;
  if (action) {
    const { type, position } = action;
    if (
      (type === PLAYER_ACTIONS.navigate || type === PLAYER_ACTIONS.shoot) &&
      position === tilePos
    ) {
      return true;
    }
  }

  return false;
}

function isHelp(state, tilePos) {
  const { player } = state;
  const { action } = player;
  if (action) {
    const { type, position } = action;
    if (type === PLAYER_ACTIONS.help && position === tilePos) {
      return true;
    }
  }

  return false;
}

function isInPlayerMemory(state, tilePos) {
  const { player } = state;
  const { memory, currentLevel } = player;
  if (memory && memory[currentLevel] && tilePos in memory[currentLevel]) {
    return true;
  }

  return false;
}

function getCharTile(tile, tilePos, state, rect) {
  const { player } = state;
  const { position, visibles } = player;

  if (isNavigateOrShootAction(state, tilePos)) {
    return { ...TILES.ironSight, color: "red" };
  }
  if (isHelp(state, tilePos)) {
    return { ...TILES.helpSight };
  }
  if (tilePos === position) {
    return { ...TILES.player };
  }
  if (isInPathAction(state, tilePos)) {
    return { ...TILES.path };
  }
  if (visibles.indexOf(tilePos) !== -1) {
    return { ...tile, color: tile.color || "Gold" };
  }
  if (isInPlayerMemory(state, tilePos)) {
    return { ...tile, color: "MediumBlue" };
  }

  return { ...TILES.unknown, color: "MidnightBlue" };
}

function fill(tiles, state, rect) {
  const { dungeon, player } = state;
  const { startX, startY, width } = rect;
  const { currentLevel } = player;
  const dungeonWidth = dungeon.getWidth(currentLevel);

  return tiles.map(function (tile, i) {
    const px = i % width;
    const py = Math.trunc(i / width);
    const { position } = tile;
    // const tilePos = startX + px + (startY + py) * dungeonWidth;

    return {
      ...getSpecial(
        getCharTile(tile, position, state, rect),
        position,
        state,
        rect
      ),
      position,
    };
  });
}

export default fill;
