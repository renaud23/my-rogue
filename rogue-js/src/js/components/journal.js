import React from "react";

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
    const rows = this.state.journal.map((r, i) => <div key={i}>{r}</div>);
    return <div className="journal">{rows}</div>;
  }
}
