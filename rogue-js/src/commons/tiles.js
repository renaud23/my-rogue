import * as todo from "../game/todo";

export const TILES = {
  ground: { code: 0, char: ".", className: "tile-ground", desc: "le sol" },
  rock: {
    code: 1,
    char: "█",
    className: "tile-rock",
    desc: "un mur de pierre",
  },
  simpleObject: { code: 2, char: "-", color: "green" },
  player: { code: 10, char: "O", className: "tile-player", desc: "vous" },
  stairsUp: {
    code: 33,
    char: ">",
    color: "yellow",
    desc: "un escalier montant",
    todo: todo.stairsUpTodo,
  },
  stairsDown: {
    code: 34,
    char: "<",
    desc: "un escalier descendant",
    color: "yellow",
    todo: todo.stairsDownTodo,
  },
  stairsUpDown: {
    code: 35,
    char: "q",
    desc: "un  escalier montant et descendant",
  },
  enemy: {
    code: 100,
    char: "@",
    color: "magenta",
  },
  rat: {
    code: 101,
    char: "r",
    color: "brown",
  },
  wolf: {
    code: 101,
    char: "w",
    color: "brown",
  },
  bowman: {
    code: 102,
    char: "D",
    color: "blue",
  },
  corpse: { code: 101, char: "~", color: "#af111c" },
  chest: {
    code: 200,
    char: "#",
    color: "cyan",
  },
  key: {
    code: 201,
    char: "⚷",
    color: "cyan",
  },
  doorClosed: { code: 202, char: "X", color: "brown" },
  doorOpened: { code: 203, char: "L", color: "brown" },

  ammo: { code: 400, char: "|", color: "cyan" },
  potion: { code: 401, char: "⚱", color: "chartreuse" },

  ironSight: { code: 666, char: "$" },
  helpSight: { code: 667, char: "?", color: "chartreuse" },
  path: { code: 668, char: ".", color: "chartreuse" },
  unknown: {
    code: -1,
    char: "?",
    className: "tile-unknow",
    desc: "rien de visible",
  },
};

export const CODE_TO_TILE = Object.entries(TILES).reduce(function (
  a,
  [key, { code, ...rest }]
) {
  return { ...a, [code]: { key, ...rest } };
},
{});

export function getTile(code) {
  return code in CODE_TO_TILE
    ? CODE_TO_TILE[code]
    : { className: "tile-nop-:(", char: "." };
}
