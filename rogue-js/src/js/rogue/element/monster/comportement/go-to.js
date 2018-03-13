class GoTo {
  constructor(monster, nx, ny) {
    this.monster = monster;
    this.nx = nx;
    this.ny = ny;

    this.already = new Set();
  }

  move(w) {
    if (!this.isFinished()) {
      for (let h = 0; h < this.monster.speed; h++) {
        let dx = 0;
        let dy = 0;
        let best = 999999;

        for (let i = -1; i <= 1; i++) {
          for (let j = -1; j <= 1; j++) {
            let tmp = { x: this.monster.x + i, y: this.monster.y + j };
            if (this.already.has(tmp) || (i === 0 && j === 0) || (i !== 0 && j !== 0)) continue;

            if (w.elementCanGo(this.monster, this.monster.x + i, this.monster.y + j)) {
              let distx = this.nx - this.monster.x - i;
              distx *= distx;
              let disty = this.ny - this.monster.y - j;
              disty *= disty;
              const dist = distx + disty;
              if (dist < best) {
                best = dist;
                dx = i;
                dy = j;
              }
            }
          }
        }
        if (dx === 0 && dy === 0) {
        } else {
          this.monster.x += dx;
          this.monster.y += dy;
          this.already.add({ x: this.monster.x, y: this.monster.y });
        }
      }
    }
  }

  isFinished() {
    return this.monster.x === this.nx && this.monster.y === this.ny;
  }

  activate(world) {
    this.move(world);
  }

  reset(x, y) {
    this.nx = x;
    this.ny = y;
    this.already.clear();
  }
}

export default (monster, nx, ny) => new GoTo(monster, nx, ny);
