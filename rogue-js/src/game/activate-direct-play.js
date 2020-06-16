import activate from "./activate-player";
import { getEnnemiesAt, getObjectsAt, computeRangePositions } from "./commons";
import { shoot, buildPlayer } from "./activate-shoot";
import { aStarPath } from "./ennemies/path-finding";
import { activateMenuAction } from "./activate-action";

/**
 * For the click events.
 * @param {*} state
 * @param {*} enemy
 * @param {*} position
 */
function activateShoot(state, enemy, position) {
  const { player } = state;
  const { weapon } = player;
  const { range = 1 } = weapon;
  const posInRange = computeRangePositions(state, range, range > 1);
  if (posInRange.indexOf(position) !== -1) {
    const next = { ...state, player: buildPlayer({ player, position }) };
    return { ...shoot(next), activate };
  }

  return moveTo({ ...state, activate }, enemy.position); //{ ...state, activate };
}

function activateObjects(state, objects, position) {
  const { player } = state;
  const posInRange = computeRangePositions(state, 1);
  if (posInRange.indexOf(position) !== -1) {
    return activateMenuAction({
      ...state,
      player: { ...player, action: { position } },
    });
  }
  return moveTo({ ...state, activate }, position);
}

function moveTo(state, target) {
  const { player } = state;
  const { position, visibles } = player;
  if (visibles.indexOf(target) !== -1) {
    const [_, ...path] = aStarPath(state)(position, target);
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
  const { currentLevel } = player;

  const [enemy] = getEnnemiesAt(state, currentLevel, position);
  if (enemy) {
    return activateShoot(state, enemy, position);
  }
  const objects = getObjectsAt(state, currentLevel, position);
  if (objects.length) {
    return activateObjects(state, objects, position);
  }

  return moveTo({ ...state, activate }, position);
}

export default activateDirectPlay;
