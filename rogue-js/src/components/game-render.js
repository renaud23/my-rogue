import React from "react";
import { useRecoilState } from "recoil";
import { maxMin } from "../commons/tools";
import { dungeonState, playerState } from "../recoil";
import combine from "./combine-fill";
import { TILES, getTile } from "../commons";
import fillDungeon from "./fill-dungeon";
import fillPlayer from "./fill-player";
import fillObjects from "./fill-objects";
import fillEnnemies from "./fill-ennemies";

export function GlobalRender() {
  const [dungeon] = useRecoilState(dungeonState);
  const [player] = useRecoilState(playerState);

  if (!dungeon) return null;
  const { currentLevel } = player;
  const width = dungeon.getWidth(currentLevel);
  const data = dungeon.getData(currentLevel);
  const stack = [...data];

  const rows = render(
    stack.map((c, i) => {
      if (i === player.position) {
        return { ...TILES.player, color: "red" };
      }
      return { ...getTile(c), color: "blue" };
    }),
    width
  );

  return <pre style={{ fontSize: 8 }}>{rows}</pre>;
}

function getRow(current, { char, color }) {
  return [
    ...current,
    <span style={{ color: color || "white" }} key={current.length}>
      {char}
    </span>,
  ];
}

function render(data, width) {
  return data.reduce(
    function ({ rows, current }, tile, i) {
      if (i % width === width - 1) {
        return {
          current: ``,
          rows: [...rows, <div key={i}>{getRow(current, tile)}</div>],
        };
      }
      return {
        current: getRow(current, tile),
        rows,
      };
    },
    { rows: [], current: [] }
  ).rows;
}

const fillStack = combine(fillDungeon, fillObjects, fillEnnemies, fillPlayer);

function PlayerRender({ viewSize }) {
  const [dungeon] = useRecoilState(dungeonState);
  const [player] = useRecoilState(playerState);
  if (!dungeon) return null;

  const { position, currentLevel, fov } = player;
  const dungeonWidth = dungeon.getWidth(currentLevel);
  const dungeonHeight = dungeon.getHeight(currentLevel);
  const width = Math.max(viewSize, fov) * 2 + 1;
  const dungX = position % dungeonWidth;
  const dungY = Math.trunc(position / dungeonWidth);
  const startX = maxMin(dungX - viewSize, 0, dungeonWidth - width);
  const startY = maxMin(dungY - viewSize, 0, dungeonHeight - width);
  const rect = {
    startX,
    startY,
    width,
    height: width,
  };

  const tiles = fillStack(
    Array(width * width).fill(-1),
    { dungeon, player },
    rect
  );

  const rows = render(tiles, width);

  return <pre className="game-screen">{rows}</pre>;
}

export default PlayerRender;
