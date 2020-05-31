import { consumeMove } from "../commons";
import { getVisibles } from "../player";

export function goUp(state) {
  const { player, dungeon } = state;
  const { currentLevel } = player;
  const nextLevel = currentLevel + 1;
  const stairs = dungeon.getStairs(nextLevel);

  const nextPlayer = {
    ...player,
    currentLevel: nextLevel,
    position: stairs.down.position,
  };
  const visibles = getVisibles({ dungeon, player: nextPlayer });

  return {
    ...state,
    player: consumeMove({ ...nextPlayer, visibles, action: null }),
  };
}

export default [{ desc: "Monter l'escalier", todo: goUp }];
