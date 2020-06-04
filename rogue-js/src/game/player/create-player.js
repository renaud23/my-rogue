import getVisibles from "./get-visibles";
import createInventory, { putObject } from "./inventory";
import { createKnife, createArmageddon } from "../objects";
import { createStats, computeNextLevelXp, computeMaxLife } from "../fight";

const DEFAULT_FOV = 8;
const DEFAULT_NB_MOVE = 2;

function createPlayer(dungeon, fov = DEFAULT_FOV, maxMove = DEFAULT_NB_MOVE) {
  const currentLevel = 0;
  const position = dungeon.peekEmptyTile(currentLevel);
  const stats = computeNextLevelXp(computeMaxLife(createStats(2, 1, 1, 1)));

  const player = {
    desc: "Fitz",
    position,
    fov,
    memory: [],
    action: null,
    inventory: null,
    weapon: null,
    currentLevel,
    stats: { ...stats, xp: 0, xpPoint: 0 },
    turn: {
      moveLeft: maxMove,
      turnPlay: 0,
      maxMove: maxMove,
    },
  };

  const inventory = createInventory(10);
  const knife = createKnife();
  const sword = createArmageddon();

  return {
    ...player,
    weapon: sword,
    visibles: getVisibles({ player, dungeon, ennemies: [], objects: [] }),
    inventory: putObject(inventory, knife),
  };
}

export default createPlayer;
