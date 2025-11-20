import AsyncSelect from 'react-select/async';
import { useState, useEffect } from 'react';
import PropTypes from 'prop-types';
import debounce from 'lodash.debounce';
import toast from 'react-hot-toast';

const SearchableSelect = ({
  apiService,
  placeholder,
  displayField = 'name',
  valueField = 'id',
  buildQuery,
  isMulti = false,
  isPagination = false,
  className = '',
  initialValue,
  onChange,
  required = false
}) => {

  const [selectedValue, setSelectedValue] = useState(null);
  // eslint-disable-next-line no-unused-vars
  const [searchResults, setSearchResults] = useState([]);

  const getData = (response, isPagination) => {
    if (isPagination) {
      return response.items;
    } else {
      return response;
    }
  };

  const loadOptions = debounce(async (inputValue, callback) => {
    try {
      const query = buildQuery ? buildQuery(inputValue) : `?name=${encodeURIComponent(inputValue)}`;
      const response = await apiService.getAll(query);
      const data = getData(response, isPagination);
      const options = data.map(item => ({
        value: item[valueField],
        label: item[displayField]
      }));
      setSearchResults(options);
      callback(options);
    } catch (e) {
      toast.error('Произошла ошибка: ' + e?.error + ' - ' + e?.message);
      setSearchResults([]);
      callback([]);
    }
  }, 300);

  useEffect(() => {
    const addUniqueOptions = (targetOptions, sourceOptions) => {
      sourceOptions.forEach(sourceOpt => {
        if (!targetOptions.some(targetOpt => targetOpt.value === sourceOpt.value)) {
          targetOptions.push(sourceOpt);
        }
      });
    };

    const fetchInitialValue = async () => {
      if (!initialValue || (isMulti && initialValue.length === 0)) {
        setSelectedValue(isMulti ? [] : null);
        if (onChange) onChange(isMulti ? [] : null);
        return;
      }

      let selected = initialValue;
      let allOptions = [];

      try {
        const query = buildQuery ? buildQuery('') : `?name=${encodeURIComponent('')}`;
        let response = await apiService.getAll(query);
        const data = getData(response, isPagination);

        allOptions = data.map(item => ({
          value: item[valueField],
          label: item[displayField]
        }));

        if (isMulti) {
          addUniqueOptions(allOptions, initialValue);
        } else {
          addUniqueOptions(allOptions, [initialValue]);
        }

        setSearchResults(allOptions);
        setSelectedValue(selected);

        if (onChange) {
          const outputValue = isMulti
            ? (selected ? selected.map(s => s.value) : [])
            : (selected ? selected.value : null);
          onChange(outputValue);
        }
      } catch (e) {
        toast.error('Ошибка инициализации: ' + e?.message);
        setSelectedValue(isMulti ? [] : null);
        if (onChange) {
          onChange(isMulti ? [] : null);
        }
      }
    };

    fetchInitialValue();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [initialValue]);

  const handleSearch = (selectedOption) => {
    setSelectedValue(selectedOption);
    if (onChange) {
      const outputValue = isMulti
        ? selectedOption.map(s => s.value)
        : (selectedOption ? selectedOption.value : null);
      onChange(outputValue);
    }
  };

  return (
    <div className={className}>
      <AsyncSelect
        cacheOptions
        defaultOptions
        loadOptions={loadOptions}
        value={selectedValue}
        onChange={handleSearch}
        placeholder={placeholder || 'Выберите значение'}
        isMulti={isMulti}
        noOptionsMessage={() => "Элементы не найдены"}
        isClearable={!required}
      />
      <input
        type="text"
        tabIndex={-1}
        autoComplete="off"
        style={{ opacity: 0, height: 0, position: 'absolute' }}
        value={
          selectedValue
            ? (isMulti ? JSON.stringify(selectedValue.map(v => v.value)) : selectedValue.value)
            : ''
        }
        onChange={() => { }}
        required={required}
      />
    </div>
  );
};

SearchableSelect.propTypes = {
  apiService: PropTypes.object.isRequired,
  placeholder: PropTypes.string,
  displayField: PropTypes.string,
  valueField: PropTypes.string,
  buildQuery: PropTypes.func,
  isMulti: PropTypes.bool,
  isPagination: PropTypes.bool,
  className: PropTypes.string,
  initialValue: PropTypes.oneOfType([
    PropTypes.string,
    PropTypes.number,
    PropTypes.arrayOf(PropTypes.oneOfType([PropTypes.string, PropTypes.number]))
  ]),
  onChange: PropTypes.func,
  required: PropTypes.bool
};

export default SearchableSelect;