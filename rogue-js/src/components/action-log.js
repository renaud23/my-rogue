import React from "react";
import { useRecoilState } from "recoil";
import { PLAYER_ACTIONS, getTile, TILES } from "../commons";
import { dungeonState, playerState } from "../recoil";

function peekMenu(state) {
  const { player } = state;
  const {
    action: { options, active, header },
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
  return messages;
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

  return messages;
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
      default:
        return [];
    }
  }
  return [];
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
