import typeObject from "./type-object";
import {
  create6sidesDices,
  one6SidesDice,
  one4SidesDice,
} from "../commons/dices";
import { meleeVersus, distanceVersus } from "../fight";

let INDEX = 0;

export const WEAPONS_MAP = {
  knife: {
    code: 1000,
    desc: "un couteau",
    size: 1,
    type: typeObject.meleeWeapon,
    getDamages: one4SidesDice,
  },
  sword: {
    code: 1001,
    desc: "une épée",
    size: 2,
    type: typeObject.meleeWeapon,
    getDamages: one6SidesDice,
  },
  armageddon: {
    code: 1002,
    desc: "Armageddon",
    size: 2,
    type: typeObject.meleeWeapon,
    getDamages: create6sidesDices(18),
  },
  woodenBow: {
    code: 1003,
    desc: "un arc en bois",
    size: 2,
    type: typeObject.distanceWeapon,
    getDamages: one4SidesDice,
  },
};

export const WEAPON_LIST_CODE = Object.entries(WEAPONS_MAP).reduce(function (
  a,
  [k, { code }]
) {
  return { ...a, [k]: code };
},
{});

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
    versus: meleeVersus,
  };

  const todo = [];
  return { ...knife, todo };
}

export function createSword() {
  const sword = {
    ...WEAPONS_MAP.sword,
    takeable: true,
    id: `sword-${INDEX++}`,
    versus: meleeVersus,
  };

  const todo = [];
  return { ...sword, todo };
}

export function createBow() {
  const sword = {
    ...WEAPONS_MAP.woodenBow,
    takeable: true,
    id: `bow-${INDEX++}`,
    range: 4,
    versus: distanceVersus,
  };

  const todo = [];
  return { ...sword, todo };
}

export function createArmageddon() {
  const sword = {
    ...WEAPONS_MAP.armageddon,
    takeable: true,
    id: `armageddon-${INDEX++}`,
    versus: meleeVersus,
  };

  const todo = [];
  return { ...sword, todo };
}
