import React from "react";
export class JoueurHud extends React.Component {
  render() {
    const { joueur } = this.props;
    for (let i = 0; i < joueur.life; i++) {}
    return <div className="hud-joueur">HUD</div>;
  }
}
