export { default as navigateOptions } from "./navigate-options";
export { default as navigateMap, computeRangePositions } from "./navigate-map";
export { getEnnemiesAt } from "./get-at-position";
export { default as cleanPlayerAction } from "./clean-player-action";
export { default as isEmptyPosition } from "./is-empty-position";
export { default as isVisiblePosition } from "./is-visible-position";
export { default as getPositions } from "./get-positions-around";
export { peekOne, popOne } from "./empty-tiles-tools";
export * from "./message-parser";
export * from "./turn-play";
export * from "./dices";
export * from "./navigate-map";

export function computeDesc(o) {
  const { desc } = o;
  return typeof desc === "function" ? desc(o) : desc;
}
