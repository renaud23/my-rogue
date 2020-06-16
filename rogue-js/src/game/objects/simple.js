import typeObject from "./type-object";
import { randomInt } from "../../commons";
import { createMangerTodo } from "./create-simple-todo";

let INDEX = new Date().getTime();

export const MAP_SIMPLE = {
  stone: {
    code: 1,
    desc: "une pierre",
    size: 1,
    type: typeObject.simple,
  },
  silex: {
    code: 2,
    desc: "un silex",
    size: 1,
    type: typeObject.simple,
  },
  wood: {
    code: 3,
    desc: "du bois mort",
    size: 1,
    type: typeObject.simple,
  },
  bones: {
    code: 4,
    desc: "des os",
    size: 2,
    type: typeObject.simple,
  },

  healingPotion: {
    code: 5,
    how: 0.2,
    desc: ({ how }) => `une potion de soin (${Math.trunc(how * 100)}%)`,
    size: 3,
    type: typeObject.potion,
    todo: [
      {
        desc: "Consommer la potion.",
        todo: function (state, object) {
          return state;
        },
      },
    ],
  },
};
const SIMPLES = Object.values(MAP_SIMPLE);
const MAP_CODE_TO_OBJECT = SIMPLES.reduce(function (a, o) {
  return { ...a, [o.code]: o };
}, {});

export function createDestroyInInventory(state) {
  // TODO
  return state;
}

// export const createDestroyInDungeon = (object) => (state) => {
//   const { player, dungeon } = state;
//   const { currentLevel } = player;
//   // SIDE effect to remove
//   dungeon.removeObject(currentLevel, object);
//   return state;
// };

export function createSimple(code, { ...args }) {
  if (code in MAP_CODE_TO_OBJECT) {
    const model = MAP_CODE_TO_OBJECT[code];
    const todo = model.todo ? [...model.todo] : [];
    const simple = {
      id: INDEX++,
      ...model,
      todo,
      takeable: true,
      ...args,
    };
    // simple.destroy = createDestroyInDungeon(simple);

    return simple;
  }
  return undefined;
}

export function createRandomSimple({ ...args } = {}) {
  return createSimple(SIMPLES[randomInt(SIMPLES.length)].code, { ...args });
}

export default SIMPLES;
