import { PAD_BUTTON } from "../../commons";
import { antecedentPoint } from "../../commons";

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

function checkLimite(state, next, limite) {
  if (!limite) {
    return true;
  }
  const { dungeon, player } = state;
  const { currentLevel } = player;
  const width = dungeon.getWidth(currentLevel);
  const height = dungeon.getHeight(currentLevel);
  const { position: pp } = player;
  const [nx, ny] = antecedentPoint(next, width);
  const [px, py] = antecedentPoint(pp, width);
  const minx = Math.max(px - limite, 0);
  const maxx = Math.min(px + limite, width - 1);
  const miny = Math.max(py - limite, 0);
  const maxy = Math.min(py + limite, height - 1);
  if (nx >= minx && nx <= maxx && ny >= miny && ny <= maxy) {
    return true;
  }

  return false;
}

function movePosition(position, button, state, limite) {
  const { dungeon, player } = state;
  const { visibles, currentLevel } = player;
  const width = dungeon.getWidth(currentLevel);
  const next = nextPosition(position, button, width);
  if (checkLimite(state, next, limite)) {
    return visibles.indexOf(next) !== -1 ? next : position;
  }
  return position;
}

function navigate(state, event, limite) {
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
            position: movePosition(position, button, state, limite),
          },
        },
      };
    default:
      return state;
  }
}

export default navigate;
