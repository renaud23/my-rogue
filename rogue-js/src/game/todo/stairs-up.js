import { getVisibles, consumeMove } from "../commons";

export function goUp(state) {
  const { player, dungeon } = state;
  const { currentLevel, moveLeft } = player;
  const nextLevel = currentLevel + 1;
  const stairs = dungeon.getStairs(nextLevel);

  const nextPlayer = {
    ...player,
    currentLevel: nextLevel,
    moveLeft: moveLeft - 1,
    position: stairs.down.position,
  };
  const visibles = getVisibles({ dungeon, player: nextPlayer });

  return {
    ...state,
    player: consumeMove({ ...nextPlayer, visibles, action: null }),
  };
}

export default [{ desc: "Monter l'escalier", todo: goUp }];
