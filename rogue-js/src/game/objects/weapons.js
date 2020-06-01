import typeObject from "./type-object";
import { one6SidesDice, two6SidesDice } from "../commons";

let INDEX = 0;

export const WEAPONS_MAP = {
  knife: {
    code: 1000,
    desc: "un couteau",
    size: 1,
    type: typeObject.weapon,
    getDamages: one6SidesDice,
  },
  sword: {
    code: 1001,
    desc: "une épée",
    size: 2,
    type: typeObject.weapon,
    getDamages: two6SidesDice,
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
    takeable: true,
    id: `knife-${INDEX++}`,
  };

  const todo = [];
  return { ...knife, todo };
}

export function createSword() {
  const sword = {
    ...WEAPONS_MAP.sword,
    takeable: true,
    id: `sword-${INDEX++}`,
  };

  const todo = [];
  return { ...sword, todo };
}
