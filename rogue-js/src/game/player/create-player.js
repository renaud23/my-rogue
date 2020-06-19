import getVisibles from "./get-visibles";
import createInventory, { putObject } from "./inventory";
import { createKnife, createSword, createBow, createArrows } from "../objects";
import { popOne } from "../commons";
import { createStats, computeNextLevelXp, computeMaxLife } from "../fight";

const DEFAULT_FOV = 8;
const DEFAULT_NB_MOVE = 2;

function createBasePlayer(
  dungeon,
  empties,
  [s, a, l, e],
  [melee, distance, parade],
  fov = DEFAULT_FOV,
  maxMove = DEFAULT_NB_MOVE
) {
  const position = popOne(empties, 0);
  const stats = computeNextLevelXp(computeMaxLife(createStats(s, a, l, e)));

  return {
    desc: "Fitz",
    position,
    fov,
    memory: [],
    action: undefined,
    inventory: undefined,
    weapon: undefined,
    ammo: undefined,
    currentLevel: 0,
    stats: { ...stats, xp: 0, xpPoint: 0 },
    baseClass: { melee, distance, parade },
    turn: {
      moveLeft: maxMove,
      turnPlay: 0,
      maxMove: maxMove,
    },
  };
}

export function createWarrior(dungeon, empties) {
  const stats = [2, 1, 1, 1];
  const baseClass = [0.4, 0.2, 0.3]; // [melee|distance|parade]
  const player = createBasePlayer(dungeon, empties, stats, baseClass);

  const knife = createKnife();
  const sword = createSword();
  const inventory = createInventory(10, knife);

  return {
    ...player,
    weapon: sword,
    ammo: undefined,
    visibles: [],
    inventory,
  };
}

export function createArcher(dungeon, empties) {
  const stats = [1, 2, 1, 1];
  const baseClass = [0.2, 0.6, 0.3];
  const player = createBasePlayer(dungeon, empties, stats, baseClass);

  const knife = createKnife();
  const bow = createBow();
  const arrows = createArrows(8);
  const inventory = createInventory(10, knife);

  return {
    ...player,
    weapon: bow,
    ammo: arrows,
    visibles: [],
    inventory,
  };
}

export default createArcher;
