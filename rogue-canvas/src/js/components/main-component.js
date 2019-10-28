import React, { useRef, useEffect } from "react";

export default ({ width = 512, height = 512, launch }) => {
  const ref = useRef(null);
  useEffect(() => {
    if (ref.current) {
      launch(ref.current, width, height);
    }
  }, [ref, launch, width, height]);
  return (
    <canvas ref={ref} style={{ width: `${width}px`, height: `${height}px` }} />
  );
};
