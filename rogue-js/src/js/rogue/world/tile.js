//String.fromCharCode(0x263A)

const tile = (value, color) => ({ value, color });

export const WALL = tile(String.fromCharCode(0x2589), "gray");
export const FLOOR = tile(".", "green");
export const JOUEUR = tile(String.fromCharCode(0x2639), "yellow");
export const UNKNOW = tile("?", "darkgray");

export const WOLF = tile("W", "brown");

//0x2764 coeur
// 0x2a2d bow
