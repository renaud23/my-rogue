import { TILES } from "../../commons";
import { TYPE_OBJECT } from "../objects";
import { getObjectsAt } from "../objects/dungeon-objects";

function notObstructByObject(state, level, position) {
  const { objects } = state;
  return getObjectsAt(objects, level, position).reduce(function (a, o) {
    const { type } = o;
    switch (type) {
      case TYPE_OBJECT.door:
        return a && o.opened;
      default:
        return a;
    }
  }, true);
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
    notObstructByObject(state, level, position)
  );
}

export default isVisible;
