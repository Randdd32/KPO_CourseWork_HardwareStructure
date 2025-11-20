import { useEffect } from 'react';
import { useParams } from 'react-router-dom';
import StructureElementModelsForm from '../components/structureElementModels/form/StructureElementModelsForm';

const PageStructureElementModel = () => {
  const { id } = useParams();

  useEffect(() => {
    document.body.classList.add('bg-white');

    return () => {
      document.body.classList.remove('bg-white');
    };
  }, []);

  return (
    <StructureElementModelsForm id={id} />
  );
};

export default PageStructureElementModel;