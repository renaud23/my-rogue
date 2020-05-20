export const PAD_BUTTON = {
  up: "pad/up",
  down: "pad/down",
  left: "pad/left",
  right: "pad/right",
  buttonX: "pad/button-x",
  buttonB: "pad/button-b",
  buttonA: "pad/button-a",
  buttonY: "pad/button-y",
};

export const TILES = {
  player: { code: 10, char: "O", className: "tile-player" },
  ground: { code: 0, char: "_", className: "tile-ground" },
  rock: { code: 1, char: "X", className: "tile-rock" },
  unknow: { code: -1, char: "?", className: "tile-unknow" },
};

export const CODE_TO_TILE = Object.entries(TILES).reduce(function (
  a,
  [key, { code, ...rest }]
) {
  return { ...a, [code]: { key, ...rest } };
},
{});

export const DIRECTION = {
  NORTH: "direction/north",
  SOUTH: "direction/south",
  EAST: "direction/east",
  WEST: "direction/west",
};

export const PLAYER_ACTIONS = {
  help: "player-action/help",
};

export function getTile(code) {
  return code in CODE_TO_TILE
    ? CODE_TO_TILE[code]
    : { className: "tile-nop-:(", char: "." };
}
