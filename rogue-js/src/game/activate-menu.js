import { PAD_BUTTON, PLAYER_ACTIONS, maxMin } from "../commons";
import activateHelp, { buildActionHelp } from "./activate-help";
import activate from "./activate-player";

function build(options, active, header = ["MENU", "----"]) {
  return { type: PLAYER_ACTIONS.menu, options, active, header };
}

const buildExitAction = () => ({ player, ...args }) => ({
  ...args,
  player: { ...player, action: null },
});

const goToInventaire = () => ({ player, ...args }) => ({
  ...args,
  activate: inventaireMenu,
  player: {
    ...player,
    action: build(INVENTAIRE_OPTIONS, 0, [
      "INVENTAIRE",
      "----------",
      "nothing",
    ]),
  },
});

const ROOT_OPTIONS = [
  {
    desc: "1. observer",
    todo: ({ player, ...args }) => ({
      ...args,
      activate: activateHelp,
      player: { ...player, action: buildActionHelp(player.position) },
    }),
  },
  {
    desc: "2. inventaire",
    todo: goToInventaire(),
  },
  { desc: "3. exit", todo: buildExitAction() },
];

const INVENTAIRE_OPTIONS = [
  {
    desc: "1. retour",
    todo: ({ player, ...args }) => ({
      ...args,
      activate: root,
      player: { ...player, action: build(ROOT_OPTIONS, 1) },
    }),
  },
];

function moveActive(player, how) {
  const { action } = player;
  const { active, options } = action;

  return {
    ...player,
    action: { ...action, active: maxMin(active + how, 0, options.length - 1) },
  };
}

function navigate(button, state, callback, precCallback = activate) {
  const { player } = state;
  const {
    action: { options, active },
  } = player;
  switch (button) {
    case PAD_BUTTON.up:
      return {
        activate: callback,
        ...state,
        player: moveActive(player, -1),
      };
    case PAD_BUTTON.down:
      return {
        activate: callback,
        ...state,
        player: moveActive(player, 1),
      };
    case PAD_BUTTON.buttonY:
      return options[active].todo({ ...state, activate: precCallback });
    default:
      return { activate: precCallback, ...state };
  }
}

// inventaire
function inventaireMenu(state, event) {
  const {
    payload: { button },
  } = event;
  return navigate(button, state, root, root);
}

// Root
function root(state, event) {
  const { player, ...rest } = state;
  const {
    payload: { button },
  } = event;
  if (!player.action) {
    return {
      activate: root,
      player: { ...player, action: build(ROOT_OPTIONS, 0) },
      ...rest,
    };
  }

  return navigate(button, state, root);
}

export default root;
