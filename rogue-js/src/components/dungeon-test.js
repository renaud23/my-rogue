import React from "react";
import createDungeon from "../game/dungeon/factory";

function DungeonTest() {
  const size = 60;
  const factory = createDungeon(size, size);

  const [rows] = factory.reduce(
    function ([rows, current], tile, i) {
      if (i % size === size - 1) {
        return [
          [
            ...rows,
            <div key={rows.length} style={{ color: "white" }}>{`${current.join(
              ""
            )}${tile}`}</div>,
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
