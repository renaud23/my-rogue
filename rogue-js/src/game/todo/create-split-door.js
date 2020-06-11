import activate from "../activate-player";
import { updatePlayerView } from "../player";
import { createDoor } from "../objects/specials";

function splitDoor(level, door) {
  const { id, opened, level: lv, position } = door;
  return level.map(function (d) {
    if (id === d.id) {
      return { ...createDoor(position, lv, !opened) };
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
