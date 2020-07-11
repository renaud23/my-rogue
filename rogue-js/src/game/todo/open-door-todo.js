import activate from "../activate-player";
import { applyToObject } from "../objects//dungeon-objects";
import { updatePlayerView } from "../player";

function tryTo(state, door) {
  const { locked, opened } = door;
}

function todo(state, door) {
  const { player, objects } = state;
  const newObjects = applyToObject(objects, door, function (d) {
    const { opened } = d;
    if (opened) {
      return { ...d, opened: false };
    }
    return { ...d, opened: true };
  });
  return updatePlayerView({
    ...state,
    objects: newObjects,
    activate,
    player: { ...player, action: undefined },
  });
}

export default todo;
