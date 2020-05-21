import React from "react";
import { useRecoilState } from "recoil";
import { maxMin } from "../commons/tools";
import { dungeonState, playerState } from "../recoil";
import combine from "./combine-fill";
import { TILES } from "../commons";
import fillDungeon from "./fill-dungeon";
import fillPlayer from "./fill-player";

export function GlobalRender() {
  const [dungeon] = useRecoilState(dungeonState);
  const [player] = useRecoilState(playerState);

  if (!dungeon) return null;
  const { currentLevel } = player;
  // const { width, data } = dungeon;
  const width = dungeon.getWidth(currentLevel);
  const data = dungeon.getData(currentLevel);
  const stack = [...data];
  stack[player.position] = TILES.player.code;
  const { rows } = render(data, width);
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

const fillStack = combine(fillDungeon, fillPlayer);

function PlayerRender({ viewSize }) {
  const [dungeon] = useRecoilState(dungeonState);
  const [player] = useRecoilState(playerState);
  if (!dungeon) return null;

  const { position, currentLevel } = player;
  const dungeonWidth = dungeon.getWidth(currentLevel);
  const dungeonHeight = dungeon.getHeight(currentLevel);
  const width = viewSize * 2 + 1;
  const dungX = position % dungeonWidth;
  const dungY = Math.trunc(position / dungeonWidth);
  const startX = maxMin(dungX - viewSize, 0, dungeonWidth - viewSize);
  const startY = maxMin(dungY - viewSize, 0, dungeonHeight - viewSize);

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
