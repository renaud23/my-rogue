const getTile = (code, TILES) => {
  switch (code) {
    case TILES.rock:
      return "X";

    default:
      return " ";
  }
};

export default ({ dungeonEl }) => game => {
  const { dungeon, player } = game;
  if (!dungeon || !player) return null;
  const { width, data, tiles } = dungeon;
  const content = data
    .map((code, i) => (i === player.position ? "O" : getTile(code, tiles)))
    .reduce(
      (a, code, i) =>
        (i + 1) % width === 0 ? `${a}${code}\r\n` : `${a}${code}`,
      ""
    );

  dungeonEl.textContent = content;
};
