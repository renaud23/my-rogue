import React from "react";

import "./console.css";

class Console extends React.Component {
  render() {
    const { renderer } = this.props;

    const map = renderer.getFrame();
    const rows = [];
    for (let i = 0; i < renderer.getHauteur(); i++) {
      const rowContent = [];
      for (let j = 0; j < renderer.getLargeur(); j++) {
        const tile = map[j + i * renderer.getLargeur()];
        rowContent.push(
          <span className={tile.color} key={j}>
            {tile.value}
          </span>
        );
      }
      rows.push(
        <div className="row" key={i}>
          {rowContent}
        </div>
      );
    }
    return <div className="console">{rows}</div>;
  }
}

export default Console;
