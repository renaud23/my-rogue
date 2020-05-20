import React from "react";
import { useRecoilState } from "recoil";
import RenderDungeon, { GlobalRender } from "./render-game";
import { dungeonState, playerState, activateState } from "../recoil";
import { activate as cally } from "../game";
import Pad from "./pad";
import { createCave } from "../dungeon";
import { createPlayer } from "../player";
import "./render-game.scss";

function initialize() {
  const dungeon = createCave(60, 30);
  const player = createPlayer(dungeon);

  return { dungeon, player, callback: cally };
}

function Game() {
  const [dungeon, setDungeon] = useRecoilState(dungeonState);
  const [player, setPlayer] = useRecoilState(playerState);
  const [activate, setActivate] = useRecoilState(activateState);
  const { fov } = player;
  return (
    <>
      <Pad />
      <button
        onClick={function () {
          const { dungeon: dung, player, callback } = initialize();

          setDungeon(dung);
          setPlayer(player);
          setActivate({ cally: callback });
        }}
      >
        renew
      </button>

      <RenderDungeon viewSize={fov + 1} />
      <GlobalRender />
    </>
  );
}

export default Game;
