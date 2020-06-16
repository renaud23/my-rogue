import { isEmptyPosition, consumeMove } from "./commons";
import { updatePlayerView } from "./player";
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
        action: undefined,
        position: nextPos,
        path: restPath,
      });
      return activate(updatePlayerView({ ...state, player: nextPlayer }));
    }
  }

  return activate({
    ...state,
    player: { ...player, path: undefined },
    activate,
  });
}

export default autoPlay;
