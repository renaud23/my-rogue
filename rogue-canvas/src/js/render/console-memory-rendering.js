import { maxMin } from "../game/common-tools";

const getTile = (code, TILES) => {
  switch (code) {
    case TILES.rock:
      return "X";

    default:
      return " ";
  }
};

export default (viewWidth = 10, viewHeight = 10) => game => (
  renderer,
  texture
) => {
  const { dungeon, player } = game;
  if (!dungeon || !player) return null;
  const { width, data, tiles, height } = dungeon;
  const { worldView, position } = player;
  const wView = viewWidth * 2 + 1;
  const hView = viewHeight * 2 + 1;
  const startX = maxMin(
    (player.position % width) - viewWidth,
    0,
    Math.max(0, width - wView)
  );
  const startY = maxMin(
    Math.trunc(position / width) - viewHeight,
    0,
    Math.max(height - hView, 0)
  );
  const content = new Array(wView * hView).fill(0).map((o, i) => {
    const xi = i % wView;
    const yi = Math.trunc(i / wView);
    const inGamePos = startX + xi + (startY + yi) * width;
    if (inGamePos === player.position) {
      return "O";
    }
    if (inGamePos in worldView.visibleEnnemies) {
      return worldView.visibleEnnemies[inGamePos];
    }
    if (inGamePos in worldView.visibleTiles) {
      return getTile(data[inGamePos], tiles);
    }
    return ".";
  });
};
