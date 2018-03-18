let REFRESH = () => {};

class Journal {
  constructor() {
    this.journal = [];
  }
  setRefresh(refresh) {
    REFRESH = refresh;
    REFRESH();
  }
  getJournal() {
    return this.journal;
  }
  addRow(row, color = "black") {
    this.journal.push(row);
    REFRESH();
  }
}

export default new Journal();