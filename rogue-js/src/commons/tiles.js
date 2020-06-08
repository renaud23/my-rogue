import * as todo from "../game/todo";

export const TILES = {
  ground: { code: 0, char: "_", className: "tile-ground", desc: "le sol" },
  rock: {
    code: 1,
    char: "X",
    className: "tile-rock",
    desc: "un mur de pierre",
  },
  simpleObject: { code: 2, char: ".", color: "green" },
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
  ennemy: {
    code: 100,
    char: "@",
  },
  corpse: { code: 101, char: "~", color: "DarkRed" },
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

  ammo: { code: 400, char: "|", color: "cyan" },

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
