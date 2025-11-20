import { useState } from 'react';
import toast from 'react-hot-toast';
import BuildingsApiService from '../service/BuildingsApiService';
import useBuildingsItem from './BuildingsItemHook';

const useBuildingsItemForm = (id, buildingsChangeHandle) => {
    const { item, setItem } = useBuildingsItem(id);

    const [validated, setValidated] = useState(false);

    const resetValidity = () => {
        setValidated(false);
    };

    const getBuildingObject = ({ name, address }) => ({
        name,
        address
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
        const body = getBuildingObject(item);
        if (form.checkValidity()) {
            if (id === undefined) {
                await BuildingsApiService.create(body);
            } else {
                await BuildingsApiService.update(id, body);
            }
            if (buildingsChangeHandle) 
              buildingsChangeHandle();
            toast.success('Элемент успешно сохранен', { id: 'BuildingsTable' });
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

export default useBuildingsItemForm;