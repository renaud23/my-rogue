import React from "react";
import { useRecoilState } from "recoil";
import RenderDungeon, { GlobalRender } from "./game-render";
import {
  dungeonState,
  playerState,
  activateState,
  ennemiesState,
} from "../recoil";
import { activate as cally } from "../game";
import Pad from "./pad";
import { createDungeon } from "../game/dungeon";
import { createPlayer } from "../game/player";
import ActionConsole from "./action-log";
import "./render-game.scss";

function initialize() {
  const dungeon = createDungeon(10, 20, 20);
  const player = createPlayer(dungeon, 8);

  return { dungeon, player, callback: cally };
}

function Game() {
  const [dungeon, setDungeon] = useRecoilState(dungeonState);
  const [player, setPlayer] = useRecoilState(playerState);
  const [activate, setActivate] = useRecoilState(activateState);
  const [ennemies, setEnnemies] = useRecoilState(ennemiesState);
  const { fov } = player;
  return (
    <>
      <Pad />
      <button
        onClick={function () {
          const { dungeon: dung, player, ennemies, callback } = initialize();
          setDungeon(dung);
          setPlayer(player);
          setEnnemies(ennemies);
          setActivate({ cally: callback });
        }}
      >
        renew
      </button>
      <div className="game">
        <RenderDungeon viewSize={fov + 1} />
        <ActionConsole />
      </div>
      <GlobalRender />
    </>
  );
}

export default Game;
