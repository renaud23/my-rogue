import React from "react";
import {
  dungeonState,
  playerState,
  objectsState,
  ennemiesState,
  activateState,
} from "../recoil";
import { useRecoilState } from "recoil";
import { padEvent } from "../game";
import { PAD_BUTTON } from "../commons";
import "./pad.scss";

function Pad() {
  const [dungeon, setDungeon] = useRecoilState(dungeonState);
  const [player, setPlayer] = useRecoilState(playerState);
  const [objects, setObjects] = useRecoilState(objectsState);
  const [ennemies, setEnnemies] = useRecoilState(ennemiesState);
  const [activate, setActivate] = useRecoilState(activateState);

  const on = function (button) {
    const what = activate.cally(
      { dungeon, player, objects, ennemies },
      padEvent(button)
    );
    setActivate({ cally: what.activate });
    setDungeon(what.dungeon);
    setPlayer(what.player);
    setObjects(what.objects);
    setEnnemies(what.ennemies);
  };

  if (!dungeon) return null;
  return (
    <div className="pad">
      <div className="pad-container">
        <div className="pad-cross">
          <div className="pad-row">
            <span className="blank"></span>
            <span
              className="direction"
              tabIndex="0"
              onClick={function (e) {
                e.stopPropagation();
                on(PAD_BUTTON.up);
              }}
            ></span>
            <span className="blank"></span>
          </div>
          <div className="pad-row">
            <span
              className="direction"
              tabIndex="0"
              onClick={function (e) {
                e.stopPropagation();
                on(PAD_BUTTON.left);
              }}
            ></span>
            <span className="center"></span>
            <span
              className="direction"
              tabIndex="0"
              onClick={function (e) {
                e.stopPropagation();
                on(PAD_BUTTON.right);
              }}
            ></span>
          </div>
          <div className="pad-row">
            <span className="blank"></span>
            <span
              className="direction"
              tabIndex="0"
              onClick={function (e) {
                e.stopPropagation();
                on(PAD_BUTTON.down);
              }}
            ></span>
            <span className="blank"></span>
          </div>
        </div>
        <div className="pad-center-bloc"></div>
        <div className="pad-buttons">
          <div className="pad-row-button">
            <span className="button-blank"></span>
            <span
              className="pad-button blue"
              title="menu"
              onClick={function (e) {
                e.stopPropagation();
                on(PAD_BUTTON.buttonY);
              }}
            ></span>
            <span className="button-blank"></span>
          </div>
          <div className="pad-row-button">
            <span
              className="pad-button green"
              title="help"
              onClick={function (e) {
                e.stopPropagation();
                on(PAD_BUTTON.buttonX);
              }}
            ></span>
            <span className="button-blank"></span>
            <span
              className="pad-button red"
              title="tir"
              onClick={function (e) {
                e.stopPropagation();
                on(PAD_BUTTON.buttonB);
              }}
            ></span>
          </div>
          <div className="pad-row-button">
            <span className="button-blank"></span>
            <span
              className="pad-button yellow"
              title="action"
              onClick={function (e) {
                e.stopPropagation();
                on(PAD_BUTTON.buttonA);
              }}
            ></span>
            <span className="button-blank"></span>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Pad;
