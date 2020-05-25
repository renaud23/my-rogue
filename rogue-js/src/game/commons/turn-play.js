export function buildTurnPlay(maxMove) {
  return { turnPlay: 0, moveLeft: maxMove, maxMove };
}

export function nextTurn(player) {
  const { turn } = player;
  const { turnPlay, maxMove } = turn;
  return {
    ...player,
    turn: { ...turn, moveLeft: maxMove, turnPlay: turnPlay + 1 },
  };
}

export function consumeMove(player) {
  const { turn } = player;
  const { moveLeft } = turn;
  return { ...player, turn: { ...turn, moveLeft: moveLeft - 1 } };
}

export function isTurnFinish(player) {
  const { turn } = player;
  const { moveLeft } = turn;
  return moveLeft <= 0;
}
