import { PAD_BUTTON, maxMin } from "../../commons";

function moveActive(player, how) {
  const { action } = player;
  const { active, options } = action;

  return {
    ...player,
    action: { ...action, active: maxMin(active + how, 0, options.length - 1) },
  };
}

function navigate(button, state) {
  const { player } = state;

  switch (button) {
    case PAD_BUTTON.up:
      return {
        ...state,
        player: moveActive(player, -1),
      };
    case PAD_BUTTON.down:
      return {
        ...state,
        player: moveActive(player, 1),
      };
    default:
      return state;
  }
}

export default navigate;
