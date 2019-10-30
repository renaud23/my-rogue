import createGameRenderer from "./create-game-renderer";
import createMapRenderer from "./create-map-renderer";

export { default as createOffscreen, createTexture } from "./rendering";

export const createRenderer = params => {
  const gameRenderer = createGameRenderer(params);

  return game => {
    gameRenderer(game);

    return game;
  };
};
