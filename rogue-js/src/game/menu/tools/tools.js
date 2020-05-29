import { PLAYER_ACTIONS } from "../../../commons";

export function addOptionsNumbers(options) {
  return options.map(function (o, i) {
    const { desc } = o;
    return { ...o, desc: `${i + 1}. ${desc}` };
  });
}

export function buildPlayer({
  player,
  options = [],
  active = 0,
  type = PLAYER_ACTIONS.menu,
  header = ["MENU", "----"],
  footer = [" ", "Validez avec le bouton A.", "Sortir avec le bouton B."],
  ...args
}) {
  return {
    ...player,
    action: {
      type,
      options: addOptionsNumbers(options),
      active,
      header,
      footer,
      ...args,
    },
  };
}
