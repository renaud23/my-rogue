import activate from "./activate-player";
import { getEnnemiesAt, computeRangePositions } from "./commons";
import { shoot, buildPlayer } from "./activate-shoot";
import { aStarPath } from "./ennemies/path-finding";

function activateShoot(state, enemy, position) {
  const { player } = state;
  const { weapon, position: posPlayer } = player;
  const { range = 1 } = weapon;
  const posInRange = computeRangePositions(state, range, range > 1);
  if (posInRange.indexOf(position) !== -1) {
    const next = { ...state, player: buildPlayer({ player, position }) };
    return { ...shoot(next), activate };
  }
  return { ...state, activate };
}

function moveTo(state, target) {
  const { player } = state;
  const { position, visibles } = player;
  if (visibles.indexOf(position) !== -1) {
    const path = aStarPath(state)(position, target);
    if (path.length) {
      return activate({ ...state, player: { ...player, path } });
    }
  }
  return state;
}

function activateDirectPlay(state, event) {
  const { payload } = event;
  const { position } = payload;
  const { player } = state;
  const { currentLevel, weapon, position: posPlayer } = player;

  const [enemy] = getEnnemiesAt(state, currentLevel, position);
  if (enemy) {
    return activateShoot(state, enemy, position);
  }

  return moveTo({ ...state, activate }, position);
}

export default activateDirectPlay;
