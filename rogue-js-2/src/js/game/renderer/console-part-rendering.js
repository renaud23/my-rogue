import { distanceEucl } from "../commons";

const getTile = (code, TILES) => {
  switch (code) {
    case TILES.rock:
      return "X";

    default:
      return " ";
  }
};

const maxMin = (value, max, min) => Math.min(Math.max(value, max), min);

export default (viewWidth = 10, viewHeight = 10) => ({ dungeonEl }) => game => {
  const { dungeon, player } = game;
  if (!dungeon || !player) return null;
  const { width, data, tiles, height } = dungeon;
  const wView = viewWidth * 2 + 1;
  const hView = viewHeight * 2 + 1;
  const startX = maxMin(
    (player.position % width) - viewWidth,
    0,
    width - wView
  );
  const startY = maxMin(
    Math.trunc(player.position / width) - viewHeight,
    0,
    height - hView
  );
  const px = player.position % dungeon.width;
  const py = Math.trunc(player.position / dungeon.width);
  const fov = Math.pow(player.fov, 2);
  const content = new Array(wView * hView)
    .fill(0)
    .map((o, i) => {
      const xi = i % wView;
      const yi = Math.trunc(i / wView);
      const inGamePos = startX + xi + (startY + yi) * width;
      const dist = distanceEucl(
        { x: px, y: py },
        { x: startX + xi, y: startY + yi }
      );

      if (dist > fov) {
        return ".";
      }

      return inGamePos === player.position
        ? "O"
        : getTile(data[inGamePos], tiles);
    })
    .reduce(
      (a, c, i) =>
        (i + 1) % (viewWidth * 2 + 1) === 0 ? `${a}${c}\n\r` : `${a}${c}`,
      ""
    );

  dungeonEl.textContent = content;
};
