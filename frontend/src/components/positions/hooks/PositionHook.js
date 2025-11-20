import { useState } from 'react';
import PositionsApiService from '../service/PositionsApiService';

const usePositions = () => {
  const [positionsRefresh, setPositionsRefresh] = useState(false);
  const [positions, setPositions] = useState([]);
  const [totalPages, setTotalPages] = useState(1);

  const handlePositionsChange = () => setPositionsRefresh(!positionsRefresh);

  const getPositions = async (page = 1, size = 8) => {
    const data = await PositionsApiService.getAll(`?page=${page - 1}&size=${size}`);
    setPositions(data.items || []);
    setTotalPages(data.totalPages || 1);
  };

  return {
    positions,
    totalPages,
    getPositions,
    positionsRefresh,
    handlePositionsChange
  };
};

export default usePositions;