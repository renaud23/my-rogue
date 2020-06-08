import { fillMessage } from "../../commons";
import { createCorpse } from "../../objects";
import PATTERNS from "../../message-patterns";

function removeDeadsLevel(level) {
  return level.reduce(
    function ([a, b, deads], enn) {
      const { stats } = enn;
      const { life } = stats;
      if (life <= 0) {
        return [
          a,
          [...b, fillMessage(PATTERNS.deadEnemy, { att: enn })],
          [...deads, enn],
        ];
      }
      return [[...a, enn], b, deads];
    },
    [[], [], []]
  );
}

function transformDeads(state, deads) {
  return deads.reduce(function (ns, dead) {
    const { objects } = ns;
    const { level } = dead;
    const newLevel = [...objects];
    newLevel[level] = [...objects[level], createCorpse(dead)];

    return { ...ns, objects: newLevel };
  }, state);
}

/**
 *
 * @param {*} state
 */
function removeDeadEnnemies(state) {
  const { player, ennemies, messages } = state;
  const { currentLevel } = player;
  const [nextEnnemies, nextMessages, deadEnnemies] = ennemies.reduce(
    function ([currEnnemies, currMessages, currDeads], level, i) {
      if (i === currentLevel) {
        const [newLevel, levelMsg, deads] = removeDeadsLevel(level);
        return [
          [...currEnnemies, newLevel],
          [...currMessages, ...levelMsg],
          deads,
        ];
      }
      return [[...currEnnemies, level], currMessages, currDeads];
    },
    [[], []],
    []
  );

  return transformDeads(
    {
      ...state,
      ennemies: nextEnnemies,
      messages: [...messages, ...nextMessages],
    },
    deadEnnemies
  );
}

export default removeDeadEnnemies;
