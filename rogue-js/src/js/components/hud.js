import React from "react";
import "./hud.css";

export class JoueurHud extends React.Component {
  render() {
    const { joueur } = this.props;
    const lifes = [],
      progressions = [];
    const percentProg = Math.round(joueur.xp / joueur.howForNextLevel() * 100);
    const percentLife = Math.round(joueur.life / joueur.lifeMax * 100);

    for (let i = 0; i < 10; i++) {
      let clazzLife = "dead",
        classProg = "todo";
      if (i * 10 < percentLife) {
        clazzLife = "not-dead";
      }
      if (i * 10 < percentProg) {
        classProg = "done";
      }
      lifes.push(
        <span className={clazzLife} key={i}>
          {String.fromCharCode(0x2764)}
        </span>
      );
      progressions.push(<span className={classProg} key={i} />);
    }

    return (
      <div className="hud-joueur">
        <div>Level : {joueur.level}</div>
        <div className="progression">{progressions}</div>
        <div className="lifes">{lifes}</div>
      </div>
    );
  }
}

export class WorldHud extends React.Component {
  render() {
    const { world } = this.props;
    return (
      <div className="hud-world">
        step:{world.step} action : {world.actions} sur {world.nbActions}
      </div>
    );
  }
}
