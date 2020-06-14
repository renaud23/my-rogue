import { aStarPath } from "../path-finding";
import isEmptyPosition from "../../commons/is-empty-position";

export function isPath(enemy) {
  return enemy.path;
}

export function computePath(state, enemy) {
  const { player } = state;
  const { position: playerPos } = player;
  const { position: enemyPos } = enemy;
  const [_, position, ...path] = aStarPath(state)(enemyPos, playerPos);
  return [
    state,
    { ...enemy, position, path: path && path.length ? path : undefined },
  ];
}

export function consumePath(state, enemy) {
  const { path, level } = enemy;
  const [position, ...rest] = path;
  if (isEmptyPosition(state, level, position)) {
    return [
      state,
      { ...enemy, position, path: rest.length ? rest : undefined },
    ];
  }
  return [state, { ...enemy, path: undefined }];
}
