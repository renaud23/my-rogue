function inflict(defender, damages) {
  const { stats } = defender;
  const { life } = stats;

  return {
    ...defender,
    stats: { ...stats, life: Math.max(0, life - damages) },
  };
}

export default inflict;
