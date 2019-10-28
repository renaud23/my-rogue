// import { isAtPos } from "../ennemy";

const isEnnemyAtPosition = ({ position }) => pos => pos === position;

export const isEmptyPosition = game => pos => {
  const { dungeon, ennemies = [] } = game;
  return (
    dungeon.data[pos] === dungeon.tiles.empty &&
    ennemies.reduce((a, e) => a && !isEnnemyAtPosition(e)(pos), true)
  );
};
