import React from "react";
import {Console, MapJoueur} from "js/components"
//
export default class Controller extends React.Component {
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
    return (
      <div tabIndex="0" onKeyPress={this.handleKeyPress}>
      <Console renderer={this.props.renderer} />
      <MapJoueur joueur={this.props.renderer.getWorld().joueur} />
      </div>
    );
  }
}
