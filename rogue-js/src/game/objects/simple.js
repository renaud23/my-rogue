import typeObject from "./type-object";
import { randomInt } from "../../commons";
import { createMangerTodo } from "./create-simple-todo";

let INDEX = new Date().getTime();

export const MAP_SIMPLE = {
  thread: {
    code: 1,
    desc: "une pomme",
    size: 1,
    type: typeObject.simple,
  },
  bread: {
    code: 2,
    desc: "un clou",
    size: 1,
    type: typeObject.simple,
  },
  zippo: {
    code: 3,
    desc: "un briquet",
    size: 1,
    type: typeObject.simple,
  },
  bones: {
    code: 4,
    desc: "des os",
    size: 2,
    type: typeObject.simple,
  },
  stone: {
    code: 5,
    desc: "une grosse pierre",
    size: 2,
    type: typeObject.simple,
  },
  rock: {
    code: 6,
    desc: "un cailloux",
    size: 1,
    type: typeObject.simple,
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

export const createDestroyInDungeon = (object) => (state) => {
  const { player, dungeon } = state;
  const { currentLevel } = player;
  // SIDE effect to remove
  dungeon.removeObject(currentLevel, object);
  return state;
};

export function createSimple(code, { ...args }) {
  if (code in MAP_CODE_TO_OBJECT) {
    const model = MAP_CODE_TO_OBJECT[code];
    const todo = model.todo ? [...model.todo] : [];
    const simple = {
      id: INDEX++,
      ...model,
      ...args,
    };
    simple.todo = [...todo, createMangerTodo(simple)];
    simple.destroy = createDestroyInDungeon(simple);

    return simple;
  }
  return undefined;
}

export function createRandomSimple({ ...args } = {}) {
  return createSimple(SIMPLES[randomInt(SIMPLES.length)].code, { ...args });
}

export default SIMPLES;
