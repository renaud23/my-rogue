import {
  PAD_BUTTON,
  PLAYER_ACTIONS,
  distanceEucl2,
  antecedantPoint,
} from "../commons";
import activate from "./activate";

function build(position) {
  return { type: PLAYER_ACTIONS.help, position };
}

function nextPosition(position, button, width) {
  switch (button) {
    case PAD_BUTTON.up:
      return position - width;
    case PAD_BUTTON.down:
      return position + width;
    case PAD_BUTTON.left:
      return position - 1;
    case PAD_BUTTON.right:
      return position + 1;
    default:
      return position;
  }
}

function movePosition(position, button, state) {
  const {
    dungeon: { width },
    player: { fov, position: pp },
  } = state;
  const lim = fov * fov;
  const next = nextPosition(position, button, width);

  const dist = distanceEucl2(
    antecedantPoint(pp, width),
    antecedantPoint(next, width)
  );

  return dist <= lim ? next : position;
}

function activateHelp(state, event) {
  const { player, ...rest } = state;

  const {
    payload: { button },
  } = event;
  if (!player.action) {
    return {
      activate: activateHelp,
      player: { ...player, action: build(player.position) },
      ...rest,
    };
  }

  switch (button) {
    case PAD_BUTTON.up:
    case PAD_BUTTON.down:
    case PAD_BUTTON.left:
    case PAD_BUTTON.right:
      return {
        activate: activateHelp,
        player: {
          ...player,
          action: build(movePosition(player.action.position, button, state)),
        },
        ...rest,
      };
    case PAD_BUTTON.buttonX:

    default:
      return { activate, player: { ...player, action: null }, ...rest };
  }
}

export default activateHelp;
