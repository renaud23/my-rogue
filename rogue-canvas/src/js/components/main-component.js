import React, { useRef, useEffect } from "react";

export default ({ width = 400, height = 400, launch }) => {
  const ref = useRef(null);
  useEffect(() => {
    if (ref.current) {
      launch(ref.current);
    }
  }, [ref, launch]);
  return (
    <canvas ref={ref} style={{ width: `${width}px`, height: `${height}px` }} />
  );
};
