export * from "./is-empty-position";
export * from "./chrono";
export * from "./maths";
export * from "./get-segment";
export * from "./can-see";
export { default as Queue } from "./priority-queue";

export const posToCoord = width => pos => ({
  x: pos % width,
  y: Math.trunc(pos / width)
});
