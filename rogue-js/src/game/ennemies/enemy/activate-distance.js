import { aStarPath } from "../path-finding";
import canSeePlayer from "../commons/can-see-player";
import canShoot from "../commons/can-shoot";
import attack from "../commons/attack";

function computePath(state, enemy) {
  const { player } = state;
  const { position: playerPos } = player;
  const { position: enemyPos } = enemy;
  const [_, position, path] = aStarPath(state)(enemyPos, playerPos);
  return [
    state,
    { ...enemy, position, path: path && path.length ? path : undefined },
  ];
}

function consumePath(state, enemy) {
  const { player } = state;
  const { path } = player;
  const [position, rest] = path;

  return [state, { ...enemy, position, path: rest.length ? rest : undefined }];
}

function isPath(enemy) {
  return enemy.path;
}

function activate(state, enemy) {
  if (canSeePlayer(state, enemy)) {
    const { player } = state;
    const { position: playerPos } = player;
    const { position: enemyPos, path } = enemy;
    if (canShoot(state, enemy)) {
      const [nextState, nextEnemy] = attack(state, enemy);
      return [nextState, { ...nextEnemy, path: undefined }];
    }
    if (isPath(enemy)) {
      return consumePath(state, enemy);
    }
    return computePath(state, enemy);
  }
  if (isPath(enemy)) {
    return consumePath(state, enemy);
  }
  return [state, enemy];
}

export default activate;
