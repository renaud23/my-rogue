import React from "react";
import { Console, MapJoueur, JoueurHud, WorldHud, Journal } from "js/components";
import { journal } from "js/rogue";
import "./hud.css";

export default class Controller extends React.Component {
  constructor(props) {
    super(props);
    this.handleKeyPress = this.handleKeyPress.bind(this);
    this.state = { mouvements: 0 };
    this.refresh = this.refresh.bind(this);
    this.props.world.setRefresh(this.refresh);
    this.props.joueur.setRefresh(this.refresh);

    this.keyFunction = this.keyFunction.bind(this);
    this.inventory = false;
    this.game = true;
  }

  refresh() {
    this.setState({ mouvements: this.state.mouvements + 1 });
  }

  keyFunction(e) {
    switch (e.keyCode) {
      case 27:
        if (this.inventory) {
          this.game = true;
          this.inventory = false;
          this.props.event.dispatchWorld();
          this.setState({ mouvements: this.state.mouvements + 1 });
        }
        break;
      default:
    }
  }
  componentDidMount() {
    document.addEventListener("keydown", this.keyFunction, false);
  }
  componentWillUnmount() {
    document.removeEventListener("keydown", this.keyFunction, false);
  }

  handleKeyPress(e) {
    e.preventDefault();
    if (!this.props.event.gameIsFinished()) {
      switch (e.key) {
        case "I":
        case "i":
          if (this.game) {
            this.game = false;
            this.inventory = true;
            this.props.event.dispatchInventory();
            this.setState({ mouvements: this.state.mouvements + 1 });
          }
          break;
        case "Z":
        case "z":
          this.props.event.pressUp();
          break;
        case "S":
        case "s":
          this.props.event.pressDown();
          break;
        case "Q":
        case "q":
          this.props.event.pressLeft();
          break;
        case "D":
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
    let content = null;
    if (this.game) {
      content = <RenderGame renderer={this.props.renderer} world={this.props.world} joueur={this.props.joueur} />;
    } else if (this.inventory) {
      content = <RenderInventory />;
    }
    return (
      <div tabIndex="0" onKeyPress={this.handleKeyPress}>
        {content}
      </div>
    );
  }
}

const RenderGame = ({ renderer, world, joueur }) => (
  <div>
    <Console renderer={renderer} />
    <div className="hud">
      <WorldHud world={world} />
      <JoueurHud joueur={joueur} />
      <MapJoueur joueur={joueur} />
    </div>
    <Journal journal={journal} />
  </div>
);

const RenderInventory = () => <div>Inventaire</div>;
