import { PLAYER_ACTIONS } from "../../../commons";

export function addOptionsNumbers(options) {
  return options.map(function (o, i) {
    const { desc } = o;
    return { ...o, desc: `${i + 1}. ${desc}` };
  });
}

export function buildPlayer({
  player,
  options,
  active = 0,
  header = ["MENU", "----"],
  footer = [" ", "Validez avec le bouton Y."],
}) {
  return {
    ...player,
    action: {
      type: PLAYER_ACTIONS.menu,
      options: addOptionsNumbers(options),
      active,
      header,
      footer,
    },
  };
}
