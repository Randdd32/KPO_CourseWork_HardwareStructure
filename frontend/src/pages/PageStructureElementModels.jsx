import { useEffect } from 'react';
import StructureElementModels from '../components/structureElementModels/table/StructureElementModels';

const PageStructureElementModels = () => {
  useEffect(() => {
    document.body.classList.add('bg-white');

    return () => {
      document.body.classList.remove('bg-white');
    };
  }, []);

  return (
    <StructureElementModels />
  );
};

export default PageStructureElementModels;