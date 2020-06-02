import {
  isEmptyPosition,
  isTurnFinish,
  consumeMove,
  nextTurn,
} from "./commons";
import { getVisibles, updateMemory } from "./player"; // getVisibles(state)
import activateGame from "./activate-game";
import activate from "./activate-player";

function moveNext(state) {
  const { player } = state;
  const { currentLevel } = player;
  const { path } = player;

  if (path.length > 0) {
    const [nextPos, ...restPath] = path;
    if (isEmptyPosition(state, currentLevel, nextPos)) {
      const next = {
        ...state,
        player: consumeMove({ ...player, position: nextPos, path: restPath }),
      };
      const visibles = getVisibles(next);
      const { player: nextPlayer } = next;
      const finalPlayer = updateMemory({
        ...next,
        player: { ...nextPlayer, visibles },
      });
      return autoPlay({
        ...next,
        player: { ...finalPlayer },
        activate,
      });
    }
  }

  return { ...state, player: { ...player, path: undefined }, activate };
}

function autoPlay(state) {
  const { player } = state;
  if (!isTurnFinish(player)) {
    return moveNext(state);
  }

  const [nextState, endTurn] = activateGame(state);
  //   // TODO check status player : dead ?
  if (endTurn) {
    // TODO activate other things if necessary.
    const np = nextTurn(player);
    return autoPlay({
      ...nextState,
      //   ...appendMessages(nextState, fillMessage(PATTERNS.nextTurn, np)),
      player: { ...np },
      activate: autoPlay,
    });
  }
  //   const { activate: nextActivate = autoPlay } = nextState;
  return autoPlay(nextState);
}

export default autoPlay;
