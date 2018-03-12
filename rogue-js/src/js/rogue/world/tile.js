const tile = (value, color) => ({ value, color });

export const WALL = tile("X", "gray");
export const FLOOR = tile(".", "green");
export const JOUEUR = tile("O", "yellow");
export const UNKNOW = tile("?", "green");
