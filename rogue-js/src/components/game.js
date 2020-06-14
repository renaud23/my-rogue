import React from "react";
import { useRecoilState } from "recoil";
import RenderDungeon2 from "./game-render";
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
import { createPlayer, updatePlayerView } from "../game/player";
import { createEnnemiesDungeon } from "../game/ennemies";
import ActionConsole from "./action-log";
import ConsoleLog from "./console-log";
import PlayerConsole from "./player-log";
import "./render-game.scss";

function initialize() {
  const dungeon = createDungeon(10, 30, 30);
  const player = createPlayer(dungeon, 8);
  const objects = createObjectDungeon({ dungeon });
  const ennemies = createEnnemiesDungeon({ dungeon });
  const messages = [
    "<red>Un cri déchire la nuit. Son echo sinistre vous plonge dans la torpeur.</red> (tu flippes comme une tarlouze)",
  ];
  const state = updatePlayerView({
    dungeon,
    player,
    objects,
    ennemies,
    messages,
  });
  return [state, cally];
}

function Game() {
  const setDungeon = useRecoilState(dungeonState)[1];
  const [player, setPlayer] = useRecoilState(playerState);
  const setActivate = useRecoilState(activateState)[1];
  const setEnnemies = useRecoilState(ennemiesState)[1];
  const setObjects = useRecoilState(objectsState)[1];
  const setMessages = useRecoilState(messagesState)[1];
  const { fov } = player;

  return (
    <>
      <div className="game">
        <PlayerConsole />
        <div className="game-row">
          <RenderDungeon2 viewSize={fov + 1} />
          <ActionConsole />
          <div className="game-paddle">
            <Pad />
          </div>
        </div>
        <ConsoleLog />
        <button
          onClick={function () {
            const [state, callback] = initialize();
            setDungeon(state.dungeon);
            setPlayer(state.player);
            setEnnemies(state.ennemies);
            setObjects(state.objects);
            setMessages(state.messages);
            setActivate({ cally: callback });
          }}
        >
          renew
        </button>
      </div>
    </>
  );
}

export default Game;
