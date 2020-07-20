export { default as getVisibles } from "./get-visibles";
export { default as movePlayer } from "./move-player";
export { default as createPlayer } from "./create-player";
export { default as updateMemory } from "./update-memory";
export { default as updateVisibles } from "./update-visibles";
export { default as updatePlayerView } from "./update-player-view";
export * from "./create-player";

export function isVisibleForPlayer(player, position) {
  const { visibles } = player;
  return visibles.indexOf(position) !== -1;
}

export function isInPlayerMemory(player, position) {
  const { memory, currentLevel } = player;
  if (memory && memory[currentLevel] && position in memory[currentLevel]) {
    return true;
  }

  return false;
}
