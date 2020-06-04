import { versus, computeXP } from "../fight";
import { consumeMove } from "../commons";

function resolveLevel({ level, player, action }) {
  const { weapon, position } = action;
  return level.reduce(
    function ([currLevel, currPlayer, currMsg], enemy) {
      const { position: pe } = enemy;
      if (pe === position) {
        const [newPlayer, newEnemy, msg] = versus(player, enemy, weapon);
        return [[...currLevel, newEnemy], computeXP(newPlayer, newEnemy), msg];
      }
      return [[...currLevel, enemy], currPlayer, currMsg];
    },
    [[], player, []]
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
    [[], player, []]
  );
  return {
    ...state,
    messages: [...messages, ...newMessage],
    ennemies: nextEnnemies,
    player: consumeMove({ ...nextPlayer, action: null }),
  };
}

export default shootTodo;
