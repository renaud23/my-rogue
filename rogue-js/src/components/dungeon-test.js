import React from "react";
import createDungeon from "../game/factory/factory";
function getTileChar(tile) {
  if (tile === 0) {
    return "_";
  }
  return "X";
}

function DungeonTest() {
  const size = 60;
  const factory = createDungeon(size, size);

  const [rows] = factory.reduce(
    function ([rows, current], tile, i) {
      if (i % size === size - 1) {
        return [
          [
            ...rows,
            <div key={rows.length} style={{ color: "white" }}>{`${current
              .map(getTileChar)
              .join("")}${getTileChar(tile)}`}</div>,
          ],
          [],
        ];
      }

      return [rows, [...current, tile]];
    },
    [[], []]
  );
  return <pre>{rows}</pre>;
}

export default DungeonTest;
