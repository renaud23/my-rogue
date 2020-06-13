import { isTurnFinish, nextTurn, consumeMove } from "./commons";

function refillEnemy(ennemies, enemy, index) {
  return ennemies.map(function (level, i) {
    if (i === index) {
      return level.map(function (e) {
        if (e.id === enemy.id) {
          return enemy;
        }
        return e;
      });
    }
    return level;
  });
}

function nextTurnLevel(ennemies, index) {
  return ennemies.map(function (level, i) {
    if (i === index) {
      return level.map(function (e) {
        return nextTurn(e);
      });
    }
    return level;
  });
}

function activateEnnemies(state) {
  const { ennemies, player } = state;
  const { currentLevel } = player;
  const level = ennemies[currentLevel];
  const nextEnemyToPlay = level.find(function (e) {
    return !isTurnFinish(e);
  });

  if (nextEnemyToPlay) {
    const [newState, newEnemy] = nextEnemyToPlay.activate(
      state,
      consumeMove(nextEnemyToPlay)
    );
    const newEnnemies = refillEnemy(ennemies, newEnemy, currentLevel);
    return [newState, newEnnemies, false];
  }

  return [state, nextTurnLevel(ennemies, currentLevel), true];
}

function activateGame(state) {
  const [nextState, nextEnnemies, end] = activateEnnemies(state);
  return [{ ...nextState, ennemies: nextEnnemies }, end];
}

export default activateGame;
