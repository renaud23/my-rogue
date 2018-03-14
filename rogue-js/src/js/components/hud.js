import React from "react";
export class JoueurHud extends React.Component {
  render() {
    const { joueur } = this.props;
    const lifes = [];
    for (let i = 0; i < joueur.lifeMax / 10; i++) {
      let clazz = "dead";
      if (i < joueur.life / 10) {
        clazz = "not-dead";
      }
      lifes.push(
        <span className={clazz} key={i}>
          {String.fromCharCode(0x2764)}
        </span>
      );
    }
    return (
      <div className="hud-joueur">
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
