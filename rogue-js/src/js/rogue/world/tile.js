//String.fromCharCode(0x263A)

const tile = (value, color, render) => ({ value, color, render });

export const WALL = tile(String.fromCharCode(0x2589), "gray", String.fromCharCode(0x2589));
export const FLOOR = tile(".", "green", ".");
export const JOUEUR = tile(String.fromCharCode(0x2639), "yellow", String.fromCharCode(0x2639));
export const UNKNOW = tile("?", "darkgray");
export const AIM = tile("X", "yellow", "X");
export const BODY = tile("@", "blood", "@");
export const BODY_PART = tile("@", "blood", "~");

export const WOLF = tile("W", "brown", "W");

export const isWalkable = tile => {
  return tile.value === BODY.value || tile.value === FLOOR.value;
};

export const isWall = tile => {
  return tile.value === WALL.value;
};

//0x2764 coeur
// 0x2a2d bow
