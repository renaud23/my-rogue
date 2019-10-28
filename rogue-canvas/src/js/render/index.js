export { default as createRenderer, createTexture } from "./rendering";

export const render = game => (renderer, texture) => {
  renderer.clear();
  //
  renderer.drawTexture(texture, 0, 0, 32, 32, 0, 0, 32, 32);
  renderer.render();
  return game;
};
