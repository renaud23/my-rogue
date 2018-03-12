import React from "react";
import "./console.css";

export default class MapJoueur extends React.Component {
  constructor(props) {
    super(props);
    this.state = { step: 0 };
    this.handleKeyPress = this.handleKeyPress.bind(this);
  }

  handleKeyPress(e) {
    this.setState({ step: this.state.step + 1 });
  }
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
      <div tabIndex="0" className="map-joueur" onKeyPress={this.handleKeyPress}>
        {rows}
      </div>
    );
  }
}
