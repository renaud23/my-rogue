import { createDoor, generateDoorKind, createKey } from "../objects";
import { randomInt } from "../../commons";

function createDoorsRoom(room, level, kind, locked) {
  const { doors: doorsPos } = room;
  return doorsPos.reduce(function (doors, pos) {
    const door = createDoor(pos, level, kind, locked);
    if (locked) {
      return [...doors, door];
    }

    return [...doors, door];
  }, []);
}

function createKeys(doorsMap, dungeon, level) {
  const { regions } = dungeon;
  const { zones } = regions;
  return Object.entries(doorsMap).reduce(function (a, [zoneIndex, doors]) {
    let zone = zones[0];
    if (zoneIndex > 2) {
      zone = zones[zoneIndex - 1];
    }
    const { positions } = zone;
    return [
      ...a,
      createKey(positions[randomInt(positions.length - 1)], level, doors),
    ];
    return a;
  }, []);
}

function create(dungeon, level) {
  const { rooms, regions } = dungeon;
  const { zones } = regions;
  const doorsMap = {};
  const levelDoors = zones.reduce(function (a, zone, zoneIndex) {
    const { roomIndex } = zone;
    if (zoneIndex === 0 && roomIndex >= 0) {
      const kind = generateDoorKind(0);
      const doors = createDoorsRoom(rooms[roomIndex], level, kind, false);
      return [...a, ...doors];
    }
    if (zoneIndex > 1 && roomIndex >= 0) {
      const kind = generateDoorKind(zoneIndex - 1);
      const doors = createDoorsRoom(rooms[roomIndex], level, kind, true);
      doorsMap[zoneIndex] = doors;

      return [...a, ...doors];
    }

    return a;
  }, []);
  const keys = createKeys(doorsMap, dungeon, level);
  return [levelDoors, keys];
}

export default create;
