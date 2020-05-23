import { activateRootMenu } from "./menu";

// function lookAtTodo(state) {
//   const { player } = state;
//   const { position } = player;
//   return {
//     ...state,
//     player: { ...player, action: { type: PLAYER_ACTIONS.help, position } },
//     activate: activateHelp,
//   };
// }

/** */
// function actionTodo(state) {
//   return {
//     ...activateAction(state),
//   };
// }

function activateMenu(state, event) {
  return activateRootMenu(state, event); //rootTodo(state);
}

export default activateMenu;
