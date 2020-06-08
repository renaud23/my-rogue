import React from "react";
import { useRecoilState } from "recoil";
import { maxMin } from "../commons/tools";
import {
  dungeonState,
  playerState,
  objectsState,
  ennemiesState,
} from "../recoil";
// import { isVisiblePosition } from "../game/commons";
import combine from "./combine-fill";
import { TILES, getTile } from "../commons";
import fillDungeon from "./fill-dungeon";
import fillPlayer from "./fill-player";
import fillObjects from "./fill-objects";
import fillEnnemies from "./fill-ennemies";

export function GlobalRender() {
  const [dungeon] = useRecoilState(dungeonState);
  const [player] = useRecoilState(playerState);
  const [ennemies] = useRecoilState(ennemiesState);

  if (!dungeon) return null;
  const { currentLevel } = player;
  const width = dungeon.getWidth(currentLevel);
  const data = dungeon.getData(currentLevel);
  const ennemiesPos = ennemies[currentLevel].map(({ position }) => position);
  const stack = [...data];

  const rows = render(
    stack.map((c, i) => {
      if (i === player.position) {
        return { ...TILES.player, color: "red" };
      }
      if (ennemiesPos.indexOf(i) !== -1) {
        return { ...TILES.ennemy, color: "magenta" };
      }
      return { ...getTile(c), color: "blue" };
    }),
    width
  );

  return <pre style={{ fontSize: 8 }}>{rows}</pre>;
}

function getRow(current, { char, color, bgColor }) {
  return [
    ...current,
    <span
      className="game-screen-cell"
      style={{
        color: color || "snow",
        backgroundColor: bgColor || "transparent",
      }}
      key={current.length}
    >
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
          rows: [
            ...rows,
            <div className="game-screen-row" key={i}>
              {getRow(current, tile)}
            </div>,
          ],
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

function multiply(repeat, how, row, t) {
  return new Array(repeat).fill(null).reduce(function (a, _) {
    return [...a, ...row, ...new Array(how).fill(t)];
  }, []);
}

function enlarge(tiles, width) {
  const how = 1;
  const nWidth = width * how;

  const [nTiles] = tiles.reduce(
    function ([tl, row], t, i) {
      if (i % width === width - 1) {
        return [[...tl, ...multiply(how, how, row, t)], []];
      }
      return [tl, [...multiply(1, how, row, t)]];
    },
    [[], []]
  );

  return [nTiles, nWidth];
}

// function mergeRows(a, b) {
//   return b.map(function (t, i) {
//     if (t.char === "_" || t.char === ".") {
//       return a[i];
//     }
//     return t;
//   });
// }

// function rowUp(a) {
//   return a.map(function (t, i) {
//     if (t.char === "X") {
//       return { ...t, color: "red" };
//     }
//     return t;
//   });
// }

// function isometric(tiles, width) {
//   const nWidth = width * 2;
//   const [rows] = tiles.reduce(
//     function ([tas, row], t, i) {
//       if (i % width === width - 1) {
//         return [[...tas, [...row, t]], []];
//       }
//       return [tas, [...row, t]];
//     },
//     [[], []]
//   );
//   const doubleRows = rows.map(function (r) {
//     return [r, r, r];
//   });

//   const [merged, last] = doubleRows.reduce(
//     function ([tas, cb, cc], [a, b, c], i) {
//       if (cb === undefined) {
//         return [[a], b, c];
//       }
//       return [
//         [...tas, mergeRows(rowUp(cb), a), mergeRows(rowUp(cc), cb)],
//         b,
//         c,
//       ];
//     },
//     [[], undefined, undefined]
//   );

//   const nTiles = [...merged, last].reduce(function (a, row) {
//     return [...a, ...row];
//   }, []);
//   return [nTiles, width];
// }

function PlayerRender({ viewSize }) {
  const [dungeon] = useRecoilState(dungeonState);
  const [player] = useRecoilState(playerState);
  const [ennemies] = useRecoilState(ennemiesState);
  const [objects] = useRecoilState(objectsState);
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
    { dungeon, player, ennemies, objects },
    rect
  );

  const [newTiles, newWidth] = enlarge(tiles, width);

  const rows = render(newTiles, newWidth);

  return <pre className="game-screen">{rows}</pre>;
}

export default PlayerRender;
