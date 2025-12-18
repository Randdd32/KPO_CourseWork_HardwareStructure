import { useEffect, useState } from 'react';
import { BuildingsGetService } from '../service/BuildingsApiService';

const useBuildings = () => {
    const [buildingsRefresh, setBuildingsRefresh] = useState(false);
    const [buildings, setBuildings] = useState([]);
    const handleBuildingsChange = () => setBuildingsRefresh(!buildingsRefresh);

    const getBuildings = async () => {
        const data = await BuildingsGetService.getAll();
        setBuildings(data ?? []);
    };

    useEffect(() => {
        getBuildings();
    }, [buildingsRefresh]);

    return {
        buildings,
        setBuildings,
        handleBuildingsChange,
    };
};

export default useBuildings;