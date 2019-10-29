import { maxMin } from "../game/common-tools";

const getTile = (code, TILES) => {
  switch (code) {
    case TILES.rock:
      return { tx: 16, ty: 0, width: 16, height: 16 };
    case TILES.empty:
      return { tx: 0, ty: 0, width: 16, height: 16 };

    default:
      return {};
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

  const { drTiles, drPlayer } = new Array(wView * hView).fill(0).reduce(
    ({ drTiles, drPlayer }, o, i) => {
      const xi = i % wView;
      const yi = Math.trunc(i / wView);
      const inGamePos = startX + xi + (startY + yi) * width;
      if (inGamePos === player.position) {
        drPlayer = { tx: 32, ty: 0, width: 16, height: 16, x: xi, y: yi };
      }
      // if (inGamePos in worldView.visibleEnnemies) {
      //   return worldView.visibleEnnemies[inGamePos];
      // }

      if (inGamePos in worldView.visibleTiles) {
        drTiles.push({ ...getTile(data[inGamePos], tiles), x: xi, y: yi });
      }
      return { drTiles, drPlayer };
    },
    { drTiles: [], drPlayer: {} }
  );

  drTiles.forEach(({ x, y, width, height, tx, ty }) => {
    renderer.drawTexture(
      texture,
      tx,
      ty,
      width,
      height,
      x * width,
      y * height,
      width,
      height
    );
  });
  renderer.drawTexture(
    texture,
    drPlayer.tx,
    drPlayer.ty,
    drPlayer.width,
    drPlayer.height,
    drPlayer.x * drPlayer.width,
    drPlayer.y * drPlayer.height,
    drPlayer.width,
    drPlayer.height
  );
};
