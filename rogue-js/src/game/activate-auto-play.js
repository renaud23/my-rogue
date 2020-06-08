import { isEmptyPosition, consumeMove } from "./commons";
import { getVisibles, updateMemory } from "./player";
import activate from "./activate-player";

function autoPlay(state) {
  const { player } = state;
  const { currentLevel } = player;
  const { path } = player;
  if (path && path.length > 0) {
    const [nextPos, ...restPath] = path;
    if (isEmptyPosition(state, currentLevel, nextPos)) {
      const nextPlayer = consumeMove({
        ...player,
        position: nextPos,
        path: restPath,
      });
      const visibles = getVisibles({ ...state, player: nextPlayer });
      return activate({
        ...state,
        player: updateMemory({ ...state, player: { ...nextPlayer, visibles } }),
      });
    }
  }

  return activate({ ...state, player: { ...player, path: null }, activate });
}

export default autoPlay;
