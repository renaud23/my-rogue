import { createRenderer, render, createTexture } from "../render";
import { createGame, activate } from "./game";

export default (canvas, width, height) => {
  const renderer = createRenderer(canvas, width, height);
  const texture = createTexture(`${window.location.origin}/texture.png`);

  let game = createGame();
  const loop = () => {
    game = render(activate(game))(renderer, texture);
  };
  setInterval(loop, 100);

  return () => {
    clearInterval(loop);
  };
};
