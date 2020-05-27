import { buildPlayer, displayMenu, optionExit } from "./tools";
import { equipWeaponTodo, throwObjectTodo } from "../todo";
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
        { desc: `équiper ${desc}`, todo: equipWeaponTodo },
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
