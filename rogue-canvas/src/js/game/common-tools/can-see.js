import { getSegment } from "./get-segment";

/*
 *
 */
export const getEmptyTilesPosition = (game = {}) => {
  const { dungeon, player } = game;
  if (!dungeon || player) return [];
  return [...dungeon.emptyTiles].filter(p => p !== player.position);
};

export const canSee = game => (a, b) => {
  const { dungeon } = game;
  const { data, width, tiles } = dungeon;

  const [first, ...segment] = getSegment(
    { x: a % width, y: Math.trunc(a / width) },
    { x: b % width, y: Math.trunc(b / width) }
  );
  return segment.reduce(
    (a, v, i) =>
      a && (i === segment.length - 1 || data[v.x + v.y * width] !== tiles.rock),
    true
  );
};
