import React, { useEffect } from "react";
import { useRecoilState, useSetRecoilState } from "recoil";
import {
  dungeonState,
  playerState,
  activateState,
  ennemiesState,
  objectsState,
  messagesState,
  miscellaneousState,
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
import createEmpties from "../game/commons/empty-tiles-tools";
import CanvasRender from "./canvas-render";
import "./render-game.scss";

function initialize(setMiscellaneousState) {
  const dungeon = createDungeon(10, 40, 40);
  const empties = createEmpties(dungeon); // with side effect

  const player = createPlayer(dungeon, empties);
  const objects = createObjectDungeon({ dungeon, player }, empties);
  const ennemies = createEnnemiesDungeon({ dungeon, player, objects }, empties);
  const messages = ["<red>Un cri déchire la nuit.</red>"];
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
  const setDungeon = useSetRecoilState(dungeonState);
  const [player, setPlayer] = useRecoilState(playerState);
  const setActivate = useSetRecoilState(activateState);
  const setEnnemies = useSetRecoilState(ennemiesState);
  const setObjects = useSetRecoilState(objectsState);
  const setMessages = useSetRecoilState(messagesState);
  const [miscellaneous, setMiscellaneousState] = useRecoilState(
    miscellaneousState
  );

  const { fov } = player;

  useEffect(() => {
    const interval = setInterval(() => {
      const { effects } = miscellaneous;
      const [eff, change] = effects.reduce(
        function ([next, status], effect) {
          if (effect.activate) {
            const [nextEffect, isChange] = effect.activate(effect);
            return [[...next, nextEffect], status || isChange];
          }
          return [[...next, effect], status];
        },
        [[], false]
      );
      if (change) {
        setMiscellaneousState({ ...miscellaneous, effects: eff });
      }
    }, 100);
    return () => clearInterval(interval);
  }, [miscellaneous, setMiscellaneousState]);

  return (
    <>
      <div className="game">
        <PlayerConsole />
        <div className="game-row">
          <CanvasRender viewSize={fov + 1} tileSize={16} />
          <ActionConsole />
          <div className="game-paddle">
            <Pad />
          </div>
        </div>
        <ConsoleLog />
        <button
          onClick={function () {
            const [state, callback] = initialize(setMiscellaneousState);
            setDungeon(state.dungeon);
            setPlayer(state.player);
            setEnnemies(state.ennemies);
            setObjects(state.objects);
            setMessages(state.messages);
            setActivate({ cally: callback });
            setMiscellaneousState({ effects: [] });
          }}
        >
          renew
        </button>
      </div>
    </>
  );
}

export default Game;
