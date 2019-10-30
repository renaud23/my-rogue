import { createOffscreen, createRenderer, createTexture } from "../render";
import { createGame, activate } from "./game";

const params = { fov: 4 };

export default (canvas, width, height) => {
  const offscreen = createOffscreen(canvas, width, height);
  const texture = createTexture(`${window.location.origin}/texture.png`);

  let game = createGame(params);
  const renderer = createRenderer({
    ...params,
    screenWidth: width,
    screenHeight: height,
    marge: 2
  });
  const loop = () => {
    game = renderer(activate(game))(offscreen, texture);
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
