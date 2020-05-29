import React, { useEffect } from "react";
import { useRecoilState } from "recoil";
import { isTurnFinish } from "../game/commons";
import { padEvent } from "../game";
import RenderDungeon2 from "./game-render-2";
import {
  dungeonState,
  playerState,
  activateState,
  ennemiesState,
  objectsState,
  messagesState,
} from "../recoil";
import { createObjectDungeon } from "../game/objects";
import { activate as cally } from "../game";
import Pad from "./pad";
import { createDungeon } from "../game/dungeon";
import { createPlayer } from "../game/player";
import { createEnnemiesDungeon } from "../game/ennemies";
import ActionConsole from "./action-log";
import ConsoleLog from "./console-log";
import "./render-game.scss";

function initialize() {
  const dungeon = createDungeon(10, 40, 40);
  const player = createPlayer(dungeon, 8);
  const objects = createObjectDungeon({ dungeon });
  const ennemies = createEnnemiesDungeon({ dungeon });
  const messages = [
    "Un cri déchire la nuit. Son echo sinistre vous plonge dans la torpeur. (tu flippes comme une tarlouze)",
  ];

  return { dungeon, player, objects, ennemies, messages, callback: cally };
}

function Game() {
  const [dungeon, setDungeon] = useRecoilState(dungeonState);
  const [player, setPlayer] = useRecoilState(playerState);
  const [activate, setActivate] = useRecoilState(activateState);
  const [ennemies, setEnnemies] = useRecoilState(ennemiesState);
  const [objects, setObjects] = useRecoilState(objectsState);
  const [messages, setMessages] = useRecoilState(messagesState);
  const { fov } = player;

  // useEffect(
  //   function () {
  //     if (dungeon && isTurnFinish(player)) {
  //       const what = activate.cally(
  //         { dungeon, player, objects, ennemies, messages },
  //         padEvent({ button: "fictif" })
  //       );
  //       setActivate({ cally: what.activate });
  //       setDungeon(what.dungeon);
  //       setPlayer(what.player);
  //       setObjects(what.objects);
  //       setEnnemies(what.ennemies);
  //       setMessages(what.messages);
  //     }
  //   },
  //   [
  //     player,
  //     setActivate,
  //     dungeon,
  //     objects,
  //     activate,
  //     ennemies,
  //     setDungeon,
  //     setObjects,
  //     setPlayer,
  //     setEnnemies,
  //     messages,
  //     setMessages,
  //   ]
  // );

  return (
    <>
      <div className="game">
        <div className="game-row">
          <RenderDungeon2 viewSize={fov + 1} />
          <ActionConsole />
          <div className="game-paddle">
            <Pad />
            <button
              onClick={function () {
                const {
                  dungeon: dung,
                  player,
                  ennemies,
                  objects,
                  messages,
                  callback,
                } = initialize();
                setDungeon(dung);
                setPlayer(player);
                setEnnemies(ennemies);
                setObjects(objects);
                setMessages(messages);
                setActivate({ cally: callback });
              }}
            >
              renew
            </button>
          </div>
        </div>
        <ConsoleLog />
      </div>
    </>
  );
}

export default Game;
