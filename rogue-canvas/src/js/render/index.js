import createGameRenderer from "./create-game-renderer";

export { default as createOffscreen, createTexture } from "./rendering";

export const createRenderer = params => {
  const gameRenderer = createGameRenderer(params);
  return game => (renderer, texture) => {
    renderer.clear();
    gameRenderer(game)(renderer, texture);
    renderer.render();
    return game;
  };
};
