import { createCave } from "./dungeon";

export const activate = game => {
  return { ...game };
};

export const createGame = () => {
  const dungeon = createCave(60, 60);
  return {
    elements: [],
    dungeon
  };
};
