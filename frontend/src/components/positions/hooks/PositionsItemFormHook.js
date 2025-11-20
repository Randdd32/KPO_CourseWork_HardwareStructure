import { useState } from 'react';
import toast from 'react-hot-toast';
import PositionsApiService from '../service/PositionsApiService';
import usePositionItem from './PositionsItemHook';

const usePositionItemForm = (id, positionsChangeHandle) => {
  const { item, setItem } = usePositionItem(id);
  const [validated, setValidated] = useState(false);

  const resetValidity = () => setValidated(false);

  const getPositionObject = ({ name, description }) => ({
    name,
    description
  });

  const handleChange = (event) => {
    setItem({
      ...item,
      [event.target.name]: event.target.value
    });
  };

  const handleSubmit = async (event) => {
    const form = event.currentTarget;
    event.preventDefault();
    event.stopPropagation();

    const body = getPositionObject(item);

    if (form.checkValidity()) {
      if (id === undefined) {
        await PositionsApiService.create(body);
      } else {
        await PositionsApiService.update(id, body);
      }

      if (positionsChangeHandle) positionsChangeHandle();

      toast.success('Элемент успешно сохранен', { id: 'PositionsTable' });
      return true;
    }

    setValidated(true);
    return false;
  };

  return {
    item,
    validated,
    handleSubmit,
    handleChange,
    resetValidity
  };
};

export default usePositionItemForm;