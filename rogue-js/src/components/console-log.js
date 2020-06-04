import React from "react";
import { useRecoilState } from "recoil";
import { dungeonState, messagesState } from "../recoil";

function parse(matchAll, message) {
  if (matchAll.length === 0) {
    return [{ text: message, color: "white" }];
  }
  const [data] = matchAll.reduce(
    function ([data, index], e, i) {
      const idx = e.index;
      const newIndex = idx + e[0].length;
      const part = message.substr(index, idx - index);
      if (i === matchAll.length - 1 && newIndex < message.length - 1) {
        const rest = { text: message.substr(newIndex), color: "white" };
        return [
          [
            ...data,
            { text: part, color: "white" },
            { text: e[2], color: e[1] },
            rest,
          ],
          newIndex,
        ];
      }
      return [
        [...data, { text: part, color: "white" }, { text: e[2], color: e[1] }],
        newIndex,
      ];
    },
    [[], 0, message]
  );
  return data;
}

function parseMessage(message = "") {
  let regexp = /<(.*?)>(.*?)<(\/.*?)>/g;
  let matchAll = [...message.matchAll(regexp)];

  const data = parse(matchAll, message);
  return data.map(function ({ text, color }, i) {
    return (
      <span key={i} style={{ color }}>
        {text}
      </span>
    );
  });
}

function ConsoleLog() {
  const [messages] = useRecoilState(messagesState);
  const [dungeon] = useRecoilState(dungeonState);
  if (!dungeon) return null;

  return (
    <pre className="console-log">
      {[...messages]
        .reverse()
        .filter((_, i) => i < 100)
        .map(function (m, i) {
          return (
            <div key={i}>
              <span className="console-log-prompt">></span>
              {parseMessage(m)}
            </div>
          );
        })}
    </pre>
  );
}

export default ConsoleLog;
