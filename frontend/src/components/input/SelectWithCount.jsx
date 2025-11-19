import { useState, useEffect, useCallback } from 'react';
import AsyncSelect from 'react-select/async';
import { components } from 'react-select';
import PropTypes from 'prop-types';
import debounce from 'lodash.debounce';
import toast from 'react-hot-toast';
import './selectWithCountInput.css'

const CountInputWrapper = ({
  children,
  count,
  isSelected = false,
  onCountChange,
  data,
  className,
  readOnly = false
}) => {
  const [localCount, setLocalCount] = useState(count || 1);

  const handleChange = (e) => {
    let newCount = parseInt(e.target.value, 10);
    if (isNaN(newCount) || newCount < 1) {
      newCount = 1;
    }
    setLocalCount(newCount);
    onCountChange(data.value, newCount);
  };

  return (
    <div className={className}>
      <span>{children}</span>
      {!isSelected && (
        <input
          type="number"
          min="1"
          value={localCount}
          onChange={handleChange}
          onClick={(e) => e.stopPropagation()}
          className={className.includes('multi') ? 'multi-value-count-input' : 'count-input'}
          readOnly={readOnly}
        />
      )}
    </div>
  );
};

const OptionWithCount = ({ children, innerProps, data, isSelected, initialCount, onCountChange, ...rest }) => {
  return (
    <components.Option {...rest} innerProps={innerProps}>
      <CountInputWrapper
        children={children}
        count={initialCount}
        isSelected={isSelected}
        onCountChange={onCountChange}
        data={data}
        className="select-option-content"
        readOnly
      />
    </components.Option>
  );
};

OptionWithCount.propTypes = {
  children: PropTypes.node,
  innerProps: PropTypes.object,
  data: PropTypes.shape({
    value: PropTypes.oneOfType([PropTypes.string, PropTypes.number]).isRequired,
    label: PropTypes.string,
  }).isRequired,
  isSelected: PropTypes.bool,
  initialCount: PropTypes.number,
  onCountChange: PropTypes.func.isRequired
};

const MultiValueWithCount = ({ children, data, initialCount, onCountChange, ...rest }) => {
  return (
    <components.MultiValue {...rest}>
      <CountInputWrapper
        children={children}
        count={initialCount}
        onCountChange={onCountChange}
        data={data}
        className="multi-value-content"
      />
    </components.MultiValue>
  );
};

MultiValueWithCount.propTypes = {
  children: PropTypes.node,
  data: PropTypes.shape({
    value: PropTypes.oneOfType([PropTypes.string, PropTypes.number]).isRequired,
    label: PropTypes.string,
  }).isRequired,
  initialCount: PropTypes.number,
  onCountChange: PropTypes.func.isRequired
};

const SelectWithCount = ({
  apiService,
  placeholder,
  className = '',
  initialValue = [],
  onChange
}) => {
  // eslint-disable-next-line no-unused-vars
  const [searchResults, setSearchResults] = useState([]);

  const [selectedElements, setSelectedElements] = useState([]);

  const [tempCounts, setTempCounts] = useState({});

  const handleTempCountChange = useCallback((id, newCount) => {
    setTempCounts(prev => ({ ...prev, [id]: newCount }));
  }, []);

  useEffect(() => {
    if (!initialValue || initialValue.length === 0) {
      setSelectedElements([]);
      setTempCounts({});
      if (onChange) onChange([]);
      return;
    }

    const preformattedInitial = initialValue.map(item => ({
      value: item.structureElementId,
      label: item.structureElement.name,
      count: item.count,
    }));

    setSelectedElements(preformattedInitial);

    const initialTempCounts = {};
    preformattedInitial.forEach(item => {
      initialTempCounts[item.value] = item.count;
    });
    setTempCounts(initialTempCounts);

    if (onChange) {
      onChange(preformattedInitial.map(item => ({
        structureElementId: item.value,
        count: item.count
      })));
    }

    const fetchAllSearchOptions = async () => {
      try {
        const query = `?name=${encodeURIComponent('')}&page=0&size=40`;
        const response = await apiService.getAll(query);

        const allOptions = response.items.map(item => ({
          value: item.id,
          label: item.name,
          initialCount: tempCounts[item.id] || 1,
        }));

        preformattedInitial.forEach(initialOpt => {
          if (!allOptions.some(opt => opt.value === initialOpt.value)) {
            allOptions.push(initialOpt);
          }
        });

        setSearchResults(allOptions);
      } catch (e) {
        toast.error('Ошибка загрузки доступных элементов: ' + e?.message);
        setSearchResults([]);
      }
    };

    fetchAllSearchOptions();
  }, [initialValue]);

  const loadOptions = debounce(async (inputValue, callback) => {
    try {
      const query = `?name=${encodeURIComponent(inputValue)}&page=0&size=40`;
      const response = await apiService.getAll(query);
      const options = response.items.map(item => ({
        value: item.id,
        label: item.name,
        initialCount: tempCounts[item.id] || 1,
      }));
      setSearchResults(options);
      callback(options);
    } catch (e) {
      toast.error('Произошла ошибка: ' + e?.error + ' - ' + e?.message);
      callback([]);
    }
  }, 300);

  const handleChange = (newValue) => {
    const updatedSelected = newValue.map(item => ({
      value: item.value,
      label: item.label,
      count: tempCounts[item.value] || item.count || 1
    }));
    setSelectedElements(updatedSelected);

    const finalOutput = updatedSelected.map(item => ({
      structureElementId: item.value,
      count: item.count
    }));
    onChange(finalOutput);
  };

  const customComponents = {
    Option: (props) => (
      <OptionWithCount
        {...props}
        initialCount={tempCounts[props.data.value]}
        onCountChange={handleTempCountChange}
      />
    ),
    MultiValue: (props) => (
      <MultiValueWithCount
        {...props}
        initialCount={selectedElements.find(el => el.value === props.data.value)?.count}
        onCountChange={(id, newCount) => {
          setSelectedElements(prev =>
            prev.map(el => el.value === id ? { ...el, count: newCount } : el)
          );
          handleTempCountChange(id, newCount);
          const currentElements = selectedElements.map(el => el.value === id ? { ...el, count: newCount } : el);
          onChange(currentElements.map(item => ({
            structureElementId: item.value,
            count: item.count
          })));
        }}
      />
    ),
  };

  return (
    <div className={className}>
      <AsyncSelect
        isMulti
        cacheOptions
        loadOptions={loadOptions}
        defaultOptions
        value={selectedElements.map(item => ({ value: item.value, label: item.label }))}
        onChange={handleChange}
        placeholder={placeholder || 'Выберите элементы и укажите количество'}
        noOptionsMessage={() => "Элементы не найдены"}
        components={customComponents}
      />
    </div>
  );
};

SelectWithCount.propTypes = {
  apiService: PropTypes.object.isRequired,
  placeholder: PropTypes.string,
  className: PropTypes.string,
  initialValue: PropTypes.arrayOf(PropTypes.shape({
    structureElementId: PropTypes.oneOfType([PropTypes.string, PropTypes.number]).isRequired,
    count: PropTypes.number.isRequired,
    structureElement: PropTypes.shape({
      name: PropTypes.string.isRequired,
    }).isRequired,
  })),
  onChange: PropTypes.func.isRequired,
};

export default SelectWithCount;