import { versus } from "../fight";

function resolveLevel({ level, player, action }) {
  const { weapon, position } = action;
  return level.reduce(
    function ([currLevel, currPlayer], ennemy) {
      const { position: pe } = ennemy;
      if (pe === position) {
        const [newPlayer, newEnnemy] = versus(player, ennemy, weapon);
        return [[...currLevel, newEnnemy], newPlayer];
      }
      return [[...currLevel, ennemy], currPlayer];
    },
    [[], player]
  );
}

function shootTodo(state) {
  const { player, ennemies } = state;
  const { action, currentLevel } = player;
  const [nextEnnemies, nextPlayer] = ennemies.reduce(
    function ([currEnnemies, currPlayer], level, i) {
      if (i === currentLevel) {
        const [nextLevel, nextPlayer] = resolveLevel({ level, player, action });
        return [[...currEnnemies, nextLevel], nextPlayer];
      }
      return [[...currEnnemies, level], currPlayer];
    },
    [[], player]
  );

  return {
    ...state,
    ennemies: nextEnnemies,
    player: { ...nextPlayer, action: null },
  };
}

export default shootTodo;
