const tile = (value, color) => ({ value, color });

export const WALL = tile("X", "coral");
export const FLOOR = tile(".", "orange");
export const JOUEUR = tile("O", "yellow");
export const UNKNOW = tile("?", "darkgray");
