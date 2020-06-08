import typeObject from "./type-object";
import { create6sidesDices, one4SidesDice } from "../commons/dices";
import { meleeVersus, distanceVersus } from "../fight";

let INDEX = 0;

export const SPECIAL_MAP = {
  corpse: {
    code: 2000,
    originalDesc: "un cadavre",
    desc: "un cadavre",
    takeable: false,
    aggregative: false,
    type: typeObject.corpse,
  },
};

// export const SPECIALS_CODE_TO_OBJECT = Object.keys(SPECIAL_MAP).reduce(
//   function (a, o) {
//     const { code } = o;
//     return { ...a, [code]: o };
//   },
//   {}
// );

export function createCorpse(enemy) {
  const { position, level } = enemy;
  return { ...SPECIAL_MAP.corpse, position, level };
}
