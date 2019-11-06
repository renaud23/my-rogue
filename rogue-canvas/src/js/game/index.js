import { createRenderer } from "../render";
import { createGame, activate } from "./game";

const params = { fov: 4 };
const map = {
  size: 4,
  width: 30,
  height: 30
};

export default (canvas, width, height, canvasMap) => {
  let game = createGame(params);
  const renderer = createRenderer({
    ...params,
    canvas,
    canvasMap,
    screenWidth: width,
    screenHeight: height,
    marge: 2,
    map
  });
  const loop = () => {
    game = renderer(activate(game));
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
