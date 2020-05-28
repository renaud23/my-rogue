import { isTurnFinish, nextTurn, consumeMove } from "./commons";

function refillEnnemy(state, ennemy) {
  const { ennemies, player } = state;
  const { currentLevel } = player;
  const ne = ennemies.map(function (level, i) {
    if (i === currentLevel) {
      return level.map(function (e) {
        if (e.id === ennemy.id) {
          return ennemy;
        }
        return e;
      });
    }
    return level;
  });

  return { ...state, ennemies: ne };
}

function activateLevel(state, level) {
  return level.reduce(
    function ([currState, currLevel, currEnd], ennemy) {
      const [newState, newEnnemy] = ennemy.activate(
        currState,
        consumeMove(ennemy)
      );
      if (isTurnFinish(newEnnemy)) {
        return [
          refillEnnemy(newState, newEnnemy),
          [...currLevel, nextTurn(newEnnemy)],
          true,
        ];
      }
      return [
        refillEnnemy(newState, newEnnemy),
        [...currLevel, newEnnemy],
        false,
      ];
    },
    [state, [], true]
  );
}

function activateEnnemies(state) {
  const { ennemies, player } = state;
  const { currentLevel } = player;

  const [nextState, nextEnnemies, end] = ennemies.reduce(
    function ([currState, levels, currEnd], level, i) {
      if (i === currentLevel) {
        const [ns, nextLevel, nextEnd] = activateLevel(currState, level);
        return [ns, [...levels, nextLevel], currEnd && nextEnd];
      }
      return [currState, [...levels, level], currEnd];
    },
    [state, [], true]
  );
  return [nextState, nextEnnemies, end];
}

function activateGame(state) {
  const [nextState, nextEnnemies, end] = activateEnnemies(state);
  return [{ ...nextState, ennemies: nextEnnemies }, end];
}

export default activateGame;
