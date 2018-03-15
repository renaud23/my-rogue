import React from "react";
import { Console, MapJoueur, JoueurHud, WorldHud, Journal } from "js/components";
import { journal } from "js/rogue";
import "./hud.css";
//
export default class Controller extends React.Component {
  constructor(props) {
    super(props);
    this.handleKeyPress = this.handleKeyPress.bind(this);
    this.state = { mouvements: 0 };
    this.refresh = this.refresh.bind(this);
    this.props.world.setRefresh(this.refresh);
    this.props.joueur.setRefresh(this.refresh);
  }

  refresh() {
    this.setState({ mouvements: this.state.mouvements + 1 });
  }

  handleKeyPress(e) {
    e.preventDefault();
    if (!this.props.event.gameIsFinished()) {
      switch (e.key) {
        case "z":
          this.props.event.pressUp();
          break;
        case "s":
          this.props.event.pressDown();
          break;
        case "q":
          this.props.event.pressLeft();
          break;
        case "d":
          this.props.event.pressRight();
          break;
        case " ":
          this.props.event.pressSpace();
          break;

        case "W":
        case "w":
          this.props.event.nextStep();
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
          <WorldHud world={this.props.world} />
          <JoueurHud joueur={this.props.joueur} />
          <MapJoueur joueur={this.props.joueur} />
        </div>
        <Journal journal={journal} />
      </div>
    );
  }
}
