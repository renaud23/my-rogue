import { fillMessage } from "../../commons";
import PATTERNS from "../../message-patterns";

function removeDeadsLevel(level) {
  return level.reduce(
    function ([a, b], enn) {
      const { stats } = enn;
      const { life } = stats;
      if (life <= 0) {
        return [a, [...b, fillMessage(PATTERNS.deadEnemy, { att: enn })]];
      }
      return [[...a, enn], b];
    },
    [[], []]
  );
}

/**
 *
 * @param {*} state
 */
function removeDeadEnnemies(state) {
  const { player, ennemies, messages } = state;
  const { currentLevel } = player;
  const [nextEnnemies, nextMessages] = ennemies.reduce(
    function ([currEnnemies, currMessages], level, i) {
      if (i === currentLevel) {
        const [newLevel, levelMsg] = removeDeadsLevel(level);
        return [
          [...currEnnemies, newLevel],
          [...currMessages, ...levelMsg],
        ];
      }
      return [[...currEnnemies, level], currMessages];
    },
    [[], []]
  );

  return {
    ...state,
    ennemies: nextEnnemies,
    messages: [...messages, ...nextMessages],
  };
}

export default removeDeadEnnemies;
