class Queue {
  data = {};
  min = undefined;

  empty() {
    return this.min === undefined;
  }

  put(e, cost = 0) {
    if (cost in this.data) {
      this.data[cost].push(e);
    } else {
      this.data[cost] = [e];
    }
    this.min = Math.min(cost, this.min || cost);
  }

  getMin() {
    return Object.keys(this.data).reduce(
      (a, v) => (a ? Math.min(a, parseInt(v)) : v),
      undefined
    );
  }

  get() {
    if (this.min !== undefined) {
      const value = this.data[this.min].pop();
      if (this.data[this.min].length === 0) {
        delete this.data[this.min];
      }
      this.min = this.getMin();

      return value;
    }
    return undefined;
  }
}

export default Queue;
