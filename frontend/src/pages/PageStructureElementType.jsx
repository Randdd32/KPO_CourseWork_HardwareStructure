import { useEffect } from 'react';
import { useParams } from 'react-router-dom';
import StructureElementTypesForm from '../components/structureElementTypes/form/StructureElementTypesForm';

const PageStructureElementType = () => {
  const { id } = useParams();

  useEffect(() => {
    document.body.classList.add('bg-white');

    return () => {
      document.body.classList.remove('bg-white');
    };
  }, []);

  return (
    <StructureElementTypesForm id={id} />
  );
};

export default PageStructureElementType;