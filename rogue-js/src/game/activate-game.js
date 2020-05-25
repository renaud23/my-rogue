import { isTurnFinish, nextTurn } from "./player";

function activateEnnemies(state) {
  const { ennemies, player } = state;
  const { currentLevel } = player;
  const next = ennemies[currentLevel].reduce(function (ns, ennemy) {
    return ennemy.activate(ns, ennemy);
  }, state);

  return next;
}

function activate(state) {
  console.log("the game is playing...");

  return activateEnnemies(state);
}

function playTurn(state) {
  const { player } = state;
  if (isTurnFinish(player)) {
    return activate({ ...state, player: nextTurn(player) });
  }
  return state;
}

export default playTurn;
