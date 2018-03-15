import React from "react";
import "./journal.css";

export default class Journal extends React.Component {
  constructor(props) {
    super(props);
    this.refresh = this.refresh.bind(this);
    this.state = { journal: [] };
  }

  componentDidMount() {
    this.props.journal.setRefresh(this.refresh);
  }

  refresh() {
    this.setState({ journal: this.props.journal.getJournal() });
  }

  render() {
    const rows = this.state.journal.map((r, i) => (
      <div className={r.color} key={i}>
        {r.message}
      </div>
    ));
    return <div className="journal">{rows}</div>;
  }
}
