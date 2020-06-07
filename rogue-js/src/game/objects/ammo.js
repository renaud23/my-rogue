import typeObject from "./type-object";
import { create6sidesDices } from "../commons/dices";

let INDEX = 0;

export const AMMO_MAP = {
  woodenArrow: {
    code: 1200,
    desc: "flèches en bois",
    size: 1,
    type: typeObject.ammo,
    getDamages: create6sidesDices(1),
  },
};

export const AMMO_CODE_TO_OBJECT = Object.keys(AMMO_MAP).reduce(function (
  a,
  o
) {
  const { code } = o;
  return { ...a, [code]: o };
},
{});

export const AMMO_LIST = Object.values(AMMO_MAP);

export function createArrows(how = 5) {
  const arrows = {
    ...AMMO_MAP.woodenArrow,
    originalDesc: AMMO_MAP.woodenArrow.desc,
    desc: `${how} ${AMMO_MAP.woodenArrow.desc}`,
    id: `ammo-${INDEX++}`,
    how,
    max: undefined, //TODO
    takeable: true,
    aggregative: true,
    todo: [],
    getDamages: () => 2,
  };
  return arrows;
}
