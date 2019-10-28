import { createRenderer, render, createTexture } from "../render";
import { createGame, activate } from "./game";

export default (canvas, width, height) => {
  const renderer = createRenderer(canvas);
  const texture = createTexture(`${window.location.origin}/texture.png`);

  let game = createGame();
  setInterval(() => {
    game = render(activate(game))(renderer, texture);
  }, 100);
};
