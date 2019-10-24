import React, { useState, useEffect, useRef } from "react";
import "./game.scss";

const onKeyDown = game => e => {
  if (
    e.key === "ArrowDown" ||
    e.key === "ArrowUp" ||
    e.key === "ArrowLeft" ||
    e.key === "ArrowRight"
  ) {
    game.startAction(e.key);
  }
};

const onKeyUp = game => e => {
  game.stopAction();
};

export default ({ game }) => {
  const [start, setStart] = useState(false);
  const ref = useRef();
  const elDungeon = useRef();
  const elMap = useRef();
  /* */
  useEffect(() => {
    if (!start) {
      game.start();
      setStart(true);
    }
  }, [start, game]);
  /* */
  useEffect(() => {
    if (ref.current && game) {
      ref.current.focus();
      game.initialiseRenderer(elDungeon.current, elMap.current);
      // Loop game
      if (ref.current) {
        window.setInterval(() => {
          game.activate();
          game.render();
        }, 50);
      }
    }
  }, [ref, game]);

  return (
    <div className="insee-rogue">
      <div
        className="viewport"
        tabIndex="0"
        ref={ref}
        onKeyDown={e => {
          e.preventDefault();
          e.stopPropagation();
          onKeyDown(game)(e);
        }}
        onKeyUp={e => {
          e.preventDefault();
          e.stopPropagation();
          onKeyUp(game)(e);
        }}
      >
        <pre ref={elDungeon} />
      </div>
      <div className="map">
        <pre ref={elMap} />
      </div>
    </div>
  );
};
