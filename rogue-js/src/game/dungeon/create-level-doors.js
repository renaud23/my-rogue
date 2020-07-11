import { createDoor } from "../objects";

function createDoorsRoom(room, level, locked = false) {
  const { doors: doorsPos } = room;

  return doorsPos.reduce(function (a, pos) {
    const [door, key] = createDoor(pos, level, locked);
    if (locked) {
      return [...a, door, key];
    }

    return [...a, door];
  }, []);
}

function create(dungeon, level) {
  const { rooms, regions } = dungeon;
  const { start } = regions;
  const { zones } = regions;
  const levelDoors = zones.reduce(function (a, zone) {
    const { roomIndex } = zone;
    if (roomIndex >= 0) {
      return [...a, ...createDoorsRoom(rooms[roomIndex], level)];
    }

    return a;
  }, []);

  return levelDoors;
}

export default create;
