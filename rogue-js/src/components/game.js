import React, { useEffect } from "react";
import { useRecoilState } from "recoil";
import { isTurnFinish } from "../game/commons";
import { padEvent } from "../game";
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
  const dungeon = createDungeon(10, 40, 40);
  const player = createPlayer(dungeon, 8);
  const objects = createObjectDungeon({ dungeon });
  const ennemies = createEnnemiesDungeon({ dungeon });

  return { dungeon, player, objects, ennemies, callback: cally };
}

function Game() {
  const [dungeon, setDungeon] = useRecoilState(dungeonState);
  const [player, setPlayer] = useRecoilState(playerState);
  const [activate, setActivate] = useRecoilState(activateState);
  const [ennemies, setEnnemies] = useRecoilState(ennemiesState);
  const [objects, setObjects] = useRecoilState(objectsState);
  const { fov } = player;

  useEffect(
    function () {
      if (dungeon && isTurnFinish(player)) {
        const what = activate.cally(
          { dungeon, player, objects, ennemies },
          padEvent(null)
        );
        setActivate({ cally: what.activate });
        setDungeon(what.dungeon);
        setPlayer(what.player);
        setObjects(what.objects);
        setEnnemies(what.ennemies);
      }
    },
    [
      player,
      setActivate,
      dungeon,
      objects,
      activate,
      ennemies,
      setDungeon,
      setObjects,
      setPlayer,
      setEnnemies,
    ]
  );

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
