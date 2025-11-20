import { useState } from 'react';
import StructureElementModelsApiService from '../service/StructureElementModelsApiService';

const useStructureElementModels = () => {
  const [structureElementModelsRefresh, setStructureElementModelsRefresh] = useState(false);
  const [structureElementModels, setStructureElementModels] = useState([]);
  const [totalPages, setTotalPages] = useState(1);

  const handleStructureElementModelsChange = () => setStructureElementModelsRefresh(!structureElementModelsRefresh);

  const getStructureElementModels = async (page = 1, size = 8) => {
    const data = await StructureElementModelsApiService.getAll(`?page=${page - 1}&size=${size}`);
    setStructureElementModels(data.items || []);
    setTotalPages(data.totalPages || 1);
  };

  return {
    structureElementModels,
    totalPages,
    getStructureElementModels,
    structureElementModelsRefresh,
    handleStructureElementModelsChange
  };
};

export default useStructureElementModels;