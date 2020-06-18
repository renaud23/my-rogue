import { fillMessage } from "../../commons";
import { createCorpse } from "../../objects";
import PATTERNS from "../../message-patterns";
import { createBloodEffect } from "../../effects";
import { putObjects } from "../../objects/dungeon-objects";

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
    const { objects, miscellaneous } = ns;
    const { effects } = miscellaneous;
    const { level, position } = dead;
    const newLevel = putObjects(
      objects,
      createCorpse(dead),
      ...dead.loot(dead)
    );

    // newLevel[level] = [
    //   ...objects[level],
    //   createCorpse(dead),
    //   ...dead.loot(dead),
    // ];

    return {
      ...ns,
      objects: newLevel,
      miscellaneous: {
        ...miscellaneous,
        effects: [...effects, createBloodEffect(position, level)],
      },
    };
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
