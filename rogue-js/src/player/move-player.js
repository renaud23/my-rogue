import { DIRECTION } from "../commons";

function movePlayer(direction, { player, dungeon }) {
  if (!dungeon) return player;
  const { position } = player;
  const { width, tiles, data } = dungeon;

  switch (direction) {
    case DIRECTION.NORTH: {
      const next = position - width;
      return data[next] === tiles.empty
        ? { ...player, position: next }
        : player;
    }
    case DIRECTION.SOUTH: {
      const next = position + width;
      return data[next] === tiles.empty
        ? { ...player, position: next }
        : player;
    }
    case DIRECTION.EAST: {
      const next = position + 1;
      return data[next] === tiles.empty
        ? { ...player, position: next }
        : player;
    }
    case DIRECTION.WEST: {
      const next = position - 1;
      return data[next] === tiles.empty
        ? { ...player, position: next }
        : player;
    }

    default:
      return player;
  }
}

export default movePlayer;
