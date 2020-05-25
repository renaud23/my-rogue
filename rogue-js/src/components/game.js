import React from "react";
import { useRecoilState } from "recoil";
import RenderDungeon, { GlobalRender } from "./game-render";
import {
  dungeonState,
  playerState,
  activateState,
  ennemiesState,
  objectsState,
} from "../recoil";
import { createObjectDungeon } from "../game/objects";
import { activate as cally } from "../game";
import Pad from "./pad";
import { createDungeon } from "../game/dungeon";
import { createPlayer } from "../game/player";
import { createEnnemiesDungeon } from "../game/ennemies";
import ActionConsole from "./action-log";
import "./render-game.scss";

function initialize() {
  const dungeon = createDungeon(10, 20, 20);
  const player = createPlayer(dungeon, 8);
  const objects = createObjectDungeon({ dungeon });
  const ennemies = createEnnemiesDungeon({ dungeon });

  return { dungeon, player, objects, ennemies, callback: cally };
}

function Game() {
  const setDungeon = useRecoilState(dungeonState)[1];
  const [player, setPlayer] = useRecoilState(playerState);
  const setActivate = useRecoilState(activateState)[1];
  const setEnnemies = useRecoilState(ennemiesState)[1];
  const setObjects = useRecoilState(objectsState)[1];
  const { fov } = player;
  return (
    <>
      <Pad />
      <button
        onClick={function () {
          const {
            dungeon: dung,
            player,
            ennemies,
            objects,
            callback,
          } = initialize();
          setDungeon(dung);
          setPlayer(player);
          setEnnemies(ennemies);
          setObjects(objects);
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
