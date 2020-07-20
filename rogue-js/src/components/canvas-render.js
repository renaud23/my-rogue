import React, { useRef, useEffect, useState, useCallback } from "react";
import { maxMin } from "../commons/tools";
import { tileClick } from "../game";
import createOffscreen, { createTexture } from "./render/rendering";
import {
  dungeonState,
  playerState,
  objectsState,
  ennemiesState,
  activateState,
  miscellaneousState,
  messagesState,
} from "../recoil";
import fillDungeon from "./tiles-render/fill-dungeon";
import fillPlayer from "./tiles-render/fill-player";
import fillHud from "./tiles-render/fill-hud";
import fillEnnemies from "./tiles-render/fill-ennemies";
import fillObjects from "./tiles-render/fill-objects";
import { useRecoilState } from "recoil";

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

function computeClickPos({ player, dungeon }, [cx, cy], rect) {
  const { currentLevel } = player;
  const dungeonWidth = dungeon.getWidth(currentLevel);
  const { x, y } = rect;
  return x + cx + +(y + cy) * dungeonWidth;
}

function CanvasRenderer({ viewSize, tileSize = 16, size = 19 * tileSize }) {
  const canvas = useRef(null);
  const [mousePos, setMousePos] = useState(undefined);
  const [offscreen, setOffscreen] = useState(null);
  const [texture, setTexture] = useState(null);
  const [rect, setRect] = useState(undefined);
  const [state, setState] = useState(undefined);

  const [dungeon, setDungeon] = useRecoilState(dungeonState);
  const [player, setPlayer] = useRecoilState(playerState);
  const [ennemies, setEnnemies] = useRecoilState(ennemiesState);
  const [objects, setObjects] = useRecoilState(objectsState);
  const [activate, setActivate] = useRecoilState(activateState);
  const [miscellaneous, setMiscellaneous] = useRecoilState(miscellaneousState);
  const [messages, setMessages] = useRecoilState(messagesState);

  useEffect(
    function () {
      if (canvas.current && size) {
        setTexture(createTexture(`${window.location.origin}/texture.png`));
        setOffscreen(createOffscreen(canvas.current, size, size));
      }
    },
    [canvas, size]
  );

  useEffect(
    function () {
      setState({ dungeon, player, ennemies, objects, miscellaneous, messages });
    },
    [dungeon, player, ennemies, objects, miscellaneous, messages]
  );

  const cally = useCallback(
    function (clickPos) {
      const position = computeClickPos(state, clickPos, rect);
      if (position) {
        const next = activate.cally(state, tileClick(position));
        setActivate({ cally: next.activate });
        setDungeon(next.dungeon);
        setPlayer(next.player);
        setObjects(next.objects);
        setEnnemies(next.ennemies);
        setMessages(next.messages);
        setMiscellaneous(next.miscellaneous);
      }
    },
    [
      state,
      rect,
      activate,
      setActivate,
      setDungeon,
      setEnnemies,
      setMessages,
      setMiscellaneous,
      setObjects,
      setPlayer,
    ]
  );

  useEffect(
    function () {
      if (state && state.dungeon) {
        setRect(computeRect(state, viewSize));
      }
    },
    [state, viewSize]
  );

  useEffect(
    function () {
      if (offscreen && texture && state.dungeon && rect) {
        offscreen.clear();
        render(state, offscreen, texture, rect, tileSize);
        if (mousePos) {
          const [x, y] = mousePos;

          offscreen.fillRect(
            "rgba(50,50,0,0.3)",
            x * tileSize,
            y * tileSize,
            tileSize,
            tileSize
          );
        }
        offscreen.render();
      }
    },
    [state, rect, offscreen, texture, tileSize, mousePos]
  );

  if (!size) return null;
  return (
    <>
      <canvas
        style={{ width: `${size}px`, height: `${size}px` }}
        className="game-canvas"
        onClick={function (e) {
          const x = Math.trunc((e.pageX - e.target.offsetLeft) / tileSize);
          const y = Math.trunc((e.pageY - e.target.offsetTop) / tileSize);
          cally([x, y]);
        }}
        onMouseMove={function (e) {
          const x = Math.trunc((e.pageX - e.target.offsetLeft) / tileSize);
          const y = Math.trunc((e.pageY - e.target.offsetTop) / tileSize);
          setMousePos([x, y]);
        }}
        onMouseOut={function (e) {
          setMousePos(undefined);
        }}
        ref={canvas}
        tabIndex="0"
      />
    </>
  );
}
//
export default CanvasRenderer;
