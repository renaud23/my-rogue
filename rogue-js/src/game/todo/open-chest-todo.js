import { TYPE_OBJECT } from "../objects";
import { lookAtInventory } from "../player/inventory";
import { fillMessage } from "../commons";
import PATTERNS from "../message-patterns";
import activate from "../activate-player";
import { openChest } from "./use-key-todo";

// function getOptions(state, chest) {
//   const { player } = state;
//   const { inventory } = player;
//   const { objects } = inventory;
//   return objects.reduce(function (a, o) {
//     const { type, desc } = o;
//     if (type === TYPE_OBJECT.key) {
//       return [...a, { desc, todo: useKeyTodo }];
//     }
//     return a;
//   }, []);
// }

// function initPlayer(state, chest) {
//   const { player } = state;
//   const options = getOptions(state, chest);
//   const { desc } = chest;
//   return {
//     ...player,
//     action: {
//       type: PLAYER_ACTIONS.menu,
//       header: [`vous voulez ouvrir ${desc} avec,`],
//       options: addOptionsNumbers([...options, optionExit]),
//       footer: [" ", "Utiliser avec le bouton A.", "Sortir avec le bouton B."],
//       active: 0,
//       chest,
//     },
//   };
// }

function youNeedAKey(state, chest) {
  const { player, messages } = state;
  return {
    ...state,
    player: { ...player, action: undefined },
    messages: [...messages, fillMessage(PATTERNS.needAKey, { chest })],
    activate,
  };
}

function tryToOpenChest(state, chest) {
  const { player } = state;
  const { kind } = chest;
  const { inventory } = player;
  const key = lookAtInventory(inventory, function (o) {
    const { target, type } = o;
    return type === TYPE_OBJECT.key && target === kind;
  });
  if (!key) {
    return youNeedAKey(state, chest);
  }
  return openChest(state, chest, key);
}

export default tryToOpenChest;
