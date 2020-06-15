import { buildPlayer, displayMenu, optionExit } from "./tools";
import {
  equipWeaponTodo,
  equipAmmoTodo,
  throwObjectTodo,
  createUseKeyTodo,
} from "../todo";
import { TYPE_OBJECT } from "../objects";

function underligne(word) {
  return new Array(word.length).fill("-").join("");
}

function createOptions(object, cally) {
  const { type, desc } = object;
  switch (type) {
    case TYPE_OBJECT.meleeWeapon:
    case TYPE_OBJECT.distanceWeapon:
      return [
        ...object.todo,
        { desc: `équiper ${desc}`, todo: equipWeaponTodo },
        { desc: `poser ${desc}`, todo: throwObjectTodo },
        { desc: "retour", todo: cally },
        optionExit,
      ];
    case TYPE_OBJECT.ammo:
      return [
        ...object.todo,
        { desc: `équiper ${desc}`, todo: equipAmmoTodo },
        { desc: `poser ${desc}`, todo: throwObjectTodo },
        { desc: "retour", todo: cally },
        optionExit,
      ];
    case TYPE_OBJECT.key:
      return [
        ...object.todo,
        { desc: `utiliser ${desc}`, todo: createUseKeyTodo(object) },
        { desc: `poser ${desc}`, todo: throwObjectTodo },
        { desc: "retour", todo: cally },
        optionExit,
      ];

    default:
      return [
        ...object.todo,
        { desc: `poser ${desc}`, todo: throwObjectTodo },
        optionExit,
      ];
  }
}

function createObjectMenu(object, cally) {
  return function (state, event) {
    const { player } = state;
    return {
      ...state,
      player: buildPlayer({
        player,
        options: createOptions(object, cally),
        header: [object.desc, underligne(object.desc)],
        object,
      }),
      activate: displayMenu,
    };
  };
}

export default createObjectMenu;
