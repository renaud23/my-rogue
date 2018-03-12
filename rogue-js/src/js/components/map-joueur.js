import React from "react";
import "./console.css";

export default class MapJoueur extends React.Component {



  render() {
    const rows = [];
    const { joueur } = this.props;
    for (let j = 0; j < joueur.getMemory().getHauteur(); j++) {
      const rowContent = [];
      for (let i = 0; i < joueur.getMemory().getHauteur(); i++) {
        const tile = joueur.getMemory().getTile(i, j);
        rowContent.push(
          <span className={tile.color} key={i}>
            {tile.value}
          </span>
        );
      }
      rows.push(<div key={j}>{rowContent}</div>);
    }

    return (
      <div className="map-joueur">
        {rows}
      </div>
    );
  }
}
