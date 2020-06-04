import React from "react";
import { useRecoilState } from "recoil";
import { dungeonState, playerState } from "../recoil";

function ActionConsole() {
  const [dungeon] = useRecoilState(dungeonState);
  const [player] = useRecoilState(playerState);

  if (!dungeon) return null;
  const { desc, stats } = player;
  const {
    strength,
    agility,
    luck,
    endurance,
    level,
    xp,
    nextLevelXp,
    life,
  } = stats;
  const how = Math.trunc((xp / nextLevelXp) * 100);
  return (
    <pre className="player-console">
      <span className="bloc">
        <span className="label">Nom :</span>
        <span className="value">{desc}</span>
      </span>
      <span className="bloc">
        <span className="label">S.A.L.E :</span>
        <span className="value blue">{`(${strength}|${agility}|${luck}|${endurance})`}</span>
      </span>
      <span className="bloc">
        <span className="label">Level :</span>
        <span className="value">{level}</span>
      </span>
      <span className="bloc">
        <span className="label">progression :</span>
        <span className="value">{`${how} %`}</span>
      </span>
      <span className="bloc">
        <span className="label">vie :</span>
        <span className="value">{life}</span>
      </span>
    </pre>
  );
}

export default ActionConsole;
