import { PAD_BUTTON } from "../../commons";
import activate from "../activate-player";

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

function navigate(state, event) {
  const { player } = state;
  const { action } = player;
  if (!action || !action.position) return state;
  const { position } = action;
  const {
    payload: { button },
  } = event;
  switch (button) {
    case PAD_BUTTON.up:
    case PAD_BUTTON.down:
    case PAD_BUTTON.left:
    case PAD_BUTTON.right:
      return {
        ...state,
        player: {
          ...player,
          action: {
            ...action,
            position: movePosition(position, button, state),
          },
        },
      };
    default:
      return state;
  }
}

export default navigate;
