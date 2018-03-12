import React from "react";

export default class Console extends React.Component {
  constructor(props) {
    super(props);
    this.state = { step: 0 };
    this.handleKeyPress = this.handleKeyPress.bind(this);
  }

  handleKeyPress(e) {
    switch (e.key) {
      case "z":
        this.props.renderer.pressUp();

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
    return (
      <div tabIndex="0" onKeyPress={this.handleKeyPress}>
        {this.props.children}
      </div>
    );
  }
}
