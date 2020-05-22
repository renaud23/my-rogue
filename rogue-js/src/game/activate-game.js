import { isTurnFinish, nextTurn } from "./player";

function activate(state) {
  // console.log("the game is playing...");
  return state;
}

function playTurn(state) {
  const { player } = state;
  if (isTurnFinish(player)) {
    return activate({ ...state, player: nextTurn(player) });
  }
  return state;
}

export default playTurn;
