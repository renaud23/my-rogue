import React, { useRef, useEffect, useState } from "react";
import { maxMin } from "../commons/tools";
import getCoords from "./tiles-render/texture-mapping";
import createOffscreen, { createTexture } from "./render/rendering";
import combine from "./combine-fill";
import {
  dungeonState,
  playerState,
  objectsState,
  messagesState,
  ennemiesState,
  activateState,
  miscellaneousState,
} from "../recoil";
import fillDungeon from "./tiles-render/fill-dungeon";
import fillPlayer from "./tiles-render/fill-player";
import fillHud from "./tiles-render/fill-hud";
import fillEnnemies from "./tiles-render/fill-ennemies";
import fillObjects from "./tiles-render/fill-objects";
import { useRecoilState, useRecoilCallback, useSetRecoilState } from "recoil";

const render = [
  fillDungeon,
  fillObjects,
  fillEnnemies,
  fillPlayer,
  fillHud,
].reduce(
  function (a, next) {
    return (...args) => {
      a(...args);
      next(...args);
    };
  },
  () => null
);

function computeRect({ player, dungeon }, viewSize) {
  const { position, currentLevel, fov } = player;
  const dungeonWidth = dungeon.getWidth(currentLevel);
  const dungeonHeight = dungeon.getHeight(currentLevel);
  const width = Math.max(viewSize, fov) * 2 + 1;
  const dungX = position % dungeonWidth;
  const dungY = Math.trunc(position / dungeonWidth);
  const x = maxMin(dungX - viewSize, 0, dungeonWidth - width);
  const y = maxMin(dungY - viewSize, 0, dungeonHeight - width);

  return {
    x,
    y,
    width,
    height: width,
  };
}

function CanvasRenderer({ viewSize, width = 17 * 16, height = 17 * 16 }) {
  const canvas = useRef(null);
  const [offscreen, setOffscreen] = useState(null);
  const [texture, setTexture] = useState(null);

  const [dungeon] = useRecoilState(dungeonState);
  const [player] = useRecoilState(playerState);
  const [ennemies] = useRecoilState(ennemiesState);
  const [objects] = useRecoilState(objectsState);

  useEffect(
    function () {
      if (canvas.current) {
        setTexture(createTexture(`${window.location.origin}/texture.png`));
        setOffscreen(createOffscreen(canvas.current, width, height));
      }
    },
    [canvas.current]
  );

  useEffect(
    function () {
      if (offscreen && texture && dungeon) {
        offscreen.clear();
        const state = { dungeon, player, ennemies, objects };
        render(state, offscreen, texture, computeRect(state, viewSize), 16);
        offscreen.render();
      }
    },
    [dungeon, player]
  );

  return (
    <>
      <canvas
        style={{ width: `${width}px`, height: `${height}px` }}
        className="game-canvas"
        ref={canvas}
        tabIndex="0"
      />
    </>
  );
}
//
export default CanvasRenderer;
