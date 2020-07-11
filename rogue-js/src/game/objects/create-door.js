import { openDoorTodo } from "../todo";
import typeObject from "./type-object";

let INDEX_DOOR = 1;

const TYPES = {
  door: {
    code: "door",
    desc: "une porte",
    size: 0,
    type: typeObject.door,
  },
  keyDoor: {
    code: "door-key",
    desc: "une de cle de porte",
    size: 1,
    type: typeObject.key,
  },
};

function create(position, level, locked = false) {
  return [
    {
      ...TYPES.door,
      desc: `Une porte`,
      id: `door-${INDEX_DOOR}`,
      takeable: false,
      level,
      position,
      locked,
      opened: false,
      todo: [{ desc: () => "Ouvrir la porte.", todo: openDoorTodo }],
    },
    undefined,
  ];
}

export default create;

// export function createDoor(position, level, opened = false) {
//   const door = {
//     ...SPECIAL_MAP.door,
//     position,
//     opened,
//     desc: `Une porte ${opened ? "ouverte" : "fermée"}`,
//     level,
//     id: `door-${INDEX++}`,
//   };

//   return {
//     ...door,
//     todo: [
//       {
//         desc: `${opened ? "Fermer" : "Ouvrir"} la porte`,
//         todo: splitDoor,
//       },
//     ],
//   };
// }
