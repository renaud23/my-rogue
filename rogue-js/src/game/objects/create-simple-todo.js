// import { MAP_SIMPLE } from "./simple";
import { cleanPlayerAction } from "../commons";

const manger = (object) => (state) => {
  // const { player, dungeon } = state;
  //   const { currentLevel } = player;
  //   switch (object.code) {
  //     case MAP_SIMPLE.bread.code:
  //       // TODO something more
  //       //TODO : side effect
  //       dungeon.removeObject(currentLevel, object);
  //       break;
  //     default:
  //       console.log("Vous n'allez quand même pas avaler n'importe quoi !");
  //   }
  return cleanPlayerAction(state);
};

export const createMangerTodo = function (object) {
  const { desc } = object;
  return { desc: `manger ${desc}`, todo: manger(object) };
};
