import { createRenderer, render, createTexture } from "../render";
import { createGame, activate } from "./game";

export default (canvas, width, height) => {
  const renderer = createRenderer(canvas, width, height);
  const texture = createTexture(`${window.location.origin}/texture.png`);

  let game = createGame();
  const loop = () => {
    game = render(activate(game))(renderer, texture);
  };
  const keyDownListener = e => {
    e.stopImmediatePropagation();
    e.preventDefault();
    if (
      e.key === "ArrowDown" ||
      e.key === "ArrowUp" ||
      e.key === "ArrowLeft" ||
      e.key === "ArrowRight"
    ) {
      game.action = e.key;
    }
  };
  const keyUpListener = e => {
    e.stopImmediatePropagation();
    e.preventDefault();
    game.action = undefined;
  };
  canvas.focus();
  canvas.addEventListener("keydown", keyDownListener);
  canvas.addEventListener("keyup", keyUpListener);
  setInterval(loop, 50);

  return () => {
    clearInterval(loop);
    window.removeEventListener("keydown", keyDownListener);
    window.removeEventListener("keyup", keyUpListener);
  };
};
