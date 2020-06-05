import { PAD_BUTTON, PLAYER_ACTIONS } from "../commons";
import { navigateMap } from "./commons";
import activate from "./activate-player";
import { shootTodo } from "./todo";
import { isNeedWait } from "./activate-wait";
import { removeDeadEnnemies } from "./ennemies";

export function buildPlayer({ player, position, color }) {
  const { weapon } = player;
  const { desc } = weapon;

  return {
    ...player,
    action: {
      type: PLAYER_ACTIONS.shoot,
      position,
      weapon,
      color,
      options: [],
      header: [
        "SHOOT",
        "----",
        "",
        `Utiliser ${desc} avec le bouton A`,
        "Sortir avec le bouton B",
      ],
    },
  };
}

function moveIronSight(state, event) {
  const { player } = state;
  const { weapon } = player;
  const { range = 1 } = weapon;
  const {
    payload: { button },
  } = event;
  const next = navigateMap(state, event, range);
  switch (button) {
    case PAD_BUTTON.buttonB:
      return { ...state, activate, player: { ...player, action: undefined } };
    case PAD_BUTTON.buttonA:
      return isNeedWait({
        ...removeDeadEnnemies(shootTodo(next)),
        activate,
      });
    case PAD_BUTTON.up:
    case PAD_BUTTON.down:
    case PAD_BUTTON.left:
    case PAD_BUTTON.right:
    default:
      return { ...next, activate: moveIronSight };
  }
}

function activateShoot(state, event) {
  const { player } = state;
  const { position } = player;
  const { weapon } = player;
  if (weapon) {
    return {
      ...state,
      player: buildPlayer({ player, position, color: "red" }),
      activate: moveIronSight,
    };
  }
  return { ...state, player: { ...player, action: undefined }, activate };
}

export default activateShoot;
