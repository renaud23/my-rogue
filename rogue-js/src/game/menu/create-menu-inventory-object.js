import activate from "../activate-player";
import { buildPlayer, displayMenu, optionExit } from "./tools";
import { getObjects, removeObject } from "../player/inventory";
import { putObjectDungeon } from "../objects";
import { createEquipWeaponTodo, createThrowObjectTodo } from "../todo";
import { TYPE_OBJECT } from "../objects";

function underligne(word) {
  return new Array(word.length).fill("-").join("");
}

function createOptions(object, cally) {
  const { type, desc } = object;
  switch (type) {
    case TYPE_OBJECT.weapon:
      return [
        ...object.todo,
        { desc: `équiper ${desc}`, todo: createEquipWeaponTodo(object) },
        { desc: `poser ${desc}`, todo: createThrowObjectTodo(object) },
        { desc: "retour", todo: cally },
        optionExit,
      ];

    default:
      return [
        ...object.todo,
        { desc: `poser ${desc}`, todo: createThrowObjectTodo(object) },
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
      }),
      activate: displayMenu,
    };
  };
}

export default createObjectMenu;
