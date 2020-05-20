import { peekOne } from "../commons";
import getVisibles from "./get-visibles";

export { default as getVisibles } from "./get-visibles";
export { default as movePlayer } from "./move-player";

const DEFAULT_FOV = 5;

export function createPlayer(dungeon, fov = DEFAULT_FOV) {
  const sac = [...dungeon.emptyTiles];
  const position = peekOne(sac);
  const player = { position, fov, action: null };
  return { ...player, visibles: getVisibles({ player, dungeon }) };
}
