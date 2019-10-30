import { maxMin } from "../game/common-tools";

/* */
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

/* */
const createGameRenderer = ({
  wView,
  hView,
  tileWidth,
  tileHeight,
  marge
}) => game => (renderer, texture) => {
  const { dungeon, player } = game;
  if (!dungeon || !player) return null;
  const { width, data, tiles, height } = dungeon;
  const { worldView, position } = player;

  const startX = maxMin(
    (player.position % width) - player.fov - marge,
    0,
    Math.max(0, width - wView)
  );
  const startY = maxMin(
    Math.trunc(position / width) - player.fov - marge,
    0,
    Math.max(height - hView, 0)
  );

  const { drTiles, drPlayer, memorisedTiles } = new Array(wView * hView)
    .fill(0)
    .reduce(
      ({ drTiles, drPlayer, memorisedTiles }, o, i) => {
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
        } else if (inGamePos in worldView.memorisedTiles) {
          memorisedTiles.push({
            ...getTile(data[inGamePos], tiles),
            x: xi,
            y: yi
          });
        }
        return { drTiles, drPlayer, memorisedTiles };
      },
      { drTiles: [], drPlayer: {}, memorisedTiles: [] }
    );

  drTiles.forEach(({ x, y, width, height, tx, ty }) => {
    renderer.drawTexture(
      texture,
      tx,
      ty,
      width,
      height,
      x * tileWidth,
      y * tileHeight,
      tileWidth,
      tileHeight
    );
  });

  memorisedTiles.forEach(({ x, y, width, height, tx, ty }) => {
    renderer.drawTexture(
      texture,
      tx,
      ty,
      width,
      height,
      x * tileWidth,
      y * tileHeight,
      tileWidth,
      tileHeight
    );

    renderer.fillRect(
      "rgba(0, 0, 0, 0.8)",
      x * tileWidth,
      y * tileHeight,
      tileWidth,
      tileHeight
    );
  });

  renderer.drawTexture(
    texture,
    drPlayer.tx,
    drPlayer.ty,
    drPlayer.width,
    drPlayer.height,
    drPlayer.x * tileWidth,
    drPlayer.y * tileHeight,
    tileWidth,
    tileHeight
  );
};

/* */
export default ({ fov, screenWidth, screenHeight, marge = 2 } = {}) => {
  const wView = fov * 2 + 1 + 2 * marge;
  const hView = fov * 2 + 1 + 2 * marge;
  const tileWidth = Math.trunc(screenWidth / wView);
  const tileHeight = Math.trunc(screenHeight / hView);
  const gameRenderer = createGameRenderer({
    wView,
    hView,
    tileWidth,
    tileHeight,
    marge
  });
  return game => (renderer, texture) => gameRenderer(game)(renderer, texture);
};
