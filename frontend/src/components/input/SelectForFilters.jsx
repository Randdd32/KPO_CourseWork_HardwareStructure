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
  value,
  onChange,
  required = false
}) => {
  const [currentOptions, setCurrentOptions] = useState([]);
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

    const fetchAndSetInitialValue = async () => {
      if (!value || (isMulti && value.length === 0)) {
        setCurrentOptions([]);
        return;
      }

      const idsToFetch = isMulti ? value : [value];
      try {
        const query = `?ids=${idsToFetch.join(',')}`;
        const response = await apiService.getByIds(query);
        const data = getData(response, false);

        const fetchedOptions = data.map(item => ({
          value: item[valueField],
          label: item[displayField]
        }));

        if (isMulti) {
          addUniqueOptions(searchResults, fetchedOptions);
        } else {
          addUniqueOptions(searchResults, [fetchedOptions]);
        }

        setCurrentOptions(fetchedOptions);
      } catch (e) {
        toast.error('Ошибка при загрузке выбранных значений: ' + e?.message);
        setCurrentOptions([]);
      }
    };

    fetchAndSetInitialValue();
  }, [value, apiService, displayField, valueField, isMulti]);

  const handleSelectChange = (selectedOption) => {
    let outputValue;
    if (isMulti) {
      outputValue = selectedOption ? selectedOption.map(s => s.value) : [];
    } else {
      outputValue = selectedOption ? selectedOption.value : null;
    }
    onChange(outputValue);
  };

  const selectedReactSelectValue = Array.isArray(value)
    ? currentOptions.filter(option => value.includes(option.value))
    : currentOptions.find(option => option.value === value) || null;


  return (
    <div className={className}>
      <AsyncSelect
        cacheOptions
        defaultOptions
        loadOptions={loadOptions}
        value={selectedReactSelectValue}
        onChange={handleSelectChange}
        placeholder={placeholder || 'Выберите значение'}
        isMulti={isMulti}
        noOptionsMessage={() => "Элементы не найдены"}
        isClearable={!required}
      />
      {required && (
        <input
          type="hidden"
          tabIndex={-1}
          autoComplete="off"
          style={{ position: 'absolute', opacity: 0, pointerEvents: 'none' }}
          value={value && (isMulti ? value.length : value !== null) ? 'selected' : ''}
          required={required}
        />
      )}
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
  value: PropTypes.oneOfType([
    PropTypes.string,
    PropTypes.number,
    PropTypes.arrayOf(PropTypes.oneOfType([PropTypes.string, PropTypes.number])),
    PropTypes.array
  ]),
  onChange: PropTypes.func.isRequired,
  required: PropTypes.bool
};

export default SearchableSelect;