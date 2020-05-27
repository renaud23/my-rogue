import { PAD_BUTTON, PLAYER_ACTIONS } from "../commons";
import { navigateMap } from "./commons";
import activate from "./activate-player";
import { shootTodo } from "./todo";

export function buildPlayer({ player, position, color }) {
  const { weapon } = player;

  return {
    ...player,
    action: { type: PLAYER_ACTIONS.navigate, position, weapon, color },
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
      return { ...state, activate, player: { ...player, action: null } };
    case PAD_BUTTON.buttonA:
      return {
        ...shootTodo(next),
        activate,
        player: { ...player, action: null },
      };
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
  return { ...state, player: { ...player, action: null }, activate };
}

export default activateShoot;
