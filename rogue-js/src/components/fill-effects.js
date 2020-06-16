import { BLOOD_EFFECT } from "../game/effects";

function fillBloodEffect(tile, state, effect) {
  const { char = tile.char } = effect;
  return { ...tile, char, bgColor: "rgba(255, 0, 0, 0.4)" };
}

function fillEffect(tile, state, effect) {
  const { type } = effect;
  switch (type) {
    case BLOOD_EFFECT:
      return fillBloodEffect(tile, state, effect);
    default:
      return tile;
  }
}

function fill(tiles, state, rect) {
  const { miscellaneous, player } = state;
  const { currentLevel } = player;
  const { effects } = miscellaneous;

  const mapEffect = effects
    .filter(function (effect) {
      const { level } = effect;
      return level === currentLevel;
    })
    .reduce(function (a, effect) {
      const { position } = effect;
      return { ...a, [position]: effect };
    }, {});

  const next = tiles.map(function (tile) {
    const { position } = tile;
    if (position in mapEffect) {
      return fillEffect(tile, state, mapEffect[position]);
    }

    return tile;
  });
  return next;
}

export default fill;
