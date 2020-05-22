import React, { useRef, useEffect } from "react";

export default function Game({ width = 512, height = 512, launch }) {
  const ref = useRef(null);
  const mapRef = useRef(null);
  useEffect(() => {
    if (ref.current) {
      launch(ref.current, width, height, mapRef.current);
    }
  }, [ref, mapRef, launch, width, height]);
  return (
    <>
      <canvas
        ref={ref}
        style={{ width: `${width}px`, height: `${height}px` }}
        tabIndex="0"
      />
      <canvas ref={mapRef} />
    </>
  );
}
