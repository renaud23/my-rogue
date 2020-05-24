import { DIRECTION } from "../../commons";
import { isEmptyPosition } from "../commons";
import { consumeMove } from "../player";
import getVisibles from "./get-visibles";

function movePlayer(direction, state) {
  const { player, dungeon } = state;
  if (!dungeon) return player;
  const { position, currentLevel } = player;
  const width = dungeon.getWidth(currentLevel);

  switch (direction) {
    case DIRECTION.NORTH: {
      const next = position - width;
      return isEmptyPosition(state, next)
        ? consumeMove({ ...player, position: next })
        : player;
    }
    case DIRECTION.SOUTH: {
      const next = position + width;
      return isEmptyPosition(state, next)
        ? consumeMove({ ...player, position: next })
        : player;
    }
    case DIRECTION.EAST: {
      const next = position + 1;
      return isEmptyPosition(state, next)
        ? consumeMove({ ...player, position: next })
        : player;
    }
    case DIRECTION.WEST: {
      const next = position - 1;
      return isEmptyPosition(state, next)
        ? consumeMove({ ...player, position: next })
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
