import carveRooms from "./carve-rooms";
import carveDoors from "./carve-doors";
import carveCorridors from "./carve-corridors";
import { TILES } from "./common";
import refillCorridors from "./refill-corridors";

function createDungeon(width, height) {
  const { data, rooms } = carveRooms(
    new Array(width * height).fill(TILES.WALL),
    width,
    height
  );

  const { data: withMaze } = carveCorridors(data, width, height);
  const { data: withDoors, doors } = carveDoors(rooms, withMaze, width, height);
  const { data: refilled } = refillCorridors(rooms, withDoors, width, height);

  return { width, height, data: refilled, doors, rooms };
}

export default createDungeon;
