import React from "react";
import { useRecoilState } from "recoil";
import { PLAYER_ACTIONS, getTile, TILES } from "../commons";
import { dungeonState, playerState } from "../recoil";

function peekObjectMessages(state, position) {
  const { player, dungeon } = state;
  const { currentLevel } = player;
  return dungeon
    .getObjects(currentLevel)
    .filter(({ position: p }) => p === position)
    .reduce(function ([a], { _, object }, i) {
      const { desc } = object;
      if (i === 0) {
        return [`Posé à même le sol, vous apercevez, ${desc}`];
      }
      return [`${a}, ${desc}`];
    }, []);
}

function peekMenu(state) {
  const { player } = state;
  const {
    action: { options, active, header = [], footer = [] },
  } = player;
  const messages = options.reduce(
    function (a, { desc }, i) {
      if (i === active) {
        return [...a, `${desc} X`];
      }
      return [...a, desc];
    },
    [...header]
  );
  return [...messages, ...footer];
}

function peekHelp(state) {
  const { player, dungeon } = state;
  const { action, position, currentLevel } = player;
  const data = dungeon.getData(currentLevel);
  const messages = ["Vous apercevez :"];
  if (action.position === position) {
    messages.push(TILES.player.desc);
  }
  const tile = getTile(data[action.position]);
  messages.push(tile.desc);
  const objects = peekObjectMessages(state, action.position);
  return [...messages, ...objects];
}

function peekPosition(state) {
  const { player, dungeon } = state;
  const { position, currentLevel } = player;
  const data = dungeon.getData(currentLevel);
  const objects = peekObjectMessages(state, position);
  const tile = getTile(data[position]);

  return [`Vous êtes sur ${tile.desc}`, ...objects];
}

function peekMessages(state) {
  const { player } = state;
  const { action } = player;
  if (action) {
    switch (action.type) {
      case PLAYER_ACTIONS.help:
        return peekHelp(state);
      case PLAYER_ACTIONS.menu:
        return peekMenu(state);
      case PLAYER_ACTIONS.action:
        return peekMenu(state);
      default:
    }
  }
  return peekPosition(state);
}

function ActionConsole() {
  const [dungeon] = useRecoilState(dungeonState);
  const [player] = useRecoilState(playerState);

  if (!dungeon) return null;
  const messages = peekMessages({ player, dungeon });

  return (
    <pre className="action-console">
      {messages.map(function (m, i) {
        return (
          <div className="message" key={i}>
            {m}
          </div>
        );
      })}
    </pre>
  );
}

export default ActionConsole;
