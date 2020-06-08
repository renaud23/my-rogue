import { getPositions } from "../../commons";

function canBite(state, enemy) {
  const { player } = state;
  const { position: pp } = player;
  const { position } = enemy;
  const vois = getPositions(state, position, 1);
  return vois.reduce(function (a, pos) {
    return a || pos === pp;
  }, false);
}

export default canBite;
