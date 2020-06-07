import { PAD_BUTTON } from "../../commons";
import { antecedentPoint, distanceEucl2 } from "../../commons";
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
// function checkLimiteCirc(state, next, limite) {
//   if (!limite) {
//     return true;
//   }
//   const { dungeon, player } = state;
//   const { currentLevel } = player;
//   const width = dungeon.getWidth(currentLevel);
//   const height = dungeon.getHeight(currentLevel);
//   const { position: pp } = player;

//   const distance = Math.sqrt(
//     distanceEucl2(antecedentPoint(next, width), antecedentPoint(pp, width))
//   );

//   if (distance <= limite) {
//     return true;
//   }

//   return false;
// }

// function checkLimiteRect(state, next, limite) {
//   if (!limite) {
//     return true;
//   }
//   const { dungeon, player } = state;
//   const { currentLevel } = player;
//   const width = dungeon.getWidth(currentLevel);
//   const height = dungeon.getHeight(currentLevel);
//   const { position: pp } = player;
//   const [nx, ny] = antecedentPoint(next, width);
//   const [px, py] = antecedentPoint(pp, width);
//   const minx = Math.max(px - limite, 0);
//   const maxx = Math.min(px + limite, width - 1);
//   const miny = Math.max(py - limite, 0);
//   const maxy = Math.min(py + limite, height - 1);
//   if (nx >= minx && nx <= maxx && ny >= miny && ny <= maxy) {
//     return true;
//   }

//   return false;
// }

// function checkLimite(state, next, limite, circular = false) {
//   if (circular) {
//     return checkLimiteCirc(state, next, limite);
//   }
//   return checkLimiteRect(state, next, limite);
// }

function movePosition(position, button, state, rangePositions) {
  const { dungeon, player } = state;
  const { currentLevel } = player;

  const width = dungeon.getWidth(currentLevel);
  const next = nextPosition(position, button, width);

  if (rangePositions.indexOf(next) !== -1) {
    return next;
  }
  return position;
}

function computeRangePositions(state, limite, circular = false) {
  const { dungeon, player } = state;
  const { currentLevel, visibles } = player;

  if (!limite) {
    return [...visibles];
  }

  const width = dungeon.getWidth(currentLevel);
  const height = dungeon.getHeight(currentLevel);
  const { position: pp } = player;
  const [px, py] = antecedentPoint(pp, width);
  const minx = Math.max(px - limite, 0);
  const maxx = Math.min(px + limite, width - 1);
  const miny = Math.max(py - limite, 0);
  const maxy = Math.min(py + limite, height - 1);
  const limiteCube = limite * limite;

  const w = maxx - minx + 1;
  const h = maxy - miny + 1;

  return new Array(h * w).fill(null).reduce(function (a, _, i) {
    const p = minx + (i % w) + (miny + Math.trunc(i / w)) * width;
    if (circular) {
      const dist = distanceEucl2(
        antecedentPoint(pp, width),
        antecedentPoint(p, width)
      );
      if (dist > limiteCube) {
        return a;
      }
    }
    return [...a, p];
  }, []);
}

function navigate(state, event, limite, circular = false) {
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
      const rangePositions = computeRangePositions(state, limite, circular);
      return {
        ...state,
        player: {
          ...player,
          action: {
            ...action,
            rangePositions,
            position: movePosition(position, button, state, rangePositions), //movePosition(position, button, state, limite, circular),
          },
        },
      };
    default:
      return state;
  }
}

export function createNavigate(successCallback, limite, circular = false) {
  return function navigateFunction(state, event) {
    const { player } = state;
    const {
      payload: { button },
    } = event;
    const next = navigate(state, event, limite, circular);

    switch (button) {
      case PAD_BUTTON.buttonB:
        return { ...state, activate, player: { ...player, action: undefined } };
      case PAD_BUTTON.buttonA:
        return successCallback(state, event);
      case PAD_BUTTON.up:
      case PAD_BUTTON.down:
      case PAD_BUTTON.left:
      case PAD_BUTTON.right:
      default:
        return {
          ...next,
          activate: navigateFunction,
        };
    }
  };
}

export default navigate;
