import typeObject from "./type-object";
import { randomInt } from "../../commons";
import { createMangerTodo } from "./create-simple-todo";
import { putObject, removeObject } from "../player/inventory";

let INDEX = 0;

export const WEAPONS_MAP = {
  knife: {
    code: 1000,
    desc: "un couteau",
    size: 1,
    type: typeObject.weapon,
  },
  sword: {
    code: 1001,
    desc: "une épée",
    size: 1,
    type: typeObject.weapon,
    todo: [],
  },
};

export const WEAPONS_CODE_TO_OBJECT = Object.keys(WEAPONS_MAP).reduce(function (
  a,
  o
) {
  const { code } = o;
  return { ...a, [code]: o };
},
{});

export const WEAPONS_LIST = Object.values(WEAPONS_MAP);

export function createKnife() {
  const knife = {
    ...WEAPONS_MAP.knife,
    id: `knife-${INDEX++}`,
  };

  const todo = [];
  return { ...knife, todo };
}
