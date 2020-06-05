import { optionExit, displayMenu } from "./tools";
import { PLAYER_ACTIONS } from "../../commons";
import exPointsMenu from "./menu-xp-points";

const OPTIONS = [
  { desc: "Point d'expérience", todo: exPointsMenu },
  optionExit,
];

function initializePlayer(player) {
  return {
    ...player,
    action: {
      type: PLAYER_ACTIONS.menu,
      header: ["MENU JOUEUR", "-----------"],
      options: [...OPTIONS],
      footer: [" ", "choisir avec le bouton A", "Sortir avec le bouton B"],
      active: 0,
    },
  };
}

function menuPlayer(state, event) {
  const { player } = state;
  return {
    ...state,
    player: initializePlayer(player),
    activate: displayMenu,
  };
}

export default menuPlayer;
