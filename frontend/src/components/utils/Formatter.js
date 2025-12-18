const formatDateTime = (isoString) => {
    if (!isoString) return '';
    try {
      const date = new Date(isoString);
      const day = String(date.getDate()).padStart(2, '0');
      const month = String(date.getMonth() + 1).padStart(2, '0');
      const year = date.getFullYear();
      const hours = String(date.getHours()).padStart(2, '0');
      const minutes = String(date.getMinutes()).padStart(2, '0');
      const seconds = String(date.getSeconds()).padStart(2, '0');
      return `${day}.${month}.${year} ${hours}:${minutes}:${seconds}`;
    } catch (error) {
      console.error('Error formatting date:', error);
      return isoString;
    }
};

export const toIsoString = (date) => {
    if (!date) 
      return '';
    if (typeof date === 'string') {
      const d = new Date(date);
      if (!isNaN(d)) 
        return d.toISOString();
      return date; 
    }
    if (date instanceof Date) {
      return date.toISOString();
    }
    return '';
};

export default formatDateTime;