import { useEffect, useState } from 'react';
import StructureElementTypesApiService from '../service/StructureElementTypesApiService';

const useStructureElementTypes = () => {
  const [structureElementTypesRefresh, setStructureElementTypesRefresh] = useState(false);
  const [structureElementTypes, setStructureElementTypes] = useState([]);
  const handleStructureElementTypesChange = () => setStructureElementTypesRefresh(!structureElementTypesRefresh);

  const getStructureElementTypes = async () => {
    const data = await StructureElementTypesApiService.getAll();
    setStructureElementTypes(data ?? []);
  };

  useEffect(() => {
    getStructureElementTypes();
  }, [structureElementTypesRefresh]);

  return {
    structureElementTypes,
    setStructureElementTypes,
    handleStructureElementTypesChange,
  };
};

export default useStructureElementTypes;