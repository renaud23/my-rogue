import { consumeMove } from "../commons";
import { getVisibles } from "../player";

export function goDown(state) {
  const { player, dungeon } = state;
  const { currentLevel } = player;
  const nextLevel = currentLevel - 1;
  const stairs = dungeon.getStairs(nextLevel);

  const nextPlayer = {
    ...player,
    currentLevel: nextLevel,
    position: stairs.up.position,
  };
  const visibles = getVisibles({ dungeon, player: nextPlayer });

  return {
    ...state,
    player: consumeMove({ ...nextPlayer, visibles, action: undefined }),
  };
}

export default [{ desc: "Descendre l'escalier", todo: goDown }];
