import { versus } from "../fight";
import { consumeMove } from "../commons";

function resolveLevel({ level, player, action }) {
  const { weapon, position } = action;
  return level.reduce(
    function ([currLevel, currPlayer, currMsg], ennemy) {
      const { position: pe } = ennemy;
      if (pe === position) {
        const [newPlayer, newEnnemy, msg] = versus(player, ennemy, weapon);
        return [[...currLevel, newEnnemy], newPlayer, msg];
      }
      return [[...currLevel, ennemy], currPlayer, currMsg];
    },
    [[], player]
  );
}

function shootTodo(state) {
  const { player, ennemies, messages } = state;
  const { action, currentLevel } = player;
  const [nextEnnemies, nextPlayer, newMessage] = ennemies.reduce(
    function ([currEnnemies, currPlayer, currMsg], level, i) {
      if (i === currentLevel) {
        const [nextLevel, nextPlayer, newMsg] = resolveLevel({
          level,
          player,
          action,
        });

        return [[...currEnnemies, nextLevel], nextPlayer, newMsg];
      }
      return [[...currEnnemies, level], currPlayer, currMsg];
    },
    [[], player]
  );
  return {
    ...state,
    messages: newMessage ? [...messages, newMessage] : messages,
    ennemies: nextEnnemies,
    player: consumeMove({ ...nextPlayer, action: null }),
  };
}

export default shootTodo;
