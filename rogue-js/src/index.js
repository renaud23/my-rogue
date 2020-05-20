import React from "react";
import { render } from "react-dom";
import { RecoilRoot } from "recoil";
import { Game } from "./components";

render(
  <RecoilRoot>
    <Game />
  </RecoilRoot>,
  document.getElementById("root")
);
