import getVisibles from "./get-visibles";
import createInventory, { putObject } from "./inventory";
import { createKnife, createSword } from "../objects";
import { createStats } from "../fight";

const DEFAULT_FOV = 8;
const DEFAULT_NB_MOVE = 2;

function createPlayer(dungeon, fov = DEFAULT_FOV, maxMove = DEFAULT_NB_MOVE) {
  const currentLevel = 0;
  const position = dungeon.peekEmptyTile(currentLevel);
  const player = {
    desc: "Fitz",
    position,
    fov,
    memory: [],
    action: null,
    inventory: null,
    weapon: null,
    currentLevel,
    stats: { ...createStats(2, 1, 1, 1), level: 1, life: 100 },
    turn: {
      moveLeft: maxMove,
      turnPlay: 0,
      maxMove: maxMove,
    },
  };

  const inventory = createInventory(10);
  const knife = createKnife();
  const sword = createSword();

  return {
    ...player,
    weapon: sword,
    visibles: getVisibles({ player, dungeon, ennemies: [], objects: [] }),
    inventory: putObject(inventory, knife),
  };
}

export default createPlayer;
