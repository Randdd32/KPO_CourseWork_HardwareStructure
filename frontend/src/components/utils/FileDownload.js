import toast from 'react-hot-toast';

const downloadFile = async (apiCallPromise, defaultFilename, errorMessage) => {
    try {
        const response = await apiCallPromise;
        const blob = new Blob([response.data]);

        let filename = defaultFilename;
        console.log(response);
        const contentDisposition = response?.headers['content-disposition'];
        if (contentDisposition) {
            const filenameMatch = contentDisposition.match(/filename="([^"]+)"/);
            if (filenameMatch && filenameMatch[1]) {
                filename = decodeURIComponent(filenameMatch[1]);
            }
        }

        const url = window.URL.createObjectURL(blob);
        const link = document.createElement('a');
        link.href = url;
        link.setAttribute('download', filename);
        document.body.appendChild(link);
        link.click();
        link.remove();
        window.URL.revokeObjectURL(url);
    } catch (error) {
        toast.error(errorMessage || `Произошла ошибка при скачивании файла: ${error?.message || 'Неизвестная ошибка'}`);
        throw error; 
    }
};

export default downloadFile;