import React from "react";
import { maxMin, getSegment } from "../commons/tools";
import { dungeonState, playerState } from "../recoil";
import combine from "./combine-fill";

import { useRecoilState } from "recoil";
import { TILES, CODE_TO_TILE, PLAYER_ACTIONS } from "../commons";
import fillDungeon from "./fill-dungeon";
import fillPlayer from "./fill-player";

export function GlobalRender() {
  const [dungeon] = useRecoilState(dungeonState);
  const [player] = useRecoilState(playerState);

  if (!dungeon) return null;
  const { width, data } = dungeon;
  const stack = [...data];
  stack[player.position] = TILES.player.code;
  const { rows } = render(data, width);
  return <pre style={{ fontSize: 8 }}>{rows}</pre>;
}

function getRow(current, { char, color }) {
  return [
    ...current,
    <span style={{ color: color || "black" }} key={current.length}>
      {char}
    </span>,
  ];
}

function render(data, width) {
  return data.reduce(
    function ({ rows, current }, tile, i) {
      const { char, className, color } = tile;
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

  const { position, action } = player;

  const width = viewSize * 2 + 1;
  const dungX = position % dungeon.width;
  const dungY = Math.trunc(position / dungeon.width);
  const startX = maxMin(dungX - viewSize, 0, dungeon.width - viewSize);
  const startY = maxMin(dungY - viewSize, 0, dungeon.height - viewSize);

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

  return <pre style={{ fontSize: 14 }}>{rows}</pre>;
}

export default PlayerRender;
