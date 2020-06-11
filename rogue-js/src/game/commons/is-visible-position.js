import { TILES } from "../../commons";
import { TYPE_OBJECT } from "../objects";
import { getObjectsAt } from "./get-at-position";

function obstructByObject(state, level, position) {
  return getObjectsAt(state, level, position).reduce(function (a, o) {
    const { type } = o;
    switch (type) {
      case TYPE_OBJECT.door:
        return !o.opened;
      default:
        return a;
    }
  }, false);
}

function obstructByDungeon(state, level, position) {
  const { dungeon } = state;

  const data = dungeon.getData(level);
  switch (data[position]) {
    case TILES.ground.code:
    case TILES.stairsDown.code:
      return false;

    default:
      return true;
  }
}

function isVisible(state, level, position) {
  return (
    !obstructByDungeon(state, level, position) &&
    !obstructByObject(state, level, position)
  );
}

export default isVisible;
