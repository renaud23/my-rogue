import { PAD_BUTTON, PLAYER_ACTIONS } from "../commons";
import activate from "./activate-player";

export function buildActionHelp(position) {
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
  const { dungeon, player } = state;
  const { fov, position: pp, visibles, currentLevel } = player;
  const width = dungeon.getWidth(currentLevel);
  const lim = fov * fov;
  const next = nextPosition(position, button, width);

  return visibles.indexOf(next) !== -1 ? next : position;
}

function activateHelp(state, event) {
  const { player, ...rest } = state;
  const {
    payload: { button },
  } = event;
  if (!player.action) {
    return {
      activate: activateHelp,
      player: { ...player, action: buildActionHelp(player.position) },
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
          action: buildActionHelp(
            movePosition(player.action.position, button, state)
          ),
        },
        ...rest,
      };
    default:
      return { activate, player: { ...player, action: null }, ...rest };
  }
}

export default activateHelp;
