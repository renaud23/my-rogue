import dungeonRenderer from "./console-memory-rendering";

export { default as createRenderer, createTexture } from "./rendering";

export const render = game => (renderer, texture) => {
  renderer.clear();
  dungeonRenderer(10, 10)(game)(renderer, texture);
  renderer.render();
  return game;
};
