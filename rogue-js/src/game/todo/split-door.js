import activate from "../activate-player";
import { updatePlayerView } from "../player";
import { createDoor } from "../objects/specials";
import { replaceObject } from "../objects/dungeon-objects";

function splitDoor(state, door) {
  const { player, objects } = state;
  const { position, level, opened } = door;

  const newObjects = replaceObject(
    objects,
    door,
    createDoor(position, level, !opened)
  );

  return activate(
    updatePlayerView({
      ...state,
      objects: newObjects,
      player: { ...player, action: undefined },
    })
  );
}

export default splitDoor;
