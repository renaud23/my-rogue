import React from "react";
import { useRecoilState } from "recoil";
import { PLAYER_ACTIONS, getTile, TILES } from "../commons";
import { computeDesc } from "../game/commons";
import {
  dungeonState,
  playerState,
  objectsState,
  ennemiesState,
} from "../recoil";
import { getObjectsAt } from "../game/commons";

function observeAt(state, position, ground = false) {
  const { player, objects, ennemies, dungeon } = state;
  const { currentLevel } = player;
  const data = dungeon.getData(currentLevel);
  const tile = getTile(data[position]);

  const what = [
    { ...tile, position },
    ...ennemies[currentLevel],
    ...objects[currentLevel],
  ].reduce(function (a, o, i) {
    if (i === 0 && !ground) {
      return a;
    }
    if (o.position === position) {
      return [...a, computeDesc(o)];
    }
    return a;
  }, []);

  const message = what.reduce(function (a, desc, i) {
    if (i === 0) {
      return `Vous apercevez, ${desc}${what.length === 1 ? "." : ""}`;
    }
    if (i === what.length - 1) {
      return `${a} et ${desc}.`;
    }
    return `${a}, ${desc}`;
  }, "Vous n'apercevez rien de spécial.");

  return `${message}`;
}

function peekEnnemiesMessages(state, level, position) {
  const { ennemies } = state;
  return ennemies[level].reduce(function (a, e) {
    const { position: ePos, desc } = e;
    if (ePos === position) {
      return [...a, `${desc} vous dévisage.`];
    }
    return a;
  }, []);
}

function peekObjectMessages(state, level, position) {
  return getObjectsAt(state, level, position).reduce(function ([a], o, i) {
    if (i === 0) {
      return [`Posé à vos pieds, vous apercevez, ${computeDesc(o)}`];
    }
    return [`${a}, ${computeDesc(o)}`];
  }, []);
}

function peekMenu(state) {
  const { player } = state;
  const {
    action: { options = [], active, header = [], footer = [] },
  } = player;

  const messages = options.reduce(
    function (a, { desc }, i) {
      if (i === active) {
        return [...a, { message: `${desc} X`, classnames: "message active" }];
      }
      return [...a, desc];
    },
    [...header]
  );
  return [...messages, ...footer];
}

function peekHelp(state) {
  const { player, dungeon } = state;
  const { action, position, currentLevel } = player;
  const data = dungeon.getData(currentLevel);
  const messages = ["OBSERVER", "--------"];
  if (action.position === position) {
    messages.push(TILES.player.desc);
  }
  const tile = getTile(data[action.position]);
  messages.push(tile.desc);
  const objects = peekObjectMessages(state, currentLevel, action.position);
  const ennemies = peekEnnemiesMessages(state, currentLevel, action.position);
  return [...messages, ...objects, ...ennemies];
}

function peekWeaponMessage(weapon) {
  // const {desc} = weapon
  return weapon
    ? `, ${weapon.desc} à la main`
    : ", sans rien pour vous défendre";
}

function peekNavigate(state) {
  const { player } = state;
  const { action } = player;
  const { position } = action;

  return [
    "Déplacer le curseur",
    "-------------------",
    observeAt(state, position, true),
    "Valider avec le bouton A.",
    "Sortir avec le bouton B.",
  ];
}

function peekPosition(state) {
  const { player, dungeon } = state;
  const { position, currentLevel, weapon } = player;
  const data = dungeon.getData(currentLevel);

  const tile = getTile(data[position]);
  const weaponMsg = peekWeaponMessage(weapon);

  return [
    `Vous marchez sur ${tile.desc}${weaponMsg}.`,
    observeAt(state, position),
  ];
}

function peekShootMessage(state) {
  const { player } = state;
  const { action } = player;
  const { position } = action;
  return [
    "Défendez vous !",
    "---------------",
    observeAt(state, position),
    " ",
    "Tirer avec le bouton A.",
    "Sortir avec le bouton B.",
  ];
}

function peekMessages(state) {
  const { player } = state;
  const { action } = player;
  if (action) {
    switch (action.type) {
      case PLAYER_ACTIONS.help:
        return peekHelp(state);
      case PLAYER_ACTIONS.menu:
        return peekMenu(state);
      case PLAYER_ACTIONS.navigate:
      case PLAYER_ACTIONS.action:
        return peekNavigate(state);
      case PLAYER_ACTIONS.shoot:
        return peekShootMessage(state);
      default:
    }
  }
  return peekPosition(state);
}

function ActionConsole() {
  const [dungeon] = useRecoilState(dungeonState);
  const [player] = useRecoilState(playerState);
  const [objects] = useRecoilState(objectsState);
  const [ennemies] = useRecoilState(ennemiesState);

  if (!dungeon) return null;
  const messages = peekMessages({ player, dungeon, objects, ennemies });

  return (
    <div className="action-console">
      {messages.map(function (m, i) {
        if (typeof m === "object") {
          const { classnames, message } = m;
          return (
            <div className={classnames} key={i}>
              {message}
            </div>
          );
        }

        return (
          <div className={`message`} key={i}>
            {m}
          </div>
        );
      })}
    </div>
  );
}

export default ActionConsole;
