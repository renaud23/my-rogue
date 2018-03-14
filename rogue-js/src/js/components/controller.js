import React from "react";
import { Console, MapJoueur, JoueurHud } from "js/components";
import "./hud";
//
export default class Controller extends React.Component {
  constructor(props) {
    super(props);
    this.state = { step: 0 };
    this.handleKeyPress = this.handleKeyPress.bind(this);
  }

  handleKeyPress(e) {
    if (!this.props.event.gameIsFinished()) {
      switch (e.key) {
        case "z":
          this.props.event.pressUp();
          this.setState({ step: this.state.step + 1 });
          break;
        case "s":
          this.props.event.pressDown();
          this.setState({ step: this.state.step + 1 });
          break;
        case "q":
          this.props.event.pressLeft();
          this.setState({ step: this.state.step + 1 });
          break;
        case "d":
          this.props.event.pressRight();
          this.setState({ step: this.state.step + 1 });
          break;
        case " ":
          this.props.event.pressSpace();
          this.setState({ step: this.state.step + 1 });
          break;
        default:
      }
    }
  }
  render() {
    return (
      <div tabIndex="0" onKeyPress={this.handleKeyPress}>
        <Console renderer={this.props.renderer} />
        <div className="hud">
          <div>Step : {this.state.step}</div>
          <JoueurHud joueur={this.props.joueur} />
          <MapJoueur joueur={this.props.joueur} />
        </div>
      </div>
    );
  }
}
