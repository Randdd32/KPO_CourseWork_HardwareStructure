import { useEffect } from 'react';
import StructureElementTypes from '../components/structureElementTypes/table/StructureElementTypes';

const PageStructureElementTypes = () => {
  useEffect(() => {
    document.body.classList.add('bg-white');

    return () => {
      document.body.classList.remove('bg-white');
    };
  }, []);

  return (
    <StructureElementTypes />
  );
};

export default PageStructureElementTypes;