import dungeonRenderer from "./console-memory-rendering";

export { default as createRenderer, createTexture } from "./rendering";

export const render = game => (renderer, texture) => {
  const { dungeon } = game;
  renderer.clear();
  dungeonRenderer(game)(renderer, texture);
  // renderer.drawTexture(texture, 0, 0, 32, 32, 0, 0, 32, 16);
  renderer.render();
  return game;
};
