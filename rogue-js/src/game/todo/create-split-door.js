import activate from "../activate-player";
import { updatePlayerView } from "../player";

function splitDoor(level, door) {
  const { id, opened } = door;
  return level.map(function (d) {
    if (id === d.id) {
      return { ...door, opened: !opened };
    }
    return d;
  });
}

function create(door) {
  return function (state) {
    const { player, objects } = state;
    const { currentLevel } = player;
    const newObjects = objects.map(function (l, i) {
      if (currentLevel === i) {
        return splitDoor(l, door);
      }
      return l;
    });
    return activate(
      updatePlayerView({
        ...state,
        objects: newObjects,
        player: { ...player, action: undefined },
      })
    );
  };
}

export default create;
