import { buildPlayer, displayMenu, optionExit } from "./tools";
import {
  equipWeaponTodo,
  equipAmmoTodo,
  throwObjectTodo,
  createUseKeyTodo,
} from "../todo";
import { TYPE_OBJECT } from "../objects";
import { computeDesc } from "../../game/commons";

function underligne(word) {
  return new Array(word.length).fill("-").join("");
}

function createOptions(object, cally) {
  const { type, todo } = object;
  switch (type) {
    case TYPE_OBJECT.meleeWeapon:
    case TYPE_OBJECT.distanceWeapon:
      return [
        ...todo,
        {
          desc: `équiper ${computeDesc(object)}`,
          todo: equipWeaponTodo,
          args: object,
        },
        {
          desc: `poser ${computeDesc(object)}`,
          todo: throwObjectTodo,
          args: object,
        },
        { desc: "retour", todo: cally },
        optionExit,
      ];
    case TYPE_OBJECT.ammo:
      return [
        ...todo,
        {
          desc: `équiper ${computeDesc(object)}`,
          todo: equipAmmoTodo,
          args: object,
        },
        {
          desc: `poser ${computeDesc(object)}`,
          todo: throwObjectTodo,
          args: object,
        },
        { desc: "retour", todo: cally },
        optionExit,
      ];
    case TYPE_OBJECT.key:
      return [
        ...todo,
        {
          desc: `utiliser ${computeDesc(object)}`,
          todo: createUseKeyTodo(object),
          args: object,
        },
        {
          desc: `poser ${computeDesc(object)}`,
          todo: throwObjectTodo,
          args: object,
        },
        { desc: "retour", todo: cally },
        optionExit,
      ];

    default:
      return [
        ...todo,
        {
          desc: `poser ${computeDesc(object)}`,
          todo: throwObjectTodo,
          args: object,
        },
        optionExit,
      ];
  }
}

function createObjectMenu(object, cally) {
  return function (state, event) {
    const { player } = state;
    const desc = computeDesc(object);
    return {
      ...state,
      player: buildPlayer({
        player,
        options: createOptions(object, cally),
        header: [desc, underligne(desc)],
        object,
      }),
      activate: displayMenu,
    };
  };
}

export default createObjectMenu;
