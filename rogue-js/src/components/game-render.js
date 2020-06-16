import React from "react";
import { useRecoilState, useRecoilCallback, useSetRecoilState } from "recoil";
import { maxMin } from "../commons/tools";
import { tileClick } from "../game";
import {
  dungeonState,
  playerState,
  objectsState,
  messagesState,
  ennemiesState,
  activateState,
  miscellaneousState,
} from "../recoil";
import combine from "./combine-fill";
import fillDungeon from "./fill-dungeon";
import fillPlayer from "./fill-player";
import fillObjects from "./fill-objects";
import fillEnnemies from "./fill-ennemies";
import fillEffects from "./fill-effects";

function Tile({ char, color, bgColor, position }) {
  const setDungeon = useSetRecoilState(dungeonState);
  const setPlayer = useSetRecoilState(playerState);
  const setEnnemies = useSetRecoilState(ennemiesState);
  const setObjects = useSetRecoilState(objectsState);
  const setMessages = useSetRecoilState(messagesState);
  const setActivate = useSetRecoilState(activateState);
  const setMiscellaneousState = useSetRecoilState(miscellaneousState);

  const onClickTileCallback = useRecoilCallback(async function ({
    getPromise,
    ...r
  }) {
    const [
      dungeon,
      player,
      ennemies,
      objects,
      messages,
      activate,
      miscellaneous,
    ] = await Promise.all([
      getPromise(dungeonState),
      getPromise(playerState),
      getPromise(ennemiesState),
      getPromise(objectsState),
      getPromise(messagesState),
      getPromise(activateState),
      getPromise(miscellaneousState),
    ]);

    if (position !== undefined) {
      const next = activate.cally(
        { dungeon, player, objects, ennemies, messages, miscellaneous },
        tileClick(position)
      );
      setActivate({ cally: next.activate });
      setDungeon(next.dungeon);
      setPlayer(next.player);
      setObjects(next.objects);
      setEnnemies(next.ennemies);
      setMessages(next.messages);
      setMiscellaneousState(next.miscellaneous);
    }
  });
  return (
    <span
      className={`game-screen-tile`}
      onClick={onClickTileCallback}
      style={{
        color: color || "snow",
        backgroundColor: bgColor || "transparent",
      }}
    >
      {char}
    </span>
  );
}

function getRow(current, tile) {
  const { char, color, bgColor, position } = tile;
  return [
    ...current,
    <Tile
      char={char}
      color={color}
      bgColor={bgColor}
      position={position}
      key={current.length}
    />,
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

const fillStack = combine(
  fillDungeon,
  fillObjects,
  fillEnnemies,
  fillPlayer,
  fillEffects
);

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

function isVisible(player, tile) {
  const { visibles } = player;
  const { position } = tile;
  return visibles && visibles.indexOf(position) !== -1;
}

function PlayerRender({ viewSize }) {
  const [dungeon] = useRecoilState(dungeonState);
  const [player] = useRecoilState(playerState);
  const [ennemies] = useRecoilState(ennemiesState);
  const [objects] = useRecoilState(objectsState);
  const [miscellaneous] = useRecoilState(miscellaneousState);

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
    { dungeon, player, ennemies, objects, miscellaneous },
    rect
  );

  const [newTiles, newWidth] = enlarge(tiles, width);

  const rows = render(
    newTiles.map(function (t) {
      return { ...t, visible: isVisible(player, t) };
    }),
    newWidth
  );

  return <pre className="game-screen">{rows}</pre>;
}

export default PlayerRender;
