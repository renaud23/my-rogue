import React from "react";

import "./console.css";

class Console extends React.Component {
  constructor(props) {
    super(props);
    this.state = { step: 0 };
    this.handleKeyPress = this.handleKeyPress.bind(this);
  }

  handleKeyPress(e) {
    switch (e.key) {
      case "z":
        this.props.renderer.pressUp();
        this.setState({ step: this.state.step + 1 });
        break;
      case "s":
        this.props.renderer.pressDown();
        this.setState({ step: this.state.step + 1 });
        break;
      case "q":
        this.props.renderer.pressLeft();
        this.setState({ step: this.state.step + 1 });
        break;
      case "d":
        this.props.renderer.pressRight();
        this.setState({ step: this.state.step + 1 });
        break;
      case " ":
        this.props.renderer.pressSpace();
        this.setState({ step: this.state.step + 1 });
        break;
      default:
    }
  }

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
      rows.push(<div key={i}>{rowContent}</div>);
    }
    return (
      <div tabIndex="0" className="console" onKeyPress={this.handleKeyPress}>
        {rows}
      </div>
    );
  }
}

export default Console;
