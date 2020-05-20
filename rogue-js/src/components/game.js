import React from "react";
import { useRecoilState } from "recoil";
import RenderDungeon, { GlobalRender } from "./render-dungeon";
import { dungeonState, playerState, activateState } from "../recoil";
import { activate as cally } from "../game";
import { randomInt } from "../commons/tools";
import Pad from "./pad";
import { createCave } from "../dungeon";

const FOV = 8;

function peekOne(sac) {
  return sac.splice(randomInt(sac.length), 1)[0];
}

function initialize() {
  const dungeon = createCave(60, 30);

  const sac = [...dungeon.emptyTiles];
  const position = peekOne(sac);

  const player = { position, fov: FOV, action: null };

  return { dungeon, player, callback: cally };
}

function Game() {
  const [dungeon, setDungeon] = useRecoilState(dungeonState);
  const [player, setPlayer] = useRecoilState(playerState);
  const [activate, setActivate] = useRecoilState(activateState);
  return (
    <>
      <Pad />
      <button
        onClick={function () {
          const { dungeon, player, callback } = initialize();

          setDungeon(dungeon);
          setPlayer(player);
          setActivate({ cally: callback });
        }}
      >
        renew
      </button>

      <RenderDungeon viewSize={FOV + 1} />
      <GlobalRender />
    </>
  );
}

export default Game;
