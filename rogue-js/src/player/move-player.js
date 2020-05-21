import { DIRECTION, isEmpty } from "../commons";
import getVisibles from "./get-visibles";

function movePlayer(direction, state) {
  const { player, dungeon } = state;
  if (!dungeon) return player;
  const { position, currentLevel } = player;
  const width = dungeon.getWidth(currentLevel);
  const data = dungeon.getData(currentLevel);

  switch (direction) {
    case DIRECTION.NORTH: {
      const next = position - width;

      return isEmpty({ dungeon, player }, next)
        ? { ...player, position: next }
        : player;
    }
    case DIRECTION.SOUTH: {
      const next = position + width;
      return isEmpty({ dungeon, player }, next)
        ? { ...player, position: next }
        : player;
    }
    case DIRECTION.EAST: {
      const next = position + 1;
      return isEmpty({ dungeon, player }, next)
        ? { ...player, position: next }
        : player;
    }
    case DIRECTION.WEST: {
      const next = position - 1;
      return isEmpty({ dungeon, player }, next)
        ? { ...player, position: next }
        : player;
    }

    default:
      return player;
  }
}

export default function (direction, state) {
  const { dungeon } = state;
  const player = movePlayer(direction, state);
  return { ...player, visibles: getVisibles({ player, dungeon }) };
}